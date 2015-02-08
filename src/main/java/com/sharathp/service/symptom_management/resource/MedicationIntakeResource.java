package com.sharathp.service.symptom_management.resource;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class MedicationIntakeResource {
    @NotNull
    private MedicationResource medication;

    @NotNull
    private Date time;

    public MedicationResource getMedication() {
        return medication;
    }

    public void setMedication(MedicationResource medication) {
        this.medication = medication;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}