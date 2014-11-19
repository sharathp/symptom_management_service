package com.sharathp.service.symptom_management;

import com.sharathp.service.symptom_management.dao.PatientDao;
import com.sharathp.service.symptom_management.dao.impl.StubbedPatientDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableGlobalMethodSecurity(securedEnabled = true)
// TODO - consider using org.springframework.boot.autoconfigure.SpringBootApplication annotation instead
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PatientDao patientDao() {
        return new StubbedPatientDao();
    }
}
