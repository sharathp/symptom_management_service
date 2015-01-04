package com.sharathp.service.symptom_management.rest;

import com.sharathp.service.symptom_management.model.Doctor;
import com.sharathp.service.symptom_management.repo.DoctorRepository;
import com.sharathp.service.symptom_management.resource.DoctorResource;
import com.sharathp.service.symptom_management.resource.PatientResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/doctors", produces = "application/json")
public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private Mapper mapper;

    // TODO - authorize only if the id = logged in user..
    @RequestMapping(value="/{id}/patients", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<List<PatientResource>> getPatients(@PathVariable final UUID id) {
        final Doctor doctor = doctorRepository.findOne(id);
        if(doctor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final List<PatientResource> patientResources = doctor
                .getPatients()
                .stream()
                .map(patient -> mapper.map(patient, PatientResource.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(patientResources,  HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<DoctorResource> getPatient(@PathVariable final UUID id) {
        final Doctor doctor = doctorRepository.findOne(id);
        if(doctor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(doctor, DoctorResource.class),
                HttpStatus.OK);
    }
}