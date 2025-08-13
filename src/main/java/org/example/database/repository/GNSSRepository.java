package org.example.database.repository;

import org.example.database.model.GNSS;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GNSSRepository extends CrudRepository<GNSS, Long> {
    GNSS findById(long id);
    List<GNSS> findByTimestampUTCBetween(int start, int end);
}
