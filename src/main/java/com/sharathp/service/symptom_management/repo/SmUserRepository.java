package com.sharathp.service.symptom_management.repo;

import org.springframework.data.repository.CrudRepository;

import com.sharathp.service.symptom_management.model.SmUser;

public interface SmUserRepository extends CrudRepository<SmUser, String> {
    SmUser findByUsername(String username);
}
