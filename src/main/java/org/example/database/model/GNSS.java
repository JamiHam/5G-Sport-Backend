package org.example.database.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.example.database.deserializer.GNSSDeserializer;
import org.example.database.deserializer.GNSSSerializer;

@Entity
@Table(name = "gnss")
@JsonSerialize(using = GNSSSerializer.class)
@JsonDeserialize(using = GNSSDeserializer.class)
public class GNSS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id")
    private String deviceId;

    private String date;

    private double latitude;

    private double longitude;

    @Column(name = "timestamp_utc")
    private int timestampUTC;

    @Column(name = "timestamp_ms")
    private int timestampMs;

    @ManyToOne
    @JoinColumn(name = "pico_id")
    private Pico pico;

    public GNSS() { }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    @Override
    public String toString() {
        return "{ id: " + id
                + ", device_id: " + deviceId
                + ", date: " + date
                + ", latitude: " + latitude
                + ", longitude: " + longitude
                + ", timestamp_utc: " + timestampUTC
                + ", timestamp_ms: " + timestampMs
                + ", pico_id: " + pico.getId()
                + " }";
    }
}