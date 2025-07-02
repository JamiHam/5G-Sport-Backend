package org.example.kafka;

import org.example.websocket.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Service
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private WebSocketHandler handler = null;
    private CountDownLatch latch = new CountDownLatch(1);
    private String payload;

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.group-id}")
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

    public void setHandler(WebSocketHandler handler) {
        this.handler = handler;
    }
}