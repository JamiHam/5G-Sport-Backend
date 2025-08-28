package org.example.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.database.service.*;
import org.example.websocket.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Service
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private WebSocketHandler handler;

    @Autowired
    private ImuService imuService;

    @Autowired
    private HeartRateService heartRateService;

    @Autowired
    private EcgService ecgService;

    @Autowired
    private GnssService gnssService;

    private CountDownLatch latch = new CountDownLatch(1); // For testing.
    private String payload; // For testing.

    @KafkaListener(topics = "#{'${spring.kafka.topics}'.split(',')}", groupId = "${spring.kafka.group-id}")
    public void consume(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws IOException {
        LOGGER.info("Received message from topic '{}', payload = '{}'", topic, message);

        broadcast(message);
        //saveToDatabase(message, topic);

        payload = message;
        latch.countDown();
    }

    private void broadcast(String message) throws IOException {
        if (handler != null) {
            TextMessage convertedMessage = new TextMessage(message);
            handler.broadcast(convertedMessage);
        }
    }

    private void saveToDatabase(String message, String topic) throws JsonProcessingException {
        switch (topic) {
            case "sensors.imu":
                imuService.handleJson(message);
                break;
            case "sensors.hr":
                heartRateService.handleJson(message);
                break;
            case "sensors.ecg":
                ecgService.handleJson(message);
                break;
            case "sensors.gnss":
                gnssService.handleJson(message);
                break;
        }
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getPayload() {
        return payload;
    }
}