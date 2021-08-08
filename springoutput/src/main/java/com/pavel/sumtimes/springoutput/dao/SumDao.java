package com.pavel.sumtimes.springoutput.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SumDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SumDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<Timestamp, Long> getInRange(Timestamp startMinute, Timestamp endMinute) {
        return jdbcTemplate.query("SELECT * FROM sum where time >= ? AND time <= ?",
                new Object[]{startMinute, endMinute}, rs -> {
                    Map<Timestamp, Long> sums = new HashMap<>();
                    while (rs.next()) {
                        sums.put(rs.getTimestamp("time"), rs.getLong("value"));
                    }
                    return sums;
        });
    }

    public Timestamp getLastRecordTime(){
        Timestamp result;
        try {
            result = jdbcTemplate.queryForObject("SELECT time FROM last_record",Timestamp.class);
        } catch (IncorrectResultSizeDataAccessException ignore) {
            result = null;
        }
        return result;
    }

    @Transactional
    public void updateSum(long sum, Timestamp sumTime, Timestamp lastRecordTime) {
        jdbcTemplate.update("INSERT INTO sum(time, value) VALUES(?, ?) ", sumTime, sum);
        updateLastRecord(lastRecordTime);
    }

    public void updateLastRecord(Timestamp lastRecordTime) {
        jdbcTemplate.update("UPDATE last_record SET time = ?", new Object[] {lastRecordTime});
    }




}
