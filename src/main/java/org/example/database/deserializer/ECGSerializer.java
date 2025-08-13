package org.example.database.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.database.model.ECG;
import org.example.database.model.ECGSample;

import java.io.IOException;

public class ECGSerializer extends JsonSerializer<ECG> {
    @Override
    public void serialize(ECG ecg, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("Pico_ID", ecg.getPico().getId());
        jsonGenerator.writeNumberField("Movesense_series", ecg.getMovesense().getId());
        jsonGenerator.writeNumberField("Timestamp_UTC", ecg.getTimestampUTC());
        jsonGenerator.writeNumberField("Timestamp_ms", ecg.getTimestampMs());

        jsonGenerator.writeArrayFieldStart("Samples");
        for (ECGSample sample : ecg.getECGSamples()) {
            jsonGenerator.writeNumber(sample.getValue());
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
