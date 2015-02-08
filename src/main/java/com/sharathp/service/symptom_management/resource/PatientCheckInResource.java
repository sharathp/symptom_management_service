package com.sharathp.service.symptom_management.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sharathp.service.symptom_management.model.Eating;
import com.sharathp.service.symptom_management.model.Pain;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PatientCheckInResource {
    @JsonIgnore
    private UUID id;

    @NotNull
    private Date checkInTime;

    @NotNull
    private Pain pain;

    @NotNull
    private Eating eating;

    @NotNull
    private Boolean medicated;

    private List<MedicationIntakeResource> medicationIntakes = new ArrayList<>();

    @JsonProperty
    public UUID getId() {
        return id;
    }

    @JsonIgnore
    public void setId(final UUID id) {
        this.id = id;
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

    public Boolean getMedicated() {
        return medicated;
    }

    public void setMedicated(Boolean medicated) {
        this.medicated = medicated;
    }

    public List<MedicationIntakeResource> getMedicationIntakes() {
        return medicationIntakes;
    }

    public void setMedicationIntakes(List<MedicationIntakeResource> medicationIntakes) {
        this.medicationIntakes = medicationIntakes;
    }
}