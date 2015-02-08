package com.sharathp.service.symptom_management.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Immutable
@Entity
public class PatientCheckIn {

    @Id
    @GeneratedValue(generator="uuid2-generator")
    @GenericGenerator(name="uuid2-generator", strategy = "uuid2")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date checkInTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Pain pain;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Eating eating;

    @Column(nullable = false)
    private Boolean medicated;

    @ElementCollection
    @CollectionTable(name="patient_medication_intake",  joinColumns = @JoinColumn(name="checkin_id"))
    private List<MedicationIntake> medicationIntakes = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Pain getPain() {
        return pain;
    }

    public void setPain(Pain pain) {
        this.pain = pain;
    }

    public Eating getEating() {
        return eating;
    }

    public void setEating(Eating eating) {
        this.eating = eating;
    }

    public Boolean isMedicated() {
        return medicated;
    }

    public void setMedicated(Boolean medicated) {
        this.medicated = medicated;
    }

    public List<MedicationIntake> getMedicationIntakes() {
        return medicationIntakes;
    }

    public void setMedicationIntakes(List<MedicationIntake> medicationIntakes) {
        this.medicationIntakes = medicationIntakes;
    }
}