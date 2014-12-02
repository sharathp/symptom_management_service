package com.sharathp.service.symptom_management.dao;

import java.util.List;

import com.sharathp.service.symptom_management.model.Patient;

public interface PatientDao {

    List<Patient> getAllPatients();

}
