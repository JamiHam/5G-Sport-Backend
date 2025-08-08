package org.example.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.database.model.*;
import org.example.database.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataService.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    PicoRepository picoRepository;

    @Autowired
    MovesenseRepository movesenseRepository;

    @Autowired
    IMURepository imuRepository;

    @Autowired
    IMUCoordinateRepository imuCoordinateRepository;

    @Autowired
    HeartRateRepository heartRateRepository;

    @Autowired
    RrDataRepository rrDataRepository;

    @Autowired
    ECGRepository ecgRepository;

    @Autowired
    ECGSampleRepository ecgSampleRepository;

    @Autowired
    GNSSRepository gnssRepository;

    public void handleIMUData(String message) throws JsonProcessingException {
        IMU imu = objectMapper.readValue(message, IMU.class);
        Pico pico = imu.getPico();
        Movesense movesense = imu.getMovesense();

        if (!picoExistsInDatabase(pico)) {
            savePico(pico);
        }

        if (!movesenseExistsInDatabase(movesense)) {
            saveMovesense(movesense);
        }

        saveIMUData(imu);
        saveIMUCoordinates(imu.getIMUCoordinates());
    }

    public void handleHeartRateData(String message) throws JsonProcessingException {
        HeartRate heartRate = objectMapper.readValue(message, HeartRate.class);
        Pico pico = heartRate.getPico();
        Movesense movesense = heartRate.getMovesense();

        if (!picoExistsInDatabase(pico)) {
            savePico(pico);
        }

        if (!movesenseExistsInDatabase(movesense)) {
            saveMovesense(movesense);
        }

        saveHeartRateData(heartRate);
        saveRrData(heartRate.getRrData());
    }

    public void handleECGData(String message) throws JsonProcessingException {
        ECG ecg = objectMapper.readValue(message, ECG.class);
        Pico pico = ecg.getPico();
        Movesense movesense = ecg.getMovesense();

        if (!picoExistsInDatabase(pico)) {
            savePico(pico);
        }

        if (!movesenseExistsInDatabase(movesense)) {
            saveMovesense(movesense);
        }

        saveECGData(ecg);
        saveECGSamples(ecg.getECGSamples());
    }

    public void handleGNSSData(String message) throws JsonProcessingException {
        GNSS gnss = objectMapper.readValue(message, GNSS.class);
        Pico pico = gnss.getPico();

        if (!picoExistsInDatabase(pico)) {
            savePico(pico);
        }

        saveGNSSData(gnss);
    }

    private boolean picoExistsInDatabase(Pico pico) {
        return picoRepository.existsById(pico.getId());
    }

    private boolean movesenseExistsInDatabase(Movesense movesense) {
        return movesenseRepository.existsById(movesense.getId());
    }

    private void savePico(Pico pico) {
        picoRepository.save(pico);
        LOGGER.info("Raspberry Pi Pico saved to database = '{}'", pico.getId());
    }

    private void saveGNSSData(GNSS gnss) {
        gnssRepository.save(gnss);
        LOGGER.info("GNSS data saved to database = '{}'", gnss);
    }

    private void saveIMUData(IMU imu) {
        imuRepository.save(imu);
        LOGGER.info("IMU data saved to database = '{}'", imu);
    }

    private void saveHeartRateData(HeartRate heartRate) {
        heartRateRepository.save(heartRate);
        LOGGER.info("Heart rate data saved to database = '{}'", heartRate);
    }

    private void saveMovesense(Movesense movesense) {
        movesenseRepository.save(movesense);
        LOGGER.info("Movesense saved to database = '{}'", movesense.getId());
    }

    private void saveECGData(ECG ecg) {
        ecgRepository.save(ecg);
        LOGGER.info("ECG data saved to database = '{}'", ecg);
    }

    private void saveIMUCoordinates(ArrayList<IMUCoordinate> coordinates) {
        for (IMUCoordinate coordinate : coordinates) {
            imuCoordinateRepository.save(coordinate);
            LOGGER.info("IMU coordinate saved to database = '{}'", coordinate);
        }
    }

    private void saveRrData(ArrayList<RrData> rrDataArray) {
        for (RrData rrData : rrDataArray) {
            rrDataRepository.save(rrData);
            LOGGER.info("rr data saved to database = '{}'", rrData);
        }
    }

    private void saveECGSamples(ArrayList<ECGSample> samples) {
        for (ECGSample sample : samples) {
            ecgSampleRepository.save(sample);
            LOGGER.info("ECG sample saved to database = '{}'", sample);
        }
    }
}
