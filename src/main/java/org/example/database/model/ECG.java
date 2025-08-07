package org.example.database.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import org.example.database.deserializer.ECGDeserializer;

import java.util.ArrayList;

@Entity
@Table(name = "ecg")
@JsonDeserialize(using = ECGDeserializer.class)
public class ECG {
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
    ArrayList<ECGSample> ecgSamples = new ArrayList<ECGSample>();

    public ECG() {}

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

    public ArrayList<ECGSample> getECGSamples() {
        return ecgSamples;
    }

    public void addECGSample(ECGSample sample) {
        ecgSamples.add(sample);
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
