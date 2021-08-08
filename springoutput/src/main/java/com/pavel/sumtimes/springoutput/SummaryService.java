package com.pavel.sumtimes.springoutput;

import com.pavel.sumtimes.commons.*;
import com.pavel.sumtimes.commons.serde.SumtimesDeserializer;
import com.pavel.sumtimes.springoutput.dao.SumDao;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

/**Read incoming kafka messages in endless loop. Service summarize receiving values from kafka with same quantum(some time window.
 * For example, we have quantum 3 sec, so collect every values from 9 sec to 12 sec will be stored in quantum 9).
 * Sum process flows until message with next quantum will arrive. Than update database and local cache with current sum and linked time and start to calculate next values.Try to successfully update
 * database as long as possible. If skipped some messages and received them lately, when next quantum calculated, sum
 * them to current value.
 */
@Service
public class SummaryService extends AbstractRunner {

    private final SumDao sumDAO;
    private final Cache cache;
    private final MeterRegistry meterRegistry;
    private final AtomicLong dbExceptionCounter = new AtomicLong(0);
    private final AtomicLong currentQuantum =  new AtomicLong(0);
    private volatile Consumer<String, Sumtimes.Sumtime> consumer;
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;
    private final long quantumMs;
    private long currentSum = 0;

    @Autowired
    public SummaryService(SumDao sumDAO, MeterRegistry meterRegistry, Cache cache, @Value("${app.quantum}") long quantumMs) {
        this.sumDAO = sumDAO;
        this.meterRegistry = meterRegistry;
        this.cache = cache;
        this.quantumMs =quantumMs;
    }

    public void setConsumer(Consumer<String, Sumtimes.Sumtime> consumer) {
        this.consumer = consumer;
    }

    @PostConstruct
    public void init() {
        Properties kafkaProps = new Properties();
        kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SumtimesDeserializer.class);
        kafkaProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG, AppAttributes.Consumer.groupId);
        kafkaProps.put(ConsumerConfig.CLIENT_ID_CONFIG, AppAttributes.Consumer.clientId);
        consumer = new KafkaConsumer<>(kafkaProps);
        try {
            TopicPartition topicPart = new TopicPartition(AppAttributes.sixtyNumTopicName, 0);
            consumer.assign(Collections.singletonList(topicPart));
            long lastRecordTimeMs = sumDAO.getLastRecordTime().getTime();
            var offsets = consumer.offsetsForTimes(Map.of(topicPart,  lastRecordTimeMs));
            if (offsets != null) {
                OffsetAndTimestamp offsetAndTimestamp = offsets.get(topicPart);
                if (offsetAndTimestamp != null && offsets.get(topicPart).offset()!=0) {
                    consumer.seek(topicPart, offsets.get(topicPart).offset()+1);
                    logger.info("Offset set to " + offsets.get(topicPart).offset()+1);
                }
            }
            currentQuantum.set((lastRecordTimeMs/quantumMs)*quantumMs);
        } catch (Exception e) {
            throw new RuntimeException("Failed to init Summary Service with following exception", e);
        }
        meterRegistry.gauge("dbExceptionCounter", dbExceptionCounter, AtomicLong::get);
        meterRegistry.gauge("currentMinute", currentQuantum, AtomicLong::get);
    }

    /**Poll messages from kafka. Summarize received values from kafka in {@link #currentSum}, until timestamp of kafka's record
     * reach next quantum. Then try to update database and cache with {@link #currentSum} and {@link #currentQuantum}
     * until success or shutdown of service. If receive empty message, so update last time in database and cache.
     * If skipped some messages and received them lately, when next minutes calculated, sum them to current value.
     * @throws WakeupException if calling poll due closing
     * @throws InterruptException when interrupt service due closing
     */
    @Override
    public void runIteration() throws InterruptedException, WakeupException, InterruptException {
        var records = consumer.poll(Duration.ofMillis(100));
        for (var record : records) {
            long recordQuantumMs = (record.timestamp()/quantumMs)*quantumMs;

            if (currentQuantum.get() < recordQuantumMs) {
                update(currentSum, currentQuantum.get(), recordQuantumMs);
                currentQuantum.set(recordQuantumMs);
                currentSum = 0;
                consumer.commitSync();
            }

            if (record.value().hasValue()) {
                currentSum += record.value().getValue().getValue();
            }
        }
    }

    @Override
    public void loggingStart() {
        logger.info("Starting SummaryServer");
    }

    @Override
    public void interrupt() {
        consumer.wakeup();
    }

    @Override
    public void specFinally() {
        consumer.close(Duration.ofMillis(1000));
    }
    
    /**Endlessly try to update database. return only if successfully updated db or {@link AbstractRunner#stop()} called
     * @param sum value to write in database and cache
     * @param sumTime time of sum to write in database and cache
     * @param lastRecordTime time of last acquired message from kafka
     * @throws InterruptedException if {@link AbstractRunner#stop()} called
     */
    private void update(long sum, long sumTime, long lastRecordTime) throws InterruptedException {
        while (true) {
            if (closed.get()) {
                throw new InterruptedException();
            }
            try {
                Timestamp lastRecordTimestamp = new Timestamp(lastRecordTime);
                if (sum != 0) {
                    Timestamp sumTimestamp = new Timestamp(sumTime);
                    sumDAO.updateSum(sum, sumTimestamp, lastRecordTimestamp);
                    try {
                        cache.put(sumTimestamp,lastRecordTimestamp, sum);
                    } catch (OutOfTimeException ignore) {}//will not happened in current version of service
                } else  {
                    sumDAO.updateLastRecord(lastRecordTimestamp);
                    cache.moveTime(lastRecordTimestamp);
                }
                return;
            } catch (Exception e) {
                logger.warn("Something occurred due database operations with sum = {} and time = {} ms",sum,
                        lastRecordTime, e);
                dbExceptionCounter.incrementAndGet();
                //noinspection BusyWait
                Thread.sleep(1000);
            }
        }
    }
}
