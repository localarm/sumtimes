package com.pavel.sumtimes.springinput;

import com.pavel.sumtimes.commons.AbstractRunner;
import com.pavel.sumtimes.commons.AppAttributes;
import com.pavel.sumtimes.commons.Sumtimes;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;


/**Send empty message every quantum of time to kafka
 */
@Service
public class IdleRunner extends AbstractRunner {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${app.quantum}")
    private long quantum;
    private volatile KafkaProducer<String, Sumtimes.Sumtime> producer;

    @PostConstruct
    public void init() {
        producer = KafkaProducerFactory.getProducer("IdleRunnerProducer", bootstrapServers);
    }

    @Override
    public void runIteration() throws InterruptedException {
        Sumtimes.Sumtime sumtimesProto = Sumtimes.Sumtime.newBuilder().build();
        ProducerRecord<String, Sumtimes.Sumtime> record = new ProducerRecord<>(AppAttributes.sixtyNumTopicName,
                sumtimesProto);
        producer.send(record);
        logger.info("sent idle message");
        Thread.sleep(quantum);
    }

    @Override
    public void loggingStart() {
        logger.info("Starting IdleRunner");
    }

    @Override
    public void interrupt() {
        logger.info("Shutting down IdleRunner");
    }

    @Override
    public void specFinally() {
        producer.close(Duration.ofMillis(500));
    }
}

