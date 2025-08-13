package org.example.database.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.database.model.IMU;
import org.example.database.model.IMUCoordinate;

import java.io.IOException;
import java.util.List;

public class IMUSerializer extends JsonSerializer<IMU> {
    @Override
    public void serialize(IMU imu, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("Pico_ID", imu.getPico().getId());
        jsonGenerator.writeNumberField("Movesense_series", imu.getMovesense().getId());
        jsonGenerator.writeNumberField("Timestamp_UTC", imu.getTimestampUTC());
        jsonGenerator.writeNumberField("Timestamp_ms", imu.getTimestampMs());

        jsonGenerator.writeArrayFieldStart("ArrayAcc");
        setCoordinates(jsonGenerator, imu.getIMUCoordinates(), "acc");
        jsonGenerator.writeEndArray();

        jsonGenerator.writeArrayFieldStart("ArrayGyro");
        setCoordinates(jsonGenerator, imu.getIMUCoordinates(), "gyro");
        jsonGenerator.writeEndArray();

        jsonGenerator.writeArrayFieldStart("ArrayMagn");
        setCoordinates(jsonGenerator, imu.getIMUCoordinates(), "magn");
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }

    private void setCoordinates(JsonGenerator jsonGenerator, List<IMUCoordinate> coordinates, String type) throws IOException {
        for (IMUCoordinate coordinate : coordinates) {
            if (coordinate.getType().equals(type)) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("x", coordinate.getX());
                jsonGenerator.writeNumberField("y", coordinate.getY());
                jsonGenerator.writeNumberField("z", coordinate.getZ());
                jsonGenerator.writeEndObject();
            }
        }

    }
}
