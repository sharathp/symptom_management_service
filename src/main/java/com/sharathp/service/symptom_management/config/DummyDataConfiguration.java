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
        // logger.info("Initializing database...");
        // final List<Medication> medications = createMedications();
        // createAdmin();
        // createDoctor();
        // createPatient(medications);

    }

    private List<Medication> createMedications(final int numMedications) {
        return IntStream.rangeClosed(1, numMedications)
                .mapToObj((i) -> {
                    final Medication medication = new Medication();
                    medication.setName("Medication " + i);
                    medicationUserRepository.save(medication);
                    return medication;
                })
                .collect(Collectors.toList());
    }

    private List<Patient> createPatients(final List<Medication> allMedications, final int numPatients) {
        return IntStream.rangeClosed(1, numPatients)
                .mapToObj((i) -> {
                    final Patient patient = new Patient();
                    patient.setUsername("patient " + i);
                    patient.setPassword("patient " + i);
                    patient.setFirstName("pat-first-" + i);
                    patient.setLastName("pat-last-" + i);
                    patient.setPatientId("p" + i);
                    // FIXME - make this random..
                    patient.getMedications().addAll(allMedications.stream().limit(3).collect(Collectors.toList()));
                    SmUserUtil.addPatientRoles(patient);
                    smUserRepository.save(patient);
                    return patient;
                })
                .collect(Collectors.toList());
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

    private List<Doctor> createDoctors(final int numDoctors) {
        return IntStream.rangeClosed(1, numDoctors)
                .mapToObj((i) -> {
                    final Doctor doctor = new Doctor();
                    doctor.setUsername("doctor " + i);
                    doctor.setPassword("doctor " + i);
                    doctor.setFirstName("doc-first-" + i);
                    doctor.setLastName("doc-last-" + i);
                    doctor.setDoctorId("d" + i);
                    SmUserUtil.addDoctorRoles(doctor);
                    smUserRepository.save(doctor);
                    return doctor;
                })
                .collect(Collectors.toList());
    }
}