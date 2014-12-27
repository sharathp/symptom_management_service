package com.sharathp.service.symptom_management.rest;

import com.sharathp.service.symptom_management.model.Medication;
import com.sharathp.service.symptom_management.repo.MedicationRepository;
import com.sharathp.service.symptom_management.resource.MedicationResource;
import com.sharathp.service.symptom_management.util.ValidationUtil;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/api/v1/medications", produces = "application/json")
public class MedicationController {

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public List<MedicationResource> getAllMedications() {
        final Spliterator<Medication> spliterator = medicationRepository.findAll().spliterator();
        return StreamSupport.stream(spliterator, false)
                .map(patient -> mapper.map(patient, MedicationResource.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<String> createMedication(@Valid @RequestBody final MedicationResource medicationResource,
                                              final BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<String>(ValidationUtil.getErrorMessage(bindingResult.getAllErrors()),
                    HttpStatus.BAD_REQUEST);
        }
        final Medication existingMedication = medicationRepository.findByName(medicationResource.getName());
        if(existingMedication != null) {
            return new ResponseEntity<String>("Medication already exists: " + medicationResource.getId(),
                    HttpStatus.BAD_REQUEST);
        }

        final Medication medication = mapper.map(medicationResource, Medication.class);
        medicationRepository.save(medication);

        return new ResponseEntity<>(medication.getId().toString(), HttpStatus.CREATED);
    }

    public void setMedicationRepository(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
