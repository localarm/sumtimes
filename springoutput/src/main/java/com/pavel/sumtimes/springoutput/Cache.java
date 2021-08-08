package com.pavel.sumtimes.springoutput;

import com.pavel.sumtimes.commons.OutOfTimeException;

import java.sql.Timestamp;
import java.util.Map;

/**Cache which store sum for each quantum. Must contain last record time, that indicates time of last changes in service.
 * <p>
 * quantum - time window, when values sum needed to calculate. latest available value in cache = last record time - quantum.
 */
public interface Cache {

    /**Put sum in proper place in cache. MoveTime must be greater then last record in cache. SumTime must be
     * lower than moveTime
     * @param sumTime time of sum in milliseconds, not null
     * @param moveTime time in milliseconds, not null
     * @throws OutOfTimeException when sumTime is not in range of cache
     */
    void put(Timestamp sumTime, Timestamp moveTime, long sum) throws OutOfTimeException;

    /**Try to move current time of cache further.
     * @param time in milliseconds, not null
     */
    void moveTime(Timestamp time);

    /**Search for results in specific range and store it in map, both range included during search. Cache may return
     * values between first time of cache and last record time-quantum, that indicate current quantum of service.
     * If upper bound of request after last record in cache, then trim results gap to (last record time - quantum).
     * If lower bound of request before first record in cache, then return null.
     * If lower bound after upper bound (wrong request or trim situation with upper bound and last recorded time)
     * then return empty map.
     * @param minTime lower limit for search in milliseconds, not null
     * @param maxTime upper limit for search in milliseconds, not null
     * @return Map with keys as time and values as sum for this time. Return null, if minTime before first time in cache.
     * Return empty map, if minTime > maxTime
     */
    Map<Timestamp, Long> get(Timestamp minTime, Timestamp maxTime);

    Timestamp getLastAcquiredQuantum();
}
