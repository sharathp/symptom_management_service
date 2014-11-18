package com.sharathp.service.symptom_management.rest;

import com.sharathp.service.symptom_management.dao.PatientDao;
import com.sharathp.service.symptom_management.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientDao patientDao;

    @RequestMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientDao.getAllPatients();
    }

    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }
}