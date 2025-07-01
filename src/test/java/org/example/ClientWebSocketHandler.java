package org.example;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CompletableFuture;

public class ClientWebSocketHandler extends TextWebSocketHandler {
    private CompletableFuture<String> future;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (future != null) {
            future.complete("connection established");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (future != null) {
            future.complete("connection closed");
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Client received: " + message);
        if (future != null) {
            future.complete(message.getPayload());
        }
    }

    public void setFuture(CompletableFuture<String> future) {
        this.future = future;
    }
}
