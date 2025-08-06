package org.example.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.database.model.ECG;
import org.example.database.model.ECGSample;
import org.example.database.model.GNSS;
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
    ECGRepository ecgRepository;

    @Autowired
    ECGSampleRepository ecgSampleRepository;

    @Autowired
    GNSSRepository gnssRepository;

    public void saveIMUData(String message) {
        System.out.println("saveImuData called");
    }

    public void saveHeartRateData(String message) {
        System.out.println("saveHeartRateData called");
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

    private void saveECGSamples(ECG ecg) {
        for(int value : ecg.getSampleValues()) {
            ECGSample sample = new ECGSample();
            sample.setEcg(ecg);
            sample.setValue(value);
            ecgSampleRepository.save(sample);
        }
    }
}
