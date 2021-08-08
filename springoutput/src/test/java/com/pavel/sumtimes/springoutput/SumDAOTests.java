package com.pavel.sumtimes.springoutput;

import com.pavel.sumtimes.springoutput.dao.SumDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class SumDAOTests {

    private SumDao sumDao;
    @Autowired
    public JdbcTemplate jdbcTemplate;

    @BeforeAll
    public void setSumDao() {
        sumDao = new SumDao(jdbcTemplate);
    }

    @Test
    public void testLastRecord() {
        Timestamp expected = new Timestamp(System.currentTimeMillis());
        sumDao.updateLastRecord(expected);
        Timestamp actual = jdbcTemplate.queryForObject("SELECT * FROM last_record", Timestamp.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateSum() {
        Timestamp expectedTimestamp = new Timestamp(System.currentTimeMillis());
        long expectedSum = 100;
        sumDao.updateSum(expectedSum, expectedTimestamp, expectedTimestamp);
        Timestamp actualTimestamp = jdbcTemplate.queryForObject("SELECT time FROM sum", Timestamp.class);
        long actualSum = jdbcTemplate.queryForObject("SELECT value FROM sum", Long.class);
        Assertions.assertEquals(expectedTimestamp, actualTimestamp);
        Assertions.assertEquals(expectedSum, actualSum);
        Timestamp actual = jdbcTemplate.queryForObject("SELECT * FROM last_record", Timestamp.class);
        Assertions.assertEquals(expectedTimestamp, actual);
    }

    @Test
    public void simpleTestGetInRange() {
        Timestamp expected = new Timestamp(System.currentTimeMillis());
        sumDao.updateSum(100, expected, expected);
        var actual = sumDao.getInRange(new Timestamp(0), new Timestamp(System.currentTimeMillis()));
        Assertions.assertEquals(1, actual.size());
        Timestamp actualTime = jdbcTemplate.queryForObject("SELECT * FROM last_record", Timestamp.class);
        Assertions.assertEquals(expected, actualTime);
    }

    @Test
    public void testGetInRangeEarlyAndLately() throws InterruptedException {
        Timestamp tooEarly = new Timestamp(System.currentTimeMillis());
        sumDao.updateSum(100, tooEarly, tooEarly);
        Timestamp minTimestamp = new Timestamp(System.currentTimeMillis());
        Timestamp maxTimestamp = new Timestamp(System.currentTimeMillis());
        Thread.sleep(500);
        Timestamp tooLate = new Timestamp(System.currentTimeMillis());
        sumDao.updateSum(100, tooLate, tooLate);
        var actual = sumDao.getInRange(minTimestamp, maxTimestamp);
        Assertions.assertEquals(0, actual.size());
    }
}
