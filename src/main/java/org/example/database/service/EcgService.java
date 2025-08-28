package org.example.database.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.database.model.Ecg;
import org.example.database.model.EcgSample;
import org.example.database.model.Movesense;
import org.example.database.model.Pico;
import org.example.database.repository.EcgRepository;
import org.example.database.repository.EcgSampleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcgService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EcgService.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PicoService picoService;

    @Autowired
    private MovesenseService movesenseService;

    @Autowired
    private EcgRepository ecgRepository;

    @Autowired
    private EcgSampleRepository ecgSampleRepository;

    public void handleJson(String message) throws JsonProcessingException {
        Ecg ecg = objectMapper.readValue(message, Ecg.class);
        Pico pico = ecg.getPico();
        Movesense movesense = ecg.getMovesense();

        if (!picoService.existsInDatabase(pico)) {
            picoService.save(pico);
        }

        if (!movesenseService.existsInDatabase(movesense)) {
            movesenseService.save(movesense);
        }

        saveEcgData(ecg);
        saveEcgSamples(ecg.getEcgSamples());
    }

    public Ecg findEcgById(long id) {
        Ecg ecg = ecgRepository.findById(id);
        List<EcgSample> samples = ecgSampleRepository.findByEcgId(id);
        ecg.setEcgSamples(samples);
        return ecg;
    }

    public List<Ecg> findEcgByTimestampUtcBetween(int start, int end) {
        List<Ecg> ecgList = ecgRepository.findByTimestampUtcBetween(start, end);
        for (Ecg ecg : ecgList) {
            List<EcgSample> samples = ecgSampleRepository.findByEcgId(ecg.getId());
            ecg.setEcgSamples(samples);
        }
        return ecgList;
    }

    public void saveEcgData(Ecg ecg) {
        ecgRepository.save(ecg);
        LOGGER.info("ECG data saved to database = '{}'", ecg);
    }

    public void saveEcgSamples(List<EcgSample> samples) {
        for (EcgSample sample : samples) {
            ecgSampleRepository.save(sample);
            LOGGER.info("ECG sample saved to database = '{}'", sample);
        }
    }
}
