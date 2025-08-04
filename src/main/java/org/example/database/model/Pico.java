package org.example.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pico")
public class Pico {
    @Id
    private String id;

    protected Pico() {}

    public Pico(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
