package org.example.database.repository;

import org.example.database.model.IMU;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IMURepository extends CrudRepository<IMU, Long> {
    IMU findById(long id);
    List<IMU> findByTimestampUTCBetween(int start, int end);
}
