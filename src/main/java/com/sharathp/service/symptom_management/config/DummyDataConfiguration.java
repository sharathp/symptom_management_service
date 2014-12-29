package com.sharathp.service.symptom_management.config;

import com.sharathp.service.symptom_management.model.Doctor;
import com.sharathp.service.symptom_management.model.Medication;
import com.sharathp.service.symptom_management.model.Patient;
import com.sharathp.service.symptom_management.repo.MedicationRepository;
import com.sharathp.service.symptom_management.repo.SmUserRepository;
import com.sharathp.service.symptom_management.util.SmUserUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
// dummy data for inserting data into database
public class DummyDataConfiguration {
    @Autowired
    private SmUserRepository smUserRepository;

    @Autowired
    private MedicationRepository medicationUserRepository;

    private final Log logger = LogFactory.getLog(getClass());

    @PostConstruct
    private void initDatabase() {
        logger.info("Initializing database...");
        createAdmin();
        createDoctor();
        createPatient();
        createMedications();
    }

    private List<Medication> createMedications() {
        return IntStream.rangeClosed(1, 10)
                .mapToObj((i) -> {
                    final Medication medication = new Medication();
                    medication.setName("Medication " + i);
                    medicationUserRepository.save(medication);
                    return medication;
                })
                .collect(Collectors.toList());
    }

    private Patient createPatient() {
        Patient patient = new Patient();
        patient.setUsername("patient");
        patient.setPassword("patient");
        patient.setFirstName("patient");
        patient.setLastName("patient");
        patient.setPatientId("p1");
        SmUserUtil.addPatientRoles(patient);
        smUserRepository.save(patient);
        return patient;
    }

    private Patient createAdmin() {
        // FIXME - for the time being model admin as a patient,
        // later pull admin via another UserDetailsService?
        Patient admin = new Patient();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setPatientId("");
        SmUserUtil.addAdminRoles(admin);
        smUserRepository.save(admin);
        return admin;
    }

    private Doctor createDoctor() {
        Doctor doctor = new Doctor();
        doctor.setUsername("doctor");
        doctor.setPassword("doctor");
        doctor.setFirstName("doctor");
        doctor.setLastName("doctor");
        doctor.setDoctorId("d1");
        SmUserUtil.addDoctorRoles(doctor);
        smUserRepository.save(doctor);
        return doctor;
    }
}