package com.sharathp.service.symptom_management.rest;

import com.google.common.collect.Lists;
import com.sharathp.service.symptom_management.model.SmUser;
import com.sharathp.service.symptom_management.repo.SmUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private SmUserRepository smUserRepository;

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public List<SmUser> getAllUsers() {
        return Lists.newArrayList(smUserRepository.findAll());
    }
}