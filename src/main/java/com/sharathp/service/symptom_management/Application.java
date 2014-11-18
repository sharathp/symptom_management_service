package com.sharathp.service.symptom_management;

import com.sharathp.service.symptom_management.dao.PatientDao;
import com.sharathp.service.symptom_management.dao.impl.StubbedPatientDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
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
