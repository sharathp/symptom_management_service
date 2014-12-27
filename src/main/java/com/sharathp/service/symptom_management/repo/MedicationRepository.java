package com.sharathp.service.symptom_management.repo;

import com.sharathp.service.symptom_management.model.Medication;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MedicationRepository extends CrudRepository<Medication, UUID> {
    Medication findByName(String name);
}
