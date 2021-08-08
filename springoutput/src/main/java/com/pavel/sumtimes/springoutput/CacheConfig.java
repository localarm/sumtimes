package com.pavel.sumtimes.springoutput;

import com.pavel.sumtimes.springoutput.dao.SumDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;

@Configuration
public class CacheConfig {
    private final static Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);
    @Value("${app.periods}")
    private int periods;
    @Value("${app.quantum}")
    private long quantumMs;
    @Value("${app.periodLengthQuantums}")
    private long periodLengthQuantums;
    private final SumDao sumDao;


    @Autowired
    public CacheConfig(JdbcTemplate jdbcTemplate) {
        sumDao = new SumDao(jdbcTemplate);
    }

    @Bean
    public Cache getCache(){
        Timestamp lastRecordTime = new Timestamp((sumDao.getLastRecordTime().getTime()/quantumMs)*quantumMs);
        long initCacheEndTime = lastRecordTime.getTime() + periodLengthQuantums - quantumMs;
        var sumsMap = sumDao.getInRange(new Timestamp(initCacheEndTime-periods*periodLengthQuantums),
                new Timestamp(lastRecordTime.getTime() - quantumMs));
        AppCache cache = new AppCache(periods, quantumMs, periodLengthQuantums, initCacheEndTime, lastRecordTime);
        cache.init(sumsMap, initCacheEndTime);
        LOGGER.info("setup cache with  end time = {}, last record = {}, periods = {}, period milliseconds = {}," +
                        " quantum milliseconds = {}", initCacheEndTime, lastRecordTime, periods, periodLengthQuantums,
                quantumMs);
        return cache;
    }
}
