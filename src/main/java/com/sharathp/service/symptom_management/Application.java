package com.sharathp.service.symptom_management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharathp.service.symptom_management.dozer.UUIDBeanFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories
// TODO - consider using org.springframework.boot.autoconfigure.SpringBootApplication annotation instead
public class Application {

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
    @Primary
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);
        return objectMapper;
    }

    @Bean
    public DataSource dataSource() throws IOException {
        final DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        final Resource resource = resourceLoader.getResource("datasource.properties");
        final Properties dataSourceProperties = PropertiesLoaderUtils.loadProperties(resource);
        final HikariConfig config = new HikariConfig(dataSourceProperties);
        final HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }
}