package com.pavel.sumtimes.springinput;

import com.pavel.sumtimes.commons.Sumtimes;
import com.pavel.sumtimes.commons.serde.SumtimesSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerFactory {

    public static KafkaProducer<String, Sumtimes.Sumtime> getProducer(String clientId, String bootstrapServers) {
        Properties kafkaProps = new Properties ();
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SumtimesSerializer.class);
        kafkaProps.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        kafkaProps.put(ProducerConfig.ACKS_CONFIG, "all");
        return new KafkaProducer<>(kafkaProps);
    }
}
