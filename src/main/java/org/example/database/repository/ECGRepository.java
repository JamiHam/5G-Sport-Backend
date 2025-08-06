package org.example.database.repository;

import org.example.database.model.ECG;
import org.springframework.data.repository.CrudRepository;

public interface ECGRepository extends CrudRepository<ECG, Long> {
}
