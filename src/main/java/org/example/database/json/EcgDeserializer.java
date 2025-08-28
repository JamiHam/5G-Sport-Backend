package org.example.database.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.database.model.Ecg;
import org.example.database.model.EcgSample;
import org.example.database.model.Movesense;
import org.example.database.model.Pico;

import java.io.IOException;
import java.util.Iterator;

public class EcgDeserializer extends JsonDeserializer<Ecg> {
    @Override
    public Ecg deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Ecg ecg = new Ecg();
        ecg.setTimestampUtc(node.get("Timestamp_UTC").intValue());
        ecg.setTimestampMs(node.get("Timestamp_ms").intValue());

        Pico pico = new Pico();
        pico.setId(node.get("Pico_ID").textValue());
        ecg.setPico(pico);

        Movesense movesense = new Movesense();
        movesense.setId(node.get("Movesense_series").longValue());
        ecg.setMovesense(movesense);

        Iterator<JsonNode> samples = node.get("Samples").values();
        while(samples.hasNext()) {
            EcgSample sample = new EcgSample();
            sample.setValue(samples.next().intValue());
            sample.setEcg(ecg);
            ecg.addEcgSample(sample);
        }

        return ecg;
    }
}
