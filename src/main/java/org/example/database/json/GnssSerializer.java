package org.example.database.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.database.model.Gnss;

import java.io.IOException;

public class GnssSerializer extends JsonSerializer<Gnss> {
    @Override
    public void serialize(Gnss gnss, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("Pico_ID", gnss.getPico().getId());
        jsonGenerator.writeStringField("GNSS_ID", gnss.getDeviceId());
        jsonGenerator.writeStringField("Date", gnss.getDate());
        jsonGenerator.writeNumberField("Latitude", gnss.getLatitude());
        jsonGenerator.writeNumberField("Longitude", gnss.getLongitude());
        jsonGenerator.writeNumberField("Timestamp_UTC", gnss.getTimestampUtc());
        jsonGenerator.writeNumberField("Timestamp_ms", gnss.getTimestampUtc());

        jsonGenerator.writeEndObject();
    }
}
