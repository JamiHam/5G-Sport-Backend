package org.example.database.repository;

import org.example.database.model.ECGSample;
import org.springframework.data.repository.CrudRepository;

public interface ECGSampleRepository extends CrudRepository<ECGSample, Long> {
}
