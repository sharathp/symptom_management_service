package com.sharathp.service.symptom_management.rest;

import com.sharathp.service.symptom_management.model.Medication;
import com.sharathp.service.symptom_management.model.Patient;
import com.sharathp.service.symptom_management.model.PatientCheckIn;
import com.sharathp.service.symptom_management.repo.PatientCheckInRepository;
import com.sharathp.service.symptom_management.repo.PatientRepository;
import com.sharathp.service.symptom_management.resource.MedicationResource;
import com.sharathp.service.symptom_management.resource.PatientCheckInResource;
import com.sharathp.service.symptom_management.resource.PatientResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/api/v1/patients", produces = "application/json")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientCheckInRepository patientCheckInRepository;

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

    // TODO - for ROLE_PATIENT authorize only if the id = logged in user..
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_PATIENT', 'ROLE_DOCTOR')")
    public ResponseEntity<PatientResource> getPatient(@PathVariable final UUID id) {
        final Patient patient = patientRepository.findOne(id);
        if(patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(patient, PatientResource.class),
                HttpStatus.OK);
    }

    // TODO - for ROLE_PATIENT authorize only if the id = logged in user..
    @RequestMapping(value="/{id}/medications", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_PATIENT', 'ROLE_DOCTOR')")
    public ResponseEntity<List<MedicationResource>> getMedications(@PathVariable final UUID id) {
        final Patient patient = patientRepository.findOne(id);
        if(patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final Set<Medication> medications = patient.getMedications();
        final List<MedicationResource> medicationResources = medications.stream()
                .map(medication -> mapper.map(medication, MedicationResource.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(medicationResources, HttpStatus.OK);
    }

    // TODO - authorize only if logged in doctor is patient's doctor.
    @RequestMapping(value="/{id}/medications", method = RequestMethod.POST, consumes = "application/json")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<String> addMedications(@PathVariable final UUID id,
                                                 @Valid @RequestBody final List<MedicationResource> medicationResources) {
        final Patient patient = patientRepository.findOne(id);
        if(patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final List<Medication> newMedications = medicationResources.stream()
                .map(medicationResource -> mapper.map(medicationResource, Medication.class))
                .collect(Collectors.toList());

        patient.getMedications().addAll(newMedications);
        patientRepository.save(patient);

        // TODO - return above url
        return new ResponseEntity<>(patient.getId().toString(), HttpStatus.CREATED);
    }

    // TODO - authorize only if logged in patient is the same patient
    @RequestMapping(value="/{patientId}/check-ins", method = RequestMethod.POST, consumes = "application/json")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<String> addCheckIn(@PathVariable final UUID patientId,
                                             @Valid @RequestBody final PatientCheckInResource patientCheckInResource) {
        final Patient patient = patientRepository.findOne(patientId);
        if(patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final PatientCheckIn patientCheckIn = mapper.map(patientCheckInResource, PatientCheckIn.class);
        patientCheckIn.setPatient(patient);

        patientCheckInRepository.save(patientCheckIn);

        // TODO - return correct url
        return new ResponseEntity<>(patientCheckIn.getId().toString(), HttpStatus.CREATED);
    }


    // e.g. from = urlencode("2011-07-11T21:28:59.564+0100")
    @RequestMapping(value="/{patientId}/check-ins", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_PATIENT', 'ROLE_DOCTOR')")
    public ResponseEntity<List<PatientCheckInResource>> getPatientCheckIns(@PathVariable final UUID patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam("from") final Date from) {
        final Patient patient = patientRepository.findOne(patientId);
        if(patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final List<PatientCheckInResource> patientCheckInResources = patientCheckInRepository
                .findByPatientAndCheckInTimeGreaterThanOrderByCheckInTimeDesc(patient, from).stream()
                .map(patientCheckIn -> mapper.map(patientCheckIn, PatientCheckInResource.class))
                .collect(Collectors.toList());

        // TODO - return correct url
        return new ResponseEntity<>(patientCheckInResources, HttpStatus.OK);
    }

    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void setPatientCheckInRepository(PatientCheckInRepository patientCheckInRepository) {
        this.patientCheckInRepository = patientCheckInRepository;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
