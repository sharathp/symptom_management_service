package com.sharathp.service.symptom_management.rest;

import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.sharathp.service.symptom_management.model.SmUser;
import com.sharathp.service.symptom_management.repo.SmUserRepository;
import com.sharathp.service.symptom_management.resource.AdminResource;
import com.sharathp.service.symptom_management.util.SmUserUtil;

@RestController
public class AdminController {

    @Autowired
    private SmUserRepository smUserRepository;

    @Autowired
    private Mapper mapper;

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public List<SmUser> getAllUsers() {
        return Lists.newArrayList(smUserRepository.findAll());
    }

    @RequestMapping(value = "/admin/admins", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> createAdmin(@RequestBody AdminResource adminResource) {
        // TODO - validate resource and if the username already exists..
        final SmUser smUser = mapper.map(adminResource, SmUser.class);
        SmUserUtil.addAdminRoles(smUser);
        smUserRepository.save(smUser);
        // TODO - return a url..
        return new ResponseEntity<>(smUser.getId().toString(), HttpStatus.CREATED);
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public void setSmUserRepository(SmUserRepository smUserRepository) {
        this.smUserRepository = smUserRepository;
    }
}
