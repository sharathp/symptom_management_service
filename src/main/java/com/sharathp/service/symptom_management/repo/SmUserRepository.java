package com.sharathp.service.symptom_management.repo;

import com.sharathp.service.symptom_management.model.SmUser;
import org.springframework.data.repository.CrudRepository;

public interface SmUserRepository extends CrudRepository<SmUser, String> {
    // no-op
}
