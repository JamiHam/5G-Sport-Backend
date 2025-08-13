package org.example.database.repository;

import org.example.database.model.HeartRate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HeartRateRepository extends CrudRepository<HeartRate, Long> {
    HeartRate findById(long id);
    List<HeartRate> findByTimestampUtcBetween(int start, int end);
}
