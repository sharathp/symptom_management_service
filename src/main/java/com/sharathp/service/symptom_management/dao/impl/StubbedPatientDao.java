package com.sharathp.service.symptom_management.dao.impl;

import com.sharathp.service.symptom_management.dao.PatientDao;
import com.sharathp.service.symptom_management.model.Patient;

import java.util.Arrays;
import java.util.List;

public class StubbedPatientDao implements PatientDao {
    private final List<Patient> patients;

    public StubbedPatientDao() {
        Patient p1 = new Patient();
        p1.setId("1");
        p1.setFirstName("p1");
        Patient p2 = new Patient();
        p2.setId("2");
        p2.setFirstName("p2");
        patients = Arrays.asList(p1, p2);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patients;
    }
}
