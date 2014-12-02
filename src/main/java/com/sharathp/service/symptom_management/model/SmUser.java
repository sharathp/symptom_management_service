package com.sharathp.service.symptom_management.model;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "sm_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class SmUser implements UserDetails {

    @Id
    @GeneratedValue(generator="uuid2-generator")
    @GenericGenerator(name="uuid2-generator", strategy = "uuid2")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @CollectionTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "username"))
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @Column(name="role")
    private Set<String> roles;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}