package org.example.database.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.database.model.Gnss;
import org.example.database.model.Pico;

import java.io.IOException;

public class GnssDeserializer extends JsonDeserializer<Gnss> {
    @Override
    public Gnss deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode gnssNode = jsonParser.getCodec().readTree(jsonParser);

        Pico pico = new Pico();
        pico.setId(gnssNode.get("Pico_ID").textValue());

        Gnss gnss = new Gnss();
        gnss.setPico(pico);
        gnss.setLatitude(gnssNode.get("Latitude").doubleValue());
        gnss.setLongitude(gnssNode.get("Longitude").doubleValue());
        gnss.setFixQ(gnssNode.get("FixQ").intValue());
        gnss.setTimestampUtc(gnssNode.get("Timestamp_UTC").intValue());
        gnss.setTimestampMs(gnssNode.get("Timestamp_ms").intValue());

        return gnss;
    }
}
