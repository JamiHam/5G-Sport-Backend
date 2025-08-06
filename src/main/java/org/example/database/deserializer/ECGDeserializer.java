package org.example.database.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.database.model.ECG;
import org.example.database.model.Movesense;
import org.example.database.model.Pico;

import java.io.IOException;
import java.util.Iterator;

public class ECGDeserializer extends JsonDeserializer<ECG> {
    @Override
    public ECG deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        ECG ecg = new ECG();
        ecg.setTimestampUTC(node.get("Timestamp_UTC").intValue());
        ecg.setTimestampMs(node.get("Timestamp_ms").intValue());

        Pico pico = new Pico();
        pico.setId(node.get("Pico_ID").textValue());
        ecg.setPico(pico);

        Movesense movesense = new Movesense();
        movesense.setId(node.get("Movesense_series").longValue());
        ecg.setMovesense(movesense);

        Iterator<JsonNode> samples = node.get("Samples").values();
        while(samples.hasNext()) {
            ecg.addSampleValue(samples.next().intValue());
        }

        return ecg;
    }
}
