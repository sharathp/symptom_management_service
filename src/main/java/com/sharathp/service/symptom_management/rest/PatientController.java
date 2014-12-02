package com.sharathp.service.symptom_management.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sharathp.service.symptom_management.dao.PatientDao;
import com.sharathp.service.symptom_management.model.Patient;

@RestController
public class PatientController {

    @Autowired
    private PatientDao patientDao;

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public List<Patient> getAllPatients() {
        return patientDao.getAllPatients();
    }

    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }
}
