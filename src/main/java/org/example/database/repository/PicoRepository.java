package org.example.database.repository;

import org.example.database.model.Pico;
import org.springframework.data.repository.CrudRepository;

public interface PicoRepository extends CrudRepository<Pico, String> {
}
