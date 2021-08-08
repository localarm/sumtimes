package com.pavel.sumtimes.springinput;

import com.pavel.sumtimes.commons.AppAttributes;
import com.pavel.sumtimes.commons.Sumtimes;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class InputController{

    private final static Logger LOGGER = LoggerFactory.getLogger(InputController.class);
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;
    private KafkaProducer<String, Sumtimes.Sumtime> producer;

    @PostConstruct
    public void init() {
        producer = KafkaProducerFactory.getProducer("InputProducer", bootstrapServers);
    }

    @PreDestroy
    public void close() {
        producer.close(Duration.ofMillis(500));
    }

    @RequestMapping(value = "/input", method = RequestMethod.GET)
    public ModelAndView getView(){
        return new ModelAndView("input");
    }

    @RequestMapping(value = "/input", method = RequestMethod.POST)
    public ModelAndView sendValue(@RequestParam("value") Long value) {
        Sumtimes.Sumtime protoValue = Sumtimes.Sumtime.newBuilder().setValue(
                Sumtimes.Sumtime.Value.newBuilder().setValue(value).build()).build();
        ProducerRecord<String, Sumtimes.Sumtime> record = new ProducerRecord<>(AppAttributes.sixtyNumTopicName,
                protoValue);
        var kafkaMetadataResponse = producer.send(record);
        try {
            kafkaMetadataResponse.get();// wait for success send to kafka
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.warn("Failed to send message to kafka with value {}", value, e);
        }
        return new ModelAndView("input", Map.of("success", true));
    }

    @ExceptionHandler(KafkaException.class)
    public ModelAndView handleKafkaException() {
        return new ModelAndView("input", Map.of("success", false));
    }

    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException() {
        return new ModelAndView("input", Map.of("success", false));
    }


}
