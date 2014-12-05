package com.sharathp.service.symptom_management.resource;

import javax.validation.constraints.NotNull;

public class DoctorResource extends SmUserResource {
    @NotNull
    private String doctorId;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
