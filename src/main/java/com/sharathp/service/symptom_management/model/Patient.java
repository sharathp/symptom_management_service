package com.sharathp.service.symptom_management.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Patient extends SmUser {

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @ManyToMany
    @JoinTable(name="patient_med",
            joinColumns={@JoinColumn(name="patient_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="med_id", referencedColumnName="id")})
    private Set<Medication> medications = new HashSet<>();

    public Set<Medication> getMedications() {
        return medications;
    }

    public void setMedications(Set<Medication> medications) {
        this.medications = medications;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
