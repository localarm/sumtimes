package com.pavel.sumtimes.commons.serde;

import com.pavel.sumtimes.commons.Sumtimes;
import org.apache.kafka.common.serialization.Serializer;

public class SumtimesSerializer implements Serializer<Sumtimes.Sumtime> {

    @Override
    public byte[] serialize(String topic, Sumtimes.Sumtime data) {
        return data.toByteArray();
    }
}

