package org.example.database.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.example.database.json.HeartRateDeserializer;
import org.example.database.json.HeartRateSerializer;

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
    private double averageBpm;

    @Column(name = "timestamp_utc")
    private int timestampUtc;

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

    public double getAverageBpm() {
        return averageBpm;
    }

    public void setAverageBpm(double averageBpm) {
        this.averageBpm = averageBpm;
    }

    public int getTimestampUtc() {
        return timestampUtc;
    }

    public void setTimestampUtc(int timestampUtc) {
        this.timestampUtc = timestampUtc;
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
                + ", average_bpm: " + averageBpm
                + ", timestamp_utc: " + timestampUtc
                + ", timestamp_ms: " + timestampMs
                + ", pico_id: " + pico.getId()
                + ", movesense_id: " + movesense.getId()
                + " }";
    }
}
