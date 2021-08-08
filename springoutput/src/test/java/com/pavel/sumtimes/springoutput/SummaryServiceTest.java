package com.pavel.sumtimes.springoutput;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.pavel.sumtimes.commons.AppAttributes;
import com.pavel.sumtimes.commons.Sumtimes;
import com.pavel.sumtimes.springoutput.dao.SumDao;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.record.TimestampType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SummaryServiceTest {

    private final static int periods = 2;
    private final static long quantum = 60000;
    private final static long periodLengthQuantums = 86400000;
    private final SumDao sumDao= Mockito.mock(SumDao.class);
    private MockConsumer<String, Sumtimes.Sumtime> mockConsumer;
    private final SummaryService testService = new SummaryService(sumDao, new SimpleMeterRegistry(),
            new AppCache(periods, quantum, periodLengthQuantums, 0, new Timestamp(0)), quantum);

    @BeforeEach
    void initConsumer(){
        mockConsumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        mockConsumer.assign(Collections.singletonList(new TopicPartition(AppAttributes.sixtyNumTopicName, 0)));
        mockConsumer.updateBeginningOffsets(Map.of(new TopicPartition(AppAttributes.sixtyNumTopicName, 0), 0L));
        testService.setConsumer(mockConsumer);
    }

    @Test
    void updateWithValue() throws InterruptedException {
        Sumtimes.Sumtime protoValue = Sumtimes.Sumtime.newBuilder().setValue(
                Sumtimes.Sumtime.Value.newBuilder().setValue(100).build()).build();
        long testRecordTime =System.currentTimeMillis();
        mockConsumer.addRecord(new ConsumerRecord<>
                (AppAttributes.sixtyNumTopicName, 0, 0L, testRecordTime,
                        TimestampType.CREATE_TIME, ConsumerRecord.NULL_CHECKSUM, ConsumerRecord.NULL_SIZE,
                        ConsumerRecord.NULL_SIZE,null, protoValue));
        testService.runIteration();
        Mockito.verify(sumDao).updateLastRecord(Mockito.any());
    }

    @Test
    public void updateSumAfterMinute() throws InterruptedException {
        Sumtimes.Sumtime protoValue = Sumtimes.Sumtime.newBuilder().setValue(
                Sumtimes.Sumtime.Value.newBuilder().setValue(100).build()).build();
        long testRecordTime =System.currentTimeMillis();
        mockConsumer.addRecord(new ConsumerRecord<>
                (AppAttributes.sixtyNumTopicName, 0, 0L, testRecordTime,
                        TimestampType.CREATE_TIME, ConsumerRecord.NULL_CHECKSUM, ConsumerRecord.NULL_SIZE,
                        ConsumerRecord.NULL_SIZE,null, protoValue));
        mockConsumer.addRecord(new ConsumerRecord<>
                (AppAttributes.sixtyNumTopicName, 0, 1L, testRecordTime+60000,
                        TimestampType.CREATE_TIME, ConsumerRecord.NULL_CHECKSUM, ConsumerRecord.NULL_SIZE,
                        ConsumerRecord.NULL_SIZE,null, protoValue));
        ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Timestamp> timeCapture = ArgumentCaptor.forClass(Timestamp.class);
        testService.runIteration();
        Mockito.verify(sumDao).updateLastRecord(timeCapture.capture());
        Mockito.verify(sumDao).updateSum(valueCapture.capture(), Mockito.any(), Mockito.any());
        Assertions.assertEquals(100, valueCapture.getValue());
        Assertions.assertEquals(1, timeCapture.getAllValues().size());
    }

    @Test
    public void moveForwardLastRecordTimeWithoutValue() throws InterruptedException {
        Sumtimes.Sumtime sixtyMinProto = Sumtimes.Sumtime.newBuilder().build();
        ArgumentCaptor<Timestamp> timeCapture = ArgumentCaptor.forClass(Timestamp.class);
        mockConsumer.addRecord(new ConsumerRecord<>
                (AppAttributes.sixtyNumTopicName, 0, 0L, System.currentTimeMillis(),
                        TimestampType.CREATE_TIME, ConsumerRecord.NULL_CHECKSUM, ConsumerRecord.NULL_SIZE,
                        ConsumerRecord.NULL_SIZE,null, sixtyMinProto));
        testService.runIteration();
        Mockito.verify(sumDao).updateLastRecord(timeCapture.capture());
        Assertions.assertEquals(1, timeCapture.getAllValues().size());
    }

    @Test
    public void getRecordsWithSameMinute() throws InterruptedException {
        Sumtimes.Sumtime sixtyMinProto = Sumtimes.Sumtime.newBuilder().build();
        long startTime = 60000*3;
        long sameMinute = 60000*3+100;
        long sameMinuteToo = 60000*3 +59999;
        mockConsumer.addRecord(new ConsumerRecord<>
                (AppAttributes.sixtyNumTopicName, 0, 0L, startTime,
                        TimestampType.CREATE_TIME, ConsumerRecord.NULL_CHECKSUM, ConsumerRecord.NULL_SIZE,
                        ConsumerRecord.NULL_SIZE,null, sixtyMinProto));
        mockConsumer.addRecord(new ConsumerRecord<>
                (AppAttributes.sixtyNumTopicName, 0, 0L, sameMinute,
                        TimestampType.CREATE_TIME, ConsumerRecord.NULL_CHECKSUM, ConsumerRecord.NULL_SIZE,
                        ConsumerRecord.NULL_SIZE,null, sixtyMinProto));
        mockConsumer.addRecord(new ConsumerRecord<>
                (AppAttributes.sixtyNumTopicName, 0, 0L, sameMinuteToo,
                        TimestampType.CREATE_TIME, ConsumerRecord.NULL_CHECKSUM, ConsumerRecord.NULL_SIZE,
                        ConsumerRecord.NULL_SIZE,null, sixtyMinProto));
        testService.runIteration();
        Mockito.verify(sumDao, Mockito.times(1)).updateLastRecord(Mockito.any());
    }

    @Test
    @Disabled
    public void daoExceptionThrow() throws InterruptedException {
        Sumtimes.Sumtime sixtyMinProto = Sumtimes.Sumtime.newBuilder().build();
        mockConsumer.addRecord(new ConsumerRecord<>
                (AppAttributes.sixtyNumTopicName, 0, 0L, System.currentTimeMillis(),
                        TimestampType.CREATE_TIME, ConsumerRecord.NULL_CHECKSUM, ConsumerRecord.NULL_SIZE,
                        ConsumerRecord.NULL_SIZE,null, sixtyMinProto));
        Mockito.doAnswer(invocation -> { throw new Exception();}).when(sumDao).updateLastRecord(Mockito.any());
        testService.runIteration();
    }

    @Test
    public void kafkaExceptionInvoke() {
        mockConsumer.setPollException(new KafkaException());
        Assertions.assertThrows(KafkaException.class, testService::runIteration);
    }

    @Test
    public void unexpectedShutdownWithWakeupExceptionFromKafka() throws InterruptedException {
        final Logger logger = (Logger) LoggerFactory.getLogger(SummaryService.class);
        final ListAppender<ILoggingEvent> events = new ListAppender<>();
        events.start();
        logger.addAppender(events);
        mockConsumer.setPollException(new WakeupException());
        testService.start();
        Thread.sleep(500);
        List<ILoggingEvent> logsList = events.list;
        Assertions.assertEquals(2, logsList.size());
        Assertions.assertFalse(testService.isRunning());
    }
    @Test
    public void unexpectedShutdownWithInterruptedExceptionFromKafka()  {
        final Logger logger = (Logger) LoggerFactory.getLogger(SummaryService.class);
        final ListAppender<ILoggingEvent> events = new ListAppender<>();
        events.start();
        logger.addAppender(events);
        mockConsumer.setPollException(new InterruptException("test"));
        testService.start();
        //noinspection ResultOfMethodCallIgnored
        Thread.interrupted();//clear interrupt status from InterruptException above
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignore) { }
        List<ILoggingEvent> logsList = events.list;
        Assertions.assertEquals(2, logsList.size());//start info and warn interrupt
        Assertions.assertFalse(testService.isRunning());
    }
}
