package org.example;

import org.example.kafka.KafkaConsumer;
import org.example.kafka.KafkaProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        WebSocketHandler webSocketHandler = context.getBean(WebSocketConfig.class).getWebSocketHandler();
        KafkaConsumer consumer = context.getBean(KafkaConsumer.class);
        consumer.setWebSocketHandler(webSocketHandler);

        KafkaProducer producer = context.getBean(KafkaProducer.class);
        producer.sendMessage("Hello world");
    }
}