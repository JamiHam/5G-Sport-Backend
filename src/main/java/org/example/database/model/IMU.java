package org.example.database.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import org.example.database.deserializer.IMUDeserializer;

import java.util.ArrayList;

@Entity
@Table(name = "imu")
@JsonDeserialize(using = IMUDeserializer.class)
public class IMU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp_utc")
    private int timestampUTC;

    @Column(name = "timestamp_ms")
    private int timestampMs;

    @ManyToOne
    @JoinColumn(name = "pico_id")
    private Pico pico;

    @ManyToOne
    @JoinColumn(name = "movesense_id")
    private Movesense movesense;

    @Transient
    ArrayList<IMUCoordinate> imuCoordinates = new ArrayList<IMUCoordinate>();

    public IMU() {}

    public Long getId() {
        return id;
    }

    public int getTimestampUTC() {
        return timestampUTC;
    }

    public void setTimestampUTC(int timestampUTC) {
        this.timestampUTC = timestampUTC;
    }

    public int getTimestampMs() {
        return timestampMs;
    }

    public void setTimestampMs(int timestampMs) {
        this.timestampMs = timestampMs;
    }

    public Pico getPico() {
        return pico;
    }

    public void setPico(Pico pico) {
        this.pico = pico;
    }

    public Movesense getMovesense() {
        return movesense;
    }

    public void setMovesense(Movesense movesense) {
        this.movesense = movesense;
    }

    public ArrayList<IMUCoordinate> getIMUCoordinates() {
        return imuCoordinates;
    }

    public void addIMUCoordinate(IMUCoordinate coordinate) {
        imuCoordinates.add(coordinate);
    }

    @Override
    public String toString() {
        return "{ id: " + id
                + ", timestamp_utc: " + timestampUTC
                + ", timestamp_ms: " + timestampMs
                + ", pico_id: " + pico.getId()
                + ", movesense_id: " + movesense.getId()
                + " }";
    }
}
