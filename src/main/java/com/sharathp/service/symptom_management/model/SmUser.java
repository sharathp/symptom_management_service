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

@Entity(name = "sm_users")
public class SmUser implements UserDetails {

    @Transient
    private Collection<GrantedAuthority> authorities;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @CollectionTable(
            name = "authorities",
            joinColumns = @JoinColumn(name = "username")
    )

    @ElementCollection(targetClass = String.class)
    private Set<String> authoritiesList;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getAuthoritiesList() {
        return authoritiesList;
    }

    public void setAuthoritiesList(Set<String> authoritiesList) {
        this.authoritiesList = authoritiesList;
        this.authorities = AuthorityUtils.createAuthorityList(authoritiesList.toArray(new String[0]));
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

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