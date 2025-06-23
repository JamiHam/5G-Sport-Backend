package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.kafka.KafkaProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws InterruptedException, JsonProcessingException {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        SensorData data = new SensorData();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(data);

        KafkaProducer producer = context.getBean(KafkaProducer.class);
        producer.sendMessage(json);
    }
}