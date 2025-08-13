package org.example.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.database.model.*;
import org.example.database.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataService.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    PicoRepository picoRepository;

    @Autowired
    MovesenseRepository movesenseRepository;

    @Autowired
    ImuRepository imuRepository;

    @Autowired
    ImuCoordinateRepository imuCoordinateRepository;

    @Autowired
    HeartRateRepository heartRateRepository;

    @Autowired
    RrDataRepository rrDataRepository;

    @Autowired
    EcgRepository ecgRepository;

    @Autowired
    EcgSampleRepository ecgSampleRepository;

    @Autowired
    GnssRepository gnssRepository;

    public Imu findIMUById(long id) {
        Imu imu = imuRepository.findById(id);
        List<ImuCoordinate> coordinates = imuCoordinateRepository.findByImuId(imu.getId());
        imu.setImuCoordinates(coordinates);
        return imu;
    }

    public List<Imu> findImuByTimestampUtcBetween(int start, int end) {
        List<Imu> imuList = imuRepository.findByTimestampUtcBetween(start, end);
        for (Imu imu : imuList) {
            List<ImuCoordinate> coordinates = imuCoordinateRepository.findByImuId(imu.getId());
            imu.setImuCoordinates(coordinates);
        }
        return imuList;
    }

    public HeartRate findHeartRateById(long id) {
        HeartRate heartRate = heartRateRepository.findById(id);
        List<RrData> rrData = rrDataRepository.findByHeartRateId(id);
        heartRate.setRrData(rrData);
        return heartRate;
    }

    public List<HeartRate> findHeartRateByTimestampUtcBetween(int start, int end) {
        List<HeartRate> heartRateList = heartRateRepository.findByTimestampUtcBetween(start, end);
        for (HeartRate heartRate : heartRateList) {
            List<RrData> rrData = rrDataRepository.findByHeartRateId(heartRate.getId());
            heartRate.setRrData(rrData);
        }
        return heartRateList;
    }

    public Gnss findGnssById(long id) {
        return gnssRepository.findById(id);
    }

    public List<Gnss> findGnssByTimestampUtcBetween(int start, int end) {
        return gnssRepository.findByTimestampUtcBetween(start, end);
    }

    public Ecg findEcgById(long id) {
        Ecg ecg = ecgRepository.findById(id);
        List<EcgSample> samples = ecgSampleRepository.findByEcgId(ecg.getId());
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

    public void handleIMUData(String message) throws JsonProcessingException {
        Imu imu = objectMapper.readValue(message, Imu.class);
        Pico pico = imu.getPico();
        Movesense movesense = imu.getMovesense();

        if (!picoExistsInDatabase(pico)) {
            savePico(pico);
        }

        if (!movesenseExistsInDatabase(movesense)) {
            saveMovesense(movesense);
        }

        saveIMUData(imu);
        saveIMUCoordinates(imu.getImuCoordinates());
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

    public void handleEcgData(String message) throws JsonProcessingException {
        Ecg ecg = objectMapper.readValue(message, Ecg.class);
        Pico pico = ecg.getPico();
        Movesense movesense = ecg.getMovesense();

        if (!picoExistsInDatabase(pico)) {
            savePico(pico);
        }

        if (!movesenseExistsInDatabase(movesense)) {
            saveMovesense(movesense);
        }

        saveEcgData(ecg);
        saveEcgSamples(ecg.getEcgSamples());
    }

    public void handleGnssData(String message) throws JsonProcessingException {
        Gnss gnss = objectMapper.readValue(message, Gnss.class);
        Pico pico = gnss.getPico();

        if (!picoExistsInDatabase(pico)) {
            savePico(pico);
        }

        saveGnssData(gnss);
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

    private void saveGnssData(Gnss gnss) {
        gnssRepository.save(gnss);
        LOGGER.info("GNSS data saved to database = '{}'", gnss);
    }

    private void saveIMUData(Imu imu) {
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

    private void saveEcgData(Ecg ecg) {
        ecgRepository.save(ecg);
        LOGGER.info("ECG data saved to database = '{}'", ecg);
    }

    private void saveIMUCoordinates(List<ImuCoordinate> coordinates) {
        for (ImuCoordinate coordinate : coordinates) {
            imuCoordinateRepository.save(coordinate);
            LOGGER.info("IMU coordinate saved to database = '{}'", coordinate);
        }
    }

    private void saveRrData(List<RrData> rrDataArray) {
        for (RrData rrData : rrDataArray) {
            rrDataRepository.save(rrData);
            LOGGER.info("rr data saved to database = '{}'", rrData);
        }
    }

    private void saveEcgSamples(List<EcgSample> samples) {
        for (EcgSample sample : samples) {
            ecgSampleRepository.save(sample);
            LOGGER.info("ECG sample saved to database = '{}'", sample);
        }
    }
}
