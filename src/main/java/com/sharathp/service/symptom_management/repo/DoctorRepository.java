package com.sharathp.service.symptom_management.repo;

import com.sharathp.service.symptom_management.model.Doctor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DoctorRepository extends CrudRepository<Doctor, UUID> {
    // no-op
}
