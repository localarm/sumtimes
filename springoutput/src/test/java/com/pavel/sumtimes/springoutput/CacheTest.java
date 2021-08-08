package com.pavel.sumtimes.springoutput;

import com.pavel.sumtimes.commons.OutOfTimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.util.Map;

public class CacheTest {
    //SETUP FOR TESTS
    private final static int periods = 10;
    private final static long quantum = 60000;
    private final static long periodMs = 86400000;
    private AppCache cache;

    @BeforeEach
    public void initCache(){
        long initCacheEndTIme = (System.currentTimeMillis()/quantum) * quantum + periodMs;
        cache = new AppCache(periods, quantum, periodMs, initCacheEndTIme, new Timestamp( initCacheEndTIme - periodMs));
    }

    private long prepareTime(long timeMs) { return timeMs/quantum*quantum;
    }

    @Test
    public void moveLastRecordTest(){
        long currentTimeMs = prepareTime(System.currentTimeMillis());
        Timestamp expectedLastRecord = new Timestamp(currentTimeMs + quantum);
        cache.moveTime(new Timestamp(currentTimeMs + quantum));
        Timestamp actualLastRecord = cache.getLastAcquiredQuantum();
        Assertions.assertEquals(expectedLastRecord, actualLastRecord);
    }

    @Test
    public void wrongLastRecordMoveTest(){
        long currentMillis = System.currentTimeMillis();
        cache.moveTime(new Timestamp(currentMillis));
        cache.moveTime(new Timestamp(currentMillis-quantum));
        Timestamp actualLastRecord = cache.getLastAcquiredQuantum();
        Assertions.assertEquals(currentMillis/quantum*quantum, actualLastRecord.getTime());
    }

    @Test
    public void simplePutAndGet() throws OutOfTimeException {
        long time = (System.currentTimeMillis()/quantum)*quantum+quantum*2;
        Timestamp sumTime = new Timestamp(time);
        Timestamp lastRecordTime = new Timestamp(time+quantum);
        cache.put(sumTime, lastRecordTime, 100);
        Map<Timestamp, Long> actualMap =  cache.get(sumTime, sumTime);
        Assertions.assertEquals(1, actualMap.size());
        Assertions.assertTrue( actualMap.containsValue(100L));
    }

    @Test
    public void putThenRollAndGet() throws OutOfTimeException {
        long time = prepareTime(System.currentTimeMillis());
        Timestamp record = new Timestamp(time);
        cache.put(record, new Timestamp(time + quantum), 100L);
        Timestamp timestampForRoll = new Timestamp(time + periodMs);
        cache.moveTime(timestampForRoll);
        Map<Timestamp, Long> actualMap = cache.get(record,record);
        Assertions.assertEquals(1, actualMap.size());
        Assertions.assertTrue( actualMap.containsValue(100L));
    }

    @Test
    public void putWithWrongSumTimeTest(){
        long time = (System.currentTimeMillis()/quantum)*quantum -periods* periodMs;
        Timestamp record = new Timestamp(time);
        try {
            cache.put(record, new Timestamp(time+periods* periodMs), 100);
        } catch (OutOfTimeException ignore) {
        }
        Timestamp record2 = new Timestamp((System.currentTimeMillis()/quantum)*quantum + periodMs);
        try {
            cache.put(record2, new Timestamp((System.currentTimeMillis()/quantum)*quantum), 100);
        } catch (OutOfTimeException ignore) {
        }

    }

    @Test
    public void putAgainValue() throws OutOfTimeException {
        long time = (System.currentTimeMillis()/quantum)*quantum;
        Timestamp record = new Timestamp(time);
        cache.put(record, new Timestamp(time+quantum), 100L);
        Assertions.assertEquals(100L, cache.get(record,record).get(record));
        Timestamp timestampForRoll = new Timestamp(time+ periodMs);
        cache.moveTime(timestampForRoll);
        cache.put(record, new Timestamp(time+ periodMs +quantum), 200L);
        Assertions.assertEquals(200L, cache.get(record,record).get(record));
    }

    @Test
    public void rollThenPutInFreshPeriodAndGet() throws OutOfTimeException {
        long time = (System.currentTimeMillis()/quantum)*quantum+ periodMs;
        cache.moveTime(new Timestamp(time));
        Timestamp record = new Timestamp(time+quantum);
        cache.put(record, new Timestamp(time+2*quantum), 100);
        Assertions.assertEquals(100L, cache.get(record,record).get(record));
    }

    @Test
    public void getSingleValueInRange() throws OutOfTimeException {
        long time = (System.currentTimeMillis()/quantum)*quantum+120000;
        Timestamp expectedValueTime = new Timestamp(time);
        cache.put(expectedValueTime, new Timestamp(time + 2*quantum), 100);
        Map<Timestamp, Long> actualMap =  cache.get(new Timestamp(time-quantum), new Timestamp(time+quantum));
        Assertions.assertEquals(3, actualMap.size());
        Map<Timestamp, Long> expectedMap = Map.of(new Timestamp(time-quantum), 0L, expectedValueTime,
                100L, new Timestamp(time+quantum), 0L);
        Assertions.assertEquals(expectedMap, actualMap);
    }

    @Test
    public void getValuesInRangeTest() throws OutOfTimeException {
        long time = (System.currentTimeMillis()/quantum)*quantum;
        Timestamp firstRecord = new Timestamp(time);
        cache.put(firstRecord, new Timestamp(time + quantum), 50);
        Timestamp secondRecord = new Timestamp(time + quantum);
        cache.put(secondRecord, new Timestamp(time + 2*quantum), 70);
        Timestamp thirdRecord = new Timestamp(time+2*quantum);
        cache.put(thirdRecord, new Timestamp(time + 3*quantum), 80);
        Map<Timestamp, Long> expectedMap = Map.of(firstRecord, 50L, secondRecord, 70L, thirdRecord, 80L);
        Assertions.assertEquals(expectedMap, cache.get(firstRecord, thirdRecord));
    }

    @Test
    public void getInDifferencesPeriodsTest() throws OutOfTimeException {
        long time = (System.currentTimeMillis()/quantum)*quantum;
        Timestamp firstRecord = new Timestamp(time);
        cache.put(firstRecord, new Timestamp(time + quantum), 50);
        Timestamp secondRecord = new Timestamp(time + periodMs + quantum);
        cache.put(secondRecord, new Timestamp(time + periodMs + 2 * quantum), 51);
        var actualMap = cache.get(firstRecord, new Timestamp(time + periodMs + quantum));
        Assertions.assertTrue(actualMap.containsValue(50L));
        Assertions.assertTrue(actualMap.containsValue(51L));
    }

    @Test
    public void overrollTimeWithMoveTimeTest() throws OutOfTimeException {
        long time = (System.currentTimeMillis()/quantum)*quantum;
        Timestamp expectedValueTime = new Timestamp(time);
        cache.put(expectedValueTime, new Timestamp(time + 2*quantum), 100);
        cache.moveTime(new Timestamp(time+periods* periodMs));
        Assertions.assertNull(cache.get(expectedValueTime, expectedValueTime));
    }

    @Test
    public void moveTimeLessThenOverrollTest() throws OutOfTimeException {
        long time = (System.currentTimeMillis()/quantum)*quantum;
        Timestamp expectedValueTime = new Timestamp(time);
        cache.put(expectedValueTime, new Timestamp(time + 2*quantum), 100);
        cache.moveTime(new Timestamp(time + (periods-1) * periodMs - quantum));
        Assertions.assertEquals(100, cache.get(expectedValueTime, expectedValueTime).get(expectedValueTime));
    }

    @Test
    public void failGetOutOfRangeTest() {
        long time = prepareTime(System.currentTimeMillis());
        cache.moveTime(new Timestamp(time));
        Assertions.assertTrue(cache.get(new Timestamp(time+periods* periodMs +2),
                new Timestamp(time+periods* periodMs +1)).isEmpty());
    }

    @Test
    public void failToGetValueAfterFullRefresh() throws OutOfTimeException {
        long time =prepareTime(System.currentTimeMillis());
        Timestamp expectedValueTime = new Timestamp(time);
        cache.put(expectedValueTime, new Timestamp(time + periodMs + quantum), 100);
        cache.moveTime(new Timestamp(time + periods * periodMs));
        Assertions.assertNull(cache.get(expectedValueTime,expectedValueTime));
    }
}
