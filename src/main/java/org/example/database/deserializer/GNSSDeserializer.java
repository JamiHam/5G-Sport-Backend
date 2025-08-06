package org.example.database.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.database.model.GNSS;
import org.example.database.model.Pico;

import java.io.IOException;

public class GNSSDeserializer extends JsonDeserializer<GNSS> {
    @Override
    public GNSS deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode gnssNode = jsonParser.getCodec().readTree(jsonParser);

        Pico pico = new Pico();
        pico.setId(gnssNode.get("Pico_ID").textValue());

        GNSS gnss = new GNSS();
        gnss.setPico(pico);
        gnss.setDeviceId(gnssNode.get("GNSS_ID").textValue());
        gnss.setDate(gnssNode.get("Date").textValue());
        gnss.setLatitude(gnssNode.get("Latitude").doubleValue());
        gnss.setLongitude(gnssNode.get("Longitude").doubleValue());
        gnss.setTimestampUTC(gnssNode.get("Timestamp_UTC").intValue());
        gnss.setTimestampMs(gnssNode.get("Timestamp_ms").intValue());

        return gnss;
    }
}
