package com.pavel.sumtimes.commons.serde;

import com.google.protobuf.InvalidProtocolBufferException;
import com.pavel.sumtimes.commons.Sumtimes;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SumtimesDeserializer implements Deserializer<Sumtimes.Sumtime> {
    private static final Logger logger = LoggerFactory.getLogger(SumtimesDeserializer.class);
    @Override
    public Sumtimes.Sumtime deserialize(String topic, byte[] data) {
        try {
            return Sumtimes.Sumtime.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            logger.error("Unparseable message arrived");
            throw new RuntimeException("Unparseable message", e);
        }
    }
}
