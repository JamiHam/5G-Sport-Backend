package org.example.database.repository;

import org.example.database.model.IMUCoordinate;
import org.springframework.data.repository.CrudRepository;

public interface IMUCoordinateRepository extends CrudRepository<IMUCoordinate, Long> {
}
