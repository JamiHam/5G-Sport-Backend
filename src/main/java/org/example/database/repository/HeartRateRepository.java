package org.example.database.repository;

import org.example.database.model.HeartRate;
import org.springframework.data.repository.CrudRepository;

public interface HeartRateRepository extends CrudRepository<HeartRate, Long> {
}
