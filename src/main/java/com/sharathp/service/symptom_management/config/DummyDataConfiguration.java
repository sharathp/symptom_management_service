package com.sharathp.service.symptom_management.config;

import com.sharathp.service.symptom_management.model.Doctor;
import com.sharathp.service.symptom_management.model.Medication;
import com.sharathp.service.symptom_management.model.Patient;
import com.sharathp.service.symptom_management.model.PatientCheckIn;
import com.sharathp.service.symptom_management.repo.DoctorRepository;
import com.sharathp.service.symptom_management.repo.MedicationRepository;
import com.sharathp.service.symptom_management.repo.PatientCheckInRepository;
import com.sharathp.service.symptom_management.repo.PatientRepository;
import com.sharathp.service.symptom_management.util.SmUserUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
// dummy data for inserting data into database
public class DummyDataConfiguration {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private PatientCheckInRepository patientCheckInRepository;

    @Value("${dummy.data.create}")
    private boolean createDummyData;

    private final Log logger = LogFactory.getLog(getClass());

    @PostConstruct
    private void initDatabase() {
        if(!createDummyData) {
            logger.info("No Dummy data created..");
            return;
        }

        logger.info("Initializing database...");
        createAdmin();
        final int numPatients = 9;
        final int minNumCheckInsPerPatient = 2;
        final int numMedications = 25;
        final int numDoctors = 3;

        final List<Medication> medications = createMedications(numMedications);
        final List<Patient> patients = createPatients(medications, numPatients, minNumCheckInsPerPatient);
        final List<Doctor> doctors = createDoctors(numDoctors, patients);
    }

    private List<Medication> createMedications(final int numMedications) {
        return IntStream.rangeClosed(1, numMedications)
                .mapToObj((i) -> {
                    final Medication medication = new Medication();
                    medication.setName("Medication " + i);
                    medicationRepository.save(medication);
                    return medication;
                })
                .collect(Collectors.toList());
    }

    private List<Patient> createPatients(final List<Medication> allMedications,
                                         final int numPatients,
                                         final int minNumCheckInsPerPatient) {
        final Random medicationRandom = new Random();
        final Random numCheckinRandom = new Random();
        return IntStream.rangeClosed(1, numPatients)
                .mapToObj((i) -> {
                    final Patient patient = new Patient();
                    patient.setUsername("patient" + i);
                    patient.setPassword("patient" + i);
                    patient.setFirstName("pat-first-" + i);
                    patient.setLastName("pat-last-" + i);
                    patient.setPatientId("p" + i);
                    final List<Medication> medications = medicationRandom
                            .ints(0, allMedications.size())
                            .limit(3)
                            .mapToObj(r -> allMedications.get(r))
                            .collect(Collectors.toList());
                    patient.getMedications().addAll(medications);
                    SmUserUtil.addPatientRoles(patient);
                    patientRepository.save(patient);
                    createPatientCheckIns(minNumCheckInsPerPatient + numCheckinRandom.nextInt(3), patient);
                    return patient;
                })
                .collect(Collectors.toList());
    }

    private List<PatientCheckIn> createPatientCheckIns(final int numCheckIns, final Patient patient) {
        final long beginTime = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
        final long endTime = Timestamp.valueOf("2014-12-31 00:00:00").getTime();
        final Random random = new Random();
        return random.longs(beginTime, endTime)
                .limit(numCheckIns)
                .mapToObj(l -> {
                    final PatientCheckIn patientCheckIn = new PatientCheckIn();
                    patientCheckIn.setCreatedAt(new Date(l));
                    patientCheckIn.setPatient(patient);
                    patientCheckInRepository.save(patientCheckIn);
                    return patientCheckIn;
                })
                .collect(Collectors.toList());
    }

    private Patient createAdmin() {
        // FIXME - for the time being model admin as a patient,
        // later pull admin via another UserDetailsService?
        final Patient admin = new Patient();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setPatientId("admin");
        SmUserUtil.addAdminRoles(admin);
        patientRepository.save(admin);
        return admin;
    }

    private List<Doctor> createDoctors(final int numDoctors, final List<Patient> allPatients) {
        final int numPatientsPerDoctor = allPatients.size()/numDoctors;
        return IntStream.rangeClosed(0, numDoctors - 1)
                .mapToObj((i) -> {
                    final Doctor doctor = new Doctor();
                    doctor.setUsername("doctor" + i);
                    doctor.setPassword("doctor" + i);
                    doctor.setFirstName("doc-first-" + i);
                    doctor.setLastName("doc-last-" + i);
                    doctor.setDoctorId("d" + i);
                    final int startIndex = i * numPatientsPerDoctor;
                    final Set<Patient> patients = new HashSet<>(
                            allPatients.subList(startIndex, startIndex + numPatientsPerDoctor));
                    doctor.setPatients(patients);
                    SmUserUtil.addDoctorRoles(doctor);
                    doctorRepository.save(doctor);
                    return doctor;
                })
                .collect(Collectors.toList());
    }
}