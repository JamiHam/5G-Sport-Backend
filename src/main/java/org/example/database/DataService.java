package org.example.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.database.model.*;
import org.example.database.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataService.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    PicoRepository picoRepository;

    @Autowired
    MovesenseRepository movesenseRepository;

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

    public void saveIMUData(String message) {
        System.out.println("saveImuData called");
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
        saveRrData(heartRate);
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
        saveECGSamples(ecg);
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

    private void saveRrData(HeartRate heartRate) {
        for (RrData rrData : heartRate.getRrData()) {
            rrDataRepository.save(rrData);
            LOGGER.info("rr data saved to database = '{}'", rrData);
        }
    }

    private void saveECGSamples(ECG ecg) {
        for (ECGSample sample : ecg.getECGSamples()) {
            ecgSampleRepository.save(sample);
            LOGGER.info("ECG sample saved to database = '{}'", sample.toString());
        }
    }
}
