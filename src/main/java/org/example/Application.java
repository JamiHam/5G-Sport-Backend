package org.example;

import org.example.kafka.KafkaConsumer;
import org.example.websocket.WebSocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        KafkaConsumer consumer = context.getBean(KafkaConsumer.class);
        WebSocketHandler handler = context.getBean(WebSocketHandler.class);
        consumer.setHandler(handler);
    }
}