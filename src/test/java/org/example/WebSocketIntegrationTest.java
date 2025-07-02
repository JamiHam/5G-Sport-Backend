package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@SpringBootTest(classes = {WebSocketConfig.class, WebSocketHandler.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class WebSocketConnectionTest {
    @Autowired
    private WebSocketHandler handler;

    @LocalServerPort
    private int port;

    private WebSocketClient client;
    private ClientWebSocketHandler clientHandler;
    private CompletableFuture<String> future; // Represents a future result of an asynchronous operation.
    private WebSocketSession session;

    @BeforeEach
    public void setup() {
        future = new CompletableFuture<>();
        client = new StandardWebSocketClient();
        clientHandler = new ClientWebSocketHandler();
    }

    private void connect() {
        CompletableFuture<WebSocketSession> sessionFuture = client.execute(clientHandler, "ws://localhost:" + port + "/");
        session = sessionFuture.join();
    }

    private void disconnect() throws IOException {
        session.close();
    }

    @Test
    @Timeout(5)
    public void connectionEstablishedTest() throws IOException {
        clientHandler.setFuture(future);
        connect();

        // future.join() will be executed after future.complete() is called in clientHandler's afterConnectionEstablished() method.
        assertEquals("connection established", future.join(), "afterConnectionEstablished method not executed");

        clientHandler.setFuture(null);
        disconnect();
    }

    @Test
    @Timeout(5)
    public void connectionClosedTest() throws IOException {
        connect();
        clientHandler.setFuture(future);
        disconnect();
        assertEquals("connection closed", future.join(), "afterConnectionClosed method not executed");
    }

    @Test
    @Timeout(5)
    public void broadcastTest() throws IOException {
        connect();
        clientHandler.setFuture(future);
        handler.broadcast(new TextMessage("test"));
        assertEquals("test", future.join(), "handleTextMessage method not executed");

        clientHandler.setFuture(null);
        disconnect();
    }
}
