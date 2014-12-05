package com.sharathp.service.symptom_management.resource;

import javax.validation.constraints.NotNull;

public class PatientResource extends SmUserResource {
    @NotNull
    private String patientId;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
