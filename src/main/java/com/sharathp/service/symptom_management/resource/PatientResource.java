package com.sharathp.service.symptom_management.resource;

public class PatientResource extends SmUserResource {
    private String patientId;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
