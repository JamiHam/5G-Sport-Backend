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

    public void saveHeartRateData(String message) throws JsonProcessingException {
        System.out.println("saveHeartRateData called");

        HeartRate heartRate = objectMapper.readValue(message, HeartRate.class);
        heartRateRepository.save(heartRate);

        saveRrData(heartRate);

        LOGGER.info("Heart rate data saved to database = '{}'", message);
    }

    public void saveECGData(String message) throws JsonProcessingException {
        ECG ecg = objectMapper.readValue(message, ECG.class);
        ecgRepository.save(ecg);

        saveECGSamples(ecg);

        LOGGER.info("ECG data saved to database = '{}'", message);
    }

    public void saveGNSSData(String message) throws JsonProcessingException {
        GNSS gnss = objectMapper.readValue(message, GNSS.class);
        gnssRepository.save(gnss);

        LOGGER.info("GNSS data saved to database = '{}'", message);
    }

    private void saveRrData(HeartRate heartRate) {
        for (RrData rrData : heartRate.getRrData()) {
            rrDataRepository.save(rrData);
        }
    }

    private void saveECGSamples(ECG ecg) {
        for (ECGSample sample : ecg.getECGSamples()) {
            ecgSampleRepository.save(sample);
        }
    }
}
