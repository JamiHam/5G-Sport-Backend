package org.example.database.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.database.model.Gnss;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GnssDeserializerTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void jsonCanBeConvertedToGnssObject() throws IOException {
        Gnss gnss = objectMapper.readValue(new File("src/test/java/org/example/mock/data/gnss.json"), Gnss.class);

        assertEquals("e66130100f8c9928", gnss.getPico().getId(), "Pico ID was incorrect");
        assertEquals("device123", gnss.getDeviceId(), "Device ID was incorrect");
        assertEquals("1.1.2025", gnss.getDate(), "Date was incorrect");
        assertEquals(37.7749, gnss.getLatitude(), "Latitude was incorrect");
        assertEquals(-122.4194, gnss.getLongitude(), "Longitude was incorrect");
        assertEquals(30348, gnss.getTimestampUtc(), "UTC timestamp was incorrect");
        assertEquals(29889, gnss.getTimestampMs(), "Ms Timestamp was incorrect");
    }
}
