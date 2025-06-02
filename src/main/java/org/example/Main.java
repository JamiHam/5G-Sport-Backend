package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(Main.class);

        String bootstrapServer = "127.0.0.1:9092";
        String groupId = "5G-Sport";
        String topic = "sensor_data";

        log.info("Creating the consumer thread");
        Consumer consumer = new Consumer(bootstrapServer, groupId, topic);

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        // Add a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Detected a shutdown");
            consumer.shutDown();
        }));
    }
}