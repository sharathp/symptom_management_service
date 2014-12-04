package com.sharathp.service.symptom_management.repo;

import org.springframework.data.repository.CrudRepository;

import com.sharathp.service.symptom_management.model.SmUser;

import java.util.UUID;

public interface SmUserRepository extends CrudRepository<SmUser, UUID> {
    SmUser findByUsername(String username);
}
