package org.example;

import org.example.kafka.KafkaConsumer;
import org.example.kafka.KafkaProducer;
import org.example.websocket.WebSocketHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = { KafkaConsumer.class, KafkaProducer.class })
@EnableAutoConfiguration
@EmbeddedKafka(bootstrapServersProperty = "spring.kafka.bootstrap-servers")
public class EmbeddedKafkaIntegrationTest {
    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;

    @MockitoBean
    private WebSocketHandler handler;

    private String data = "test data";

    @BeforeEach
    public void reset() {
        consumer.resetLatch();
    }

    private void sendDataAndAwait(String topic) throws InterruptedException {
        producer.send(topic, data);
        consumer.getLatch().await();
    }

    @Test
    public void imuDataIsConsumed() throws InterruptedException {
        sendDataAndAwait("sensors.imu");
        assertEquals(data, consumer.getPayload(), "afterConnectionEstablished method not executed");
    }

    @Test
    public void ecgDataIsConsumed() throws InterruptedException {
        sendDataAndAwait("sensors.ecg");
        assertEquals(data, consumer.getPayload(), "afterConnectionEstablished method not executed");
    }

    @Test
    public void hrDataIsConsumed() throws InterruptedException {
        sendDataAndAwait("sensors.hr");
        assertEquals(data, consumer.getPayload(), "afterConnectionEstablished method not executed");
    }

    @Test
    public void gnssDataIsConsumed() throws InterruptedException {
        sendDataAndAwait("sensors.gnss");
        assertEquals(data, consumer.getPayload(), "afterConnectionEstablished method not executed");
    }

    @Test
    public void broadcastIsCalled() throws InterruptedException, IOException {
        sendDataAndAwait("sensors.imu");
        verify(handler, times(1)).broadcast(new TextMessage(data));
    }
}