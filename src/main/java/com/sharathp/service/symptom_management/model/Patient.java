package com.sharathp.service.symptom_management.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Patient extends SmUser {

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @ManyToMany(targetEntity = Medication.class)
    @JoinTable(name="patient_med",
            joinColumns={@JoinColumn(name="patient_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="med_id", referencedColumnName="id")})
    @BatchSize(size = 10)
    private Set<Medication> medications = new HashSet<>();

    @OneToMany(mappedBy = "patient", targetEntity = PatientCheckIn.class)
    @OrderBy(value = "createdAt DESC")
    @BatchSize(size = 10)
    private List<PatientCheckIn> checkIns = new ArrayList<>();

    public Set<Medication> getMedications() {
        return medications;
    }

    public void setMedications(Set<Medication> medications) {
        this.medications = medications;
    }

    public List<PatientCheckIn> getCheckIns() {
        return checkIns;
    }

    public void setCheckIns(List<PatientCheckIn> checkIns) {
        this.checkIns = checkIns;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}