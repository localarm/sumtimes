package com.pavel.sumtimes.springoutput;

import com.pavel.sumtimes.commons.OutOfTimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**Thread-safe implementation of cache. Cache consists of periods, every periods consists of some quantums. Sum value
 * places at certain quantum that equals time window of summation. Quantums without value stored with zero as default.
 */
public class AppCache implements Cache{

    private final static Logger logger = LoggerFactory.getLogger(AppCache.class);
    private final int periods;
    private final long quantumMs;
    private final long periodMs;
    private final int amountOfQuantumsPerPeriod;
    private final AtomicReference<CacheData> cacheData;
    private volatile Timestamp lastAcquiredQuantum;

    public AppCache(int periods, long quantumMs, long periodMs, long initCacheEndTime, Timestamp currentServiceQuantum){
        this.periods = periods;
        this.quantumMs = quantumMs;
        this.periodMs = periodMs;
        amountOfQuantumsPerPeriod =(int) (periodMs/quantumMs);
        this.lastAcquiredQuantum = currentServiceQuantum;
        cacheData = new AtomicReference<>(new CacheData(periods, periodMs,
                new long[periods][(int) (periodMs / quantumMs)], initCacheEndTime));
    }

    /**put values from map to cache
     * @param sums map with times and sums for cache, values must be in range of cache
     * @param initCacheEndTime end time of cache
     * @throws RuntimeException if time of sum not in range of cache
     */
    public void init(Map<Timestamp, Long> sums, long initCacheEndTime){
        long[][] initCacheData = new long[periods][(int) (periodMs /quantumMs)];
        for (Map.Entry<Timestamp, Long> entry : sums.entrySet()) {
            int period = (int) ((initCacheEndTime -entry.getKey().getTime()) / periodMs);
            int index = (int) (((initCacheEndTime -entry.getKey().getTime()) % periodMs) / quantumMs);
            if (periods < period || period < 0 || amountOfQuantumsPerPeriod < index || index < 0){
                throw new RuntimeException("Failed to fill cache with time " + entry.getKey() + " and sum " +
                        entry.getValue() + " with defined period " + period + " and index " + index);
            }
            initCacheData[period][index] = entry.getValue();
        }
        cacheData.set(new CacheData(periods, periodMs, initCacheData, initCacheEndTime));
        logger.info("Cache filled with sums from Database, cache end time {}", new Timestamp(initCacheEndTime));
    }

    @Override
    public synchronized void put(Timestamp sumTime, Timestamp  moveTime, long sum) throws OutOfTimeException {
        moveCacheEndTimeForward(moveTime);
        CacheData cacheDataLocal = cacheData.get();
        int period = (int) ((sumTime.getTime() - cacheDataLocal.getCacheStartTimeMs()) / periodMs);
        if ( period < 0 || (periods-1) < period || sumTime.after(new Timestamp(moveTime.getTime()-quantumMs))){
            throw new OutOfTimeException("Time of sum " + sumTime + " out of time of cache: start "
                    + cacheDataLocal.getCacheStartTimeMs() + " and end " +cacheDataLocal.getCacheEndTimeMs());
        }
        int currentQuantum = (int) (((sumTime.getTime() - cacheDataLocal.getCacheStartTimeMs()) % periodMs)/ quantumMs);
        cacheDataLocal.getSums()[period][currentQuantum]= sum;
        if (moveTime.after(lastAcquiredQuantum)) {
            lastAcquiredQuantum = moveTime;
        }
    }

    @Override
    public synchronized void moveTime(Timestamp time) {
        moveCacheEndTimeForward(time);
        if (time.after(lastAcquiredQuantum)) {
            lastAcquiredQuantum = new Timestamp((time.getTime()/quantumMs)*quantumMs);
        }
    }

    @Override
    public Map<Timestamp, Long> get(Timestamp minTime, Timestamp maxTime) {
        if (maxTime.getTime() - lastAcquiredQuantum.getTime() - quantumMs >= 0) {
            maxTime = new Timestamp(lastAcquiredQuantum.getTime() - quantumMs);
        }
        if (minTime.after(maxTime)) {
            return new HashMap<>();
        }
        CacheData cacheDataLocal = cacheData.get();
        long cacheStartTime = cacheDataLocal.getCacheStartTimeMs();
        if (minTime.before(new Timestamp(cacheStartTime))) {// when minTime before first cache record, return null to navigate to Database
            return null;
        }
        //setup start and end points
        long[][] sumArray = cacheDataLocal.getSums();
        int startPeriod = (int) ((minTime.getTime()-cacheStartTime)/ periodMs);
        int startIndex = (int) (((minTime.getTime()-cacheStartTime) % periodMs)/ quantumMs);
        int endPeriod = (int) ((maxTime.getTime()-cacheStartTime)/ periodMs);
        int endIndex = (int) (((maxTime.getTime()-cacheStartTime) % periodMs)/ quantumMs);

        Map<Timestamp, Long> sumsMap = new HashMap<>();
        for (int period = startPeriod; period <= endPeriod; period++) {
            for(int i = (period == startPeriod ? startIndex : 0);
                i < (period == endPeriod ? endIndex: ((int) (periodMs /quantumMs)-1)) || ((period == endPeriod)
                        && i < (endIndex + 1)); i++) {
                sumsMap.put(new Timestamp(cacheStartTime + period * periodMs + i * quantumMs), sumArray[period][i]);
            }
        }
        return sumsMap;
    }

    /**Move cache for certain periods(from 1 to {@link #periods}), if time equals or after {@link CacheData#cacheEndTimeMs}
     * @param time time that multiple quantum
     */
    private void moveCacheEndTimeForward(Timestamp time){
        CacheData currentCacheData = cacheData.get();
        if ( currentCacheData.getCacheEndTimeMs() <= time.getTime()) {
            int startingPeriodFromOldCacheToCopy = (int) ((time.getTime() -
                    currentCacheData.getCacheEndTimeMs()) / periodMs) + 1;
            int periodIndex = 0;
            long[][] newSums = new long[periods][];
            long[][] currentSums = currentCacheData.getSums();
            if (startingPeriodFromOldCacheToCopy > 0) {
                for (;startingPeriodFromOldCacheToCopy + periodIndex < periods; periodIndex++) {
                    newSums[periodIndex] = currentSums[periodIndex+startingPeriodFromOldCacheToCopy];
                }
            }
            for (; periodIndex < periods; periodIndex++) {//fill remaining periods with fresh arrays
                newSums[periodIndex] = new long[amountOfQuantumsPerPeriod];
            }
            long newEndTime = currentCacheData.getCacheEndTimeMs() + ((time.getTime() -
                    currentCacheData.getCacheEndTimeMs())/ periodMs + 1) * periodMs;
            cacheData.set(new CacheData(periods, periodMs, newSums, newEndTime));
            logger.info("Rolled cache forward, new cache end time = {}",new Timestamp(newEndTime));
        }
    }

    @Override
    public Timestamp getLastAcquiredQuantum(){
        return lastAcquiredQuantum;
    }

    private static class CacheData {

        private final long[][] sums;
        private final long cacheEndTimeMs;
        private final long cacheStartTimeMs;

        public CacheData(int periods, long periodMs, long[][] sums, long cacheEndTimeMs) {
            this.sums = sums;
            this.cacheEndTimeMs = cacheEndTimeMs;
            cacheStartTimeMs = cacheEndTimeMs - periods*periodMs;
        }

        public long[][] getSums() {
            return sums;
        }

        public long getCacheEndTimeMs() {
            return cacheEndTimeMs;
        }

        public long getCacheStartTimeMs(){
            return cacheStartTimeMs;
        }
    }
}
