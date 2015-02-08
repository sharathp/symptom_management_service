package com.sharathp.service.symptom_management.repo;

import com.sharathp.service.symptom_management.model.Patient;
import com.sharathp.service.symptom_management.model.PatientCheckIn;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface PatientCheckInRepository extends CrudRepository<PatientCheckIn, UUID> {

    List<PatientCheckIn> findByPatientAndCheckInTimeGreaterThanOrderByCheckInTimeDesc(Patient patient, Date date);
}
