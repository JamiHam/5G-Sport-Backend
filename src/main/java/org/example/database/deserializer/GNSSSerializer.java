package org.example.database.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.database.model.GNSS;

import java.io.IOException;

public class GNSSSerializer extends JsonSerializer<GNSS> {
    @Override
    public void serialize(GNSS gnss, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("Pico_ID", gnss.getPico().getId());
        jsonGenerator.writeStringField("Date", gnss.getDate());
        jsonGenerator.writeNumberField("Latitude", gnss.getLatitude());
        jsonGenerator.writeNumberField("Longitude", gnss.getLongitude());
        jsonGenerator.writeNumberField("Timestamp_UTC", gnss.getTimestampUTC());
        jsonGenerator.writeNumberField("Timestamp_ms", gnss.getTimestampUTC());

        jsonGenerator.writeEndObject();
    }
}
