package org.example.database.repository;

import org.example.database.model.EcgSample;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EcgSampleRepository extends CrudRepository<EcgSample, Long> {
    List<EcgSample> findByEcgId(long id);
}
