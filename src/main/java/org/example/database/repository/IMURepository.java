package org.example.database.repository;

import org.example.database.model.IMU;
import org.springframework.data.repository.CrudRepository;

public interface IMURepository extends CrudRepository<IMU, Long> {
}
