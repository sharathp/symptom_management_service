package com.sharathp.service.symptom_management.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Patient extends SmUser {

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
