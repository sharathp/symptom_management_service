package com.sharathp.service.symptom_management.resource;

public class DoctorResource extends SmUserResource {
    private String doctorId;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
