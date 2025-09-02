package org.example.kafka;

import org.example.database.service.EcgService;
import org.example.database.service.GnssService;
import org.example.database.service.HeartRateService;
import org.example.database.service.ImuService;
import org.example.websocket.WebSocketHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KafkaConsumerTest {
    @Mock
    private WebSocketHandler webSocketHandler;

    @Mock
    private ImuService imuService;

    @Mock
    private HeartRateService heartRateService;

    @Mock
    private EcgService ecgService;

    @Mock
    private GnssService gnssService;

    @InjectMocks
    private KafkaConsumer kafkaConsumer = new KafkaConsumer();

    private String message;

    @BeforeAll
    public void setDatabaseEnabled() {
        message = "{\"Name\":\"Test\"}";
        ReflectionTestUtils.setField(kafkaConsumer, "databaseEnabled", true);
    }

    @Test
    public void imuIsSavedToDatabase() throws IOException {
        kafkaConsumer.consume(message, "sensors.imu");
        verify(imuService).handleJson(message);
    }

    @Test
    public void HeartRateIsSavedToDatabase() throws IOException {
        kafkaConsumer.consume(message, "sensors.hr");
        verify(heartRateService).handleJson(message);
    }

    @Test
    public void ecgIsSavedToDatabase() throws IOException {
        kafkaConsumer.consume(message, "sensors.ecg");
        verify(ecgService).handleJson(message);
    }

    @Test
    public void gnssIsSavedToDatabase() throws IOException {
        kafkaConsumer.consume(message, "sensors.gnss");
        verify(gnssService).handleJson(message);
    }

    @Test
    public void topicIsAddedToImuData() throws IOException {
        kafkaConsumer.consume(message, "sensors.imu");
        verify(webSocketHandler).broadcast(new TextMessage("{\"Name\":\"Test\",\"Topic\":\"sensors.imu\"}"));
    }

    @Test
    public void topicIsAddedToHeartRateData() throws IOException {
        kafkaConsumer.consume(message, "sensors.hr");
        verify(webSocketHandler).broadcast(new TextMessage("{\"Name\":\"Test\",\"Topic\":\"sensors.hr\"}"));
    }

    @Test
    public void topicIsAddedToEcgData() throws IOException {
        kafkaConsumer.consume(message, "sensors.ecg");
        verify(webSocketHandler).broadcast(new TextMessage("{\"Name\":\"Test\",\"Topic\":\"sensors.ecg\"}"));
    }

    @Test
    public void topicIsAddedToGnssData() throws IOException {
        kafkaConsumer.consume(message, "sensors.gnss");
        verify(webSocketHandler).broadcast(new TextMessage("{\"Name\":\"Test\",\"Topic\":\"sensors.gnss\"}"));
    }
}
