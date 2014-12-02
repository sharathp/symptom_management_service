package com.sharathp.service.symptom_management;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    private final Log logger = LogFactory.getLog(getClass());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    private void initDatabase() {
        logger.info("Initializing database...");
    }

    @Bean
    public PatientDao patientDao() {
        return new StubbedPatientDao();
    }
}
