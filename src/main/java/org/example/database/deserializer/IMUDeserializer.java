package org.example.database.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.database.model.IMU;
import org.example.database.model.IMUCoordinate;
import org.example.database.model.Movesense;
import org.example.database.model.Pico;

import java.io.IOException;
import java.util.Iterator;

public class IMUDeserializer extends JsonDeserializer<IMU> {
    @Override
    public IMU deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        IMU imu = new IMU();
        imu.setTimestampUTC(node.get("Timestamp_UTC").intValue());
        imu.setTimestampMs(node.get("Timestamp_ms").intValue());

        Pico pico = new Pico();
        pico.setId(node.get("Pico_ID").textValue());
        imu.setPico(pico);

        Movesense movesense = new Movesense();
        movesense.setId(node.get("Movesense_series").longValue());
        imu.setMovesense(movesense);

        Iterator<JsonNode> ArrayAcc = node.get("ArrayAcc").values();
        while (ArrayAcc.hasNext()) {
            IMUCoordinate coordinate = new IMUCoordinate();
            coordinate.setImu(imu);
            coordinate.setType("acc");
            setXYZ(coordinate, ArrayAcc.next());
            imu.addIMUCoordinate(coordinate);
        }

        Iterator<JsonNode> ArrayGyro = node.get("ArrayGyro").values();
        while (ArrayGyro.hasNext()) {
            IMUCoordinate coordinate = new IMUCoordinate();
            coordinate.setImu(imu);
            coordinate.setType("gyro");
            setXYZ(coordinate, ArrayGyro.next());
            imu.addIMUCoordinate(coordinate);
        }

        Iterator<JsonNode> ArrayMagn = node.get("ArrayMagn").values();
        while (ArrayMagn.hasNext()) {
            IMUCoordinate coordinate = new IMUCoordinate();
            coordinate.setImu(imu);
            coordinate.setType("magn");
            setXYZ(coordinate, ArrayMagn.next());
            imu.addIMUCoordinate(coordinate);
        }

        return imu;
    }

    private void setXYZ(IMUCoordinate coordinate, JsonNode node) {
        coordinate.setX(node.get("x").doubleValue());
        coordinate.setY(node.get("y").doubleValue());
        coordinate.setZ(node.get("z").doubleValue());
    }
}
