package org.example.kafka;

import jakarta.annotation.PostConstruct;
import org.example.database.DataService;
import org.example.websocket.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Service
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private WebSocketHandler handler;

    private CountDownLatch latch = new CountDownLatch(1); // For testing.
    private String payload; // For testing.

    @KafkaListener(topics = "#{'${spring.kafka.topics}'.split(',')}", groupId = "${spring.kafka.group-id}")
    public void consume(String message) throws IOException {
        LOGGER.info("Received payload = '{}'", message);

        if (handler != null) {
            TextMessage convertedMessage = new TextMessage(message);
            handler.broadcast(convertedMessage);
        }

        payload = message;
        latch.countDown();
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