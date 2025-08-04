package org.example.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.database.model.Gnss;
import org.example.database.repository.GnssRepository;
import org.example.database.repository.PicoRepository;
import org.example.kafka.KafkaConsumer;
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
    GnssRepository gnssRepository;

    public void saveImuData(String message) {
        System.out.println("saveImuData called");
    }

    public void saveHeartRateData(String message) {
        System.out.println("saveHeartRateData called");
    }

    public void saveEcgData(String message) {
        System.out.println("saveEcgData called");
    }

    public void saveGnssData(String message) throws JsonProcessingException {
        Gnss gnss = objectMapper.readValue(message, Gnss.class);
        gnssRepository.save(gnss);

        LOGGER.info("GNSS data saved to database = '{}'", message);
    }
}
