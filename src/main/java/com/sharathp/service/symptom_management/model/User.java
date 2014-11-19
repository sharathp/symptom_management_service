/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package com.sharathp.service.symptom_management.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID id;
    private String password;
	private String username;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}