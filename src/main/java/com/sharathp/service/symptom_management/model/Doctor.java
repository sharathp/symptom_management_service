package com.sharathp.service.symptom_management.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Doctor extends SmUser {

    @Column(name = "doctor_id", nullable = false)
    private String doctorId;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
