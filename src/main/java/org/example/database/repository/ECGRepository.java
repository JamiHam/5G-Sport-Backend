package org.example.database.repository;

import org.example.database.model.ECG;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ECGRepository extends CrudRepository<ECG, Long> {
    ECG findById(long id);
    List<ECG> findByTimestampUTCBetween(int start, int end);
}
