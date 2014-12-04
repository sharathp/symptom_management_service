package com.sharathp.service.symptom_management.repo;

import com.sharathp.service.symptom_management.model.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PatientRepository extends CrudRepository<Patient, UUID> {
    // no-op
}
