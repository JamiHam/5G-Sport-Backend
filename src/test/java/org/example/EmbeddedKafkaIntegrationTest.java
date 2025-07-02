package org.example;

import org.example.kafka.KafkaConfig;
import org.example.kafka.KafkaConsumer;
import org.example.kafka.KafkaProducer;
import org.example.websocket.WebSocketHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = { KafkaConfig.class, KafkaConsumer.class, KafkaProducer.class })
@EmbeddedKafka(ports = 9092)
public class EmbeddedKafkaIntegrationTest {
    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;

    @Mock
    private WebSocketHandler handler;

    @Value("${spring.kafka.topic}")
    private String topic;

    @BeforeEach
    public void reset() {
        consumer.resetLatch();
    }

    @Test
    public void producedDataIsConsumed() throws InterruptedException {
        String data = "test data";
        producer.send(topic, data);
        consumer.getLatch().await();
        assertEquals(data, consumer.getPayload(), "afterConnectionEstablished method not executed");
    }

    @Test
    public void broadcastIsCalled() throws InterruptedException, IOException {
        consumer.setHandler(handler);
        String data = "test data";
        producer.send(topic, data);
        consumer.getLatch().await();
        verify(handler, times(1)).broadcast(new TextMessage(data));
    }
}