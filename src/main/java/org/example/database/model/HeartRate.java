package org.example.database.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.example.database.deserializer.HeartRateDeserializer;
import org.example.database.deserializer.HeartRateSerializer;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "heart_rate")
@JsonSerialize(using = HeartRateSerializer.class)
@JsonDeserialize(using = HeartRateDeserializer.class)
public class HeartRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "average_bpm")
    private double averageBPM;

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
    private List<RrData> rrData = new ArrayList<>();

    public HeartRate() {}

    public Long getId() {
        return id;
    }

    public double getAverageBPM() {
        return averageBPM;
    }

    public void setAverageBPM(double averageBPM) {
        this.averageBPM = averageBPM;
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

    public List<RrData> getRrData() {
        return rrData;
    }

    public void setRrData(List<RrData> rrData) {
        this.rrData = rrData;
    }

    public void addRrData(RrData data) {
        rrData.add(data);
    }

    @Override
    public String toString() {
        return "{ id: " + id
                + ", average_bpm: " + averageBPM
                + ", timestamp_utc: " + timestampUTC
                + ", timestamp_ms: " + timestampMs
                + ", pico_id: " + pico.getId()
                + ", movesense_id: " + movesense.getId()
                + " }";
    }
}
