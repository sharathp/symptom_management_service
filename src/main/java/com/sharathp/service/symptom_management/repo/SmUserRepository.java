package com.sharathp.service.symptom_management.repo;

import com.sharathp.service.symptom_management.model.SmUser;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SmUserRepository extends CrudRepository<SmUser, UUID> {
    SmUser findByUsername(String username);
}
