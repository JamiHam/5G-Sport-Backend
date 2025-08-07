package org.example.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ecg_sample")
public class ECGSample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int value;

    @ManyToOne
    @JoinColumn(name = "ecg_id")
    private ECG ecg;

    public ECGSample() {}

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ECG getEcg() {
        return ecg;
    }

    public void setEcg(ECG ecg) {
        this.ecg = ecg;
    }

    @Override
    public String toString() {
        return "{ id: " + id
                + ", value: " + value
                + ", ecg_id: " + ecg.getId()
                + " }";
    }
}
