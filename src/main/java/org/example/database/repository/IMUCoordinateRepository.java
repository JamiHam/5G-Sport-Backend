package org.example.database.repository;

import org.example.database.model.IMUCoordinate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IMUCoordinateRepository extends CrudRepository<IMUCoordinate, Long> {
    List<IMUCoordinate> findByImuId(long id);
}
