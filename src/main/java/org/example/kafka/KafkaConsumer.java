package org.example.kafka;

import org.example.WebSocketHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Service
public class KafkaConsumer {
    WebSocketHandler webSocketHandler;

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.group-id}")
    public void consume(String message) throws IOException {
        if (webSocketHandler != null) {
            TextMessage convertedMessage = new TextMessage(message);
            webSocketHandler.broadcast(convertedMessage);
        }
    }

    public void setWebSocketHandler(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }
}