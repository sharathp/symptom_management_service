package com.sharathp.service.symptom_management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Doctor extends SmUser {

    @Column(name = "doctor_id", nullable = false)
    private String doctorId;

    @OneToMany
    @JoinTable(name="doctor_patient",
            joinColumns={@JoinColumn(name="doctor_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="patient_id", referencedColumnName="id")})
    private Set<Patient> patients = new HashSet<>();

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Doctor doctor = (Doctor) o;

        if (!doctorId.equals(doctor.doctorId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return doctorId.hashCode();
    }
}
