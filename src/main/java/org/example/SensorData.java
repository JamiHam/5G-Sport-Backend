package org.example;

import org.springframework.stereotype.Component;

@Component
public class SensorData {
    private String heartRate;
    private String ecg;
    private String imu;
    private String gnss;
    private String timeStamp;

    public SensorData() {}

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getEcg() {
        return ecg;
    }

    public void setEcg(String ecg) {
        this.ecg = ecg;
    }

    public String getImu() {
        return imu;
    }

    public void setImu(String imu) {
        this.imu = imu;
    }

    public String getGnss() {
        return gnss;
    }

    public void setGnss(String gnss) {
        this.gnss = gnss;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
