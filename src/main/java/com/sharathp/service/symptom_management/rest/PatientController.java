package com.sharathp.service.symptom_management.rest;

import com.sharathp.service.symptom_management.model.Patient;
import com.sharathp.service.symptom_management.repo.PatientRepository;
import com.sharathp.service.symptom_management.resource.PatientResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/api/v1/patients", produces = "application/json")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public List<PatientResource> getAllPatients() {
        final Spliterator<Patient> spliterator = patientRepository.findAll().spliterator();
        return StreamSupport.stream(spliterator, false)
                .map(patient -> mapper.map(patient, PatientResource.class))
                .collect(Collectors.toList());
    }

    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
