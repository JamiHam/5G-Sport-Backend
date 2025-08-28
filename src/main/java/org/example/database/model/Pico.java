package org.example.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pico")
public class Pico {
    @Id
    private String id;

    public Pico() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
