package com.sharathp.service.symptom_management.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SmUserResource {
    @JsonIgnore
    private UUID id;
    private String password;
    private String username;
    private String firstName;
    private String lastName;

    public UUID getId() {
        return id;
    }

    @JsonIgnore
    public void setId(UUID id) {
        this.id = id;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}