package com.sharathp.service.symptom_management;

import javax.annotation.PostConstruct;

import com.sharathp.service.symptom_management.model.Doctor;
import com.sharathp.service.symptom_management.model.Patient;
import com.sharathp.service.symptom_management.repo.SmUserRepository;
import com.sharathp.service.symptom_management.util.SmUserUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sharathp.service.symptom_management.dao.PatientDao;
import com.sharathp.service.symptom_management.dao.impl.StubbedPatientDao;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories
// TODO - consider using org.springframework.boot.autoconfigure.SpringBootApplication annotation instead
public class Application {

    @Autowired
    private SmUserRepository smUserRepository;

    private final Log logger = LogFactory.getLog(getClass());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Mapper mapper() {
        return new DozerBeanMapper();
    }

    @PostConstruct
    private void initDatabase() {
        logger.info("Initializing database...");
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

        Patient patient = new Patient();
        patient.setUsername("patient");
        patient.setPassword("patient");
        patient.setFirstName("patient");
        patient.setLastName("patient");
        patient.setPatientId("p1");
        SmUserUtil.addPatientRoles(patient);
        smUserRepository.save(patient);

        Doctor doctor = new Doctor();
        doctor.setUsername("patient");
        doctor.setPassword("patient");
        doctor.setFirstName("patient");
        doctor.setLastName("patient");
        doctor.setDoctorId("d1");
        SmUserUtil.addDoctorRoles(doctor);
        smUserRepository.save(doctor);
    }

    @Bean
    public PatientDao patientDao() {
        return new StubbedPatientDao();
    }
}
