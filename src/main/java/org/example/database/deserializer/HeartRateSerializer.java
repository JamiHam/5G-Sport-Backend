package org.example.database.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.database.model.HeartRate;
import org.example.database.model.RrData;

import java.io.IOException;

public class HeartRateSerializer extends JsonSerializer<HeartRate> {
    @Override
    public void serialize(HeartRate heartRate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("Pico_ID", heartRate.getPico().getId());
        jsonGenerator.writeNumberField("Movesense_series", heartRate.getMovesense().getId());
        jsonGenerator.writeNumberField("average_bpm", heartRate.getAverageBPM());
        jsonGenerator.writeNumberField("Timestamp_UTC", heartRate.getTimestampUTC());
        jsonGenerator.writeNumberField("Timestamp_ms", heartRate.getTimestampMs());

        jsonGenerator.writeArrayFieldStart("rrData");
        for (RrData rrData : heartRate.getRrData()) {
            jsonGenerator.writeNumber(rrData.getValue());
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
