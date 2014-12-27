package com.sharathp.service.symptom_management.dao;

import com.sharathp.service.symptom_management.model.Patient;

import java.util.List;

public interface PatientDao {

    List<Patient> getAllPatients();

}
