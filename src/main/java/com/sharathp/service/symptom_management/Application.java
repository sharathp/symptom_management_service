package com.sharathp.service.symptom_management;

import com.sharathp.service.symptom_management.dozer.UUIDBeanFactory;
import com.sharathp.service.symptom_management.model.Doctor;
import com.sharathp.service.symptom_management.model.Medication;
import com.sharathp.service.symptom_management.model.Patient;
import com.sharathp.service.symptom_management.repo.MedicationRepository;
import com.sharathp.service.symptom_management.repo.SmUserRepository;
import com.sharathp.service.symptom_management.util.SmUserUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories
// TODO - consider using org.springframework.boot.autoconfigure.SpringBootApplication annotation instead
public class Application {

//    @Autowired
    private SmUserRepository smUserRepository;

//    @Autowired
    private MedicationRepository medicationUserRepository;

    private final Log logger = LogFactory.getLog(getClass());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Mapper mapper() {
        final BeanMappingBuilder builder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(UUID.class, UUID.class, TypeMappingOptions.oneWay(), TypeMappingOptions.beanFactory(UUIDBeanFactory.class.getName()));
            }
        };

        final DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(builder);
        return mapper;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder builder)
            throws IOException {
        return builder.dataSource(dataSource())
                .persistenceUnit("symptom_management")
                .build();
    }

    @Bean
    public DataSource dataSource() throws IOException {
        final DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        final Resource resource = resourceLoader.getResource("datasource.properties");
//        final ClassPathResource classPathResource = new ClassPathResource("datasource.properties");
        final Properties dataSourceProperties = PropertiesLoaderUtils.loadProperties(resource);
        final HikariConfig config = new HikariConfig(dataSourceProperties);
        final HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }

    @PostConstruct
    private void initDatabase() {
        logger.info("Initializing database...");
//        createAdmin();
//        createDoctor();
//        createPatient();
//        createMedications();
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