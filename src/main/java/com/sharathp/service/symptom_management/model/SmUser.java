/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package com.sharathp.service.symptom_management.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity(name = "sm_user")
public class SmUser implements UserDetails {
    @Column(nullable = false)
    private String password;

    @Id
    private String username;

    @CollectionTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "username"))
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @Column(name="role")
    private Set<String> roles;

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