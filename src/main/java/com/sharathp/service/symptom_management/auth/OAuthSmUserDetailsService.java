package com.sharathp.service.symptom_management.auth;

import com.sharathp.service.symptom_management.model.Role;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;

public class OAuthSmUserDetailsService implements UserDetailsService {
    private final Log logger = LogFactory.getLog(getClass());
    private final UserDetailsService userDetailsService;

    public OAuthSmUserDetailsService(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final Collection<? extends GrantedAuthority> clientAuthorities = getClientAuthorities();
        validate(userDetails, clientAuthorities);
        return userDetails;
    }

    private void validate(final UserDetails userDetails, final Collection<? extends GrantedAuthority> clientAuthorities ) {
        logger.debug("userDetails authorities: " + userDetails.getAuthorities());
        logger.debug("clientAuthorities: " + clientAuthorities);
        if(clientAuthorities.stream().anyMatch(authority -> Role.DOCTOR_CLIENT_ROLE.equals(authority.getAuthority()))) {
            validateUserHasRole(userDetails, Role.DOCTOR_ROLE);
            return;
        }

        if(clientAuthorities.stream().anyMatch(authority -> Role.PATIENT_CLIENT_ROLE.equals(authority.getAuthority()))) {
            validateUserHasRole(userDetails, Role.PATIENT_ROLE);
            return;
        }
    }

    private void validateUserHasRole(final UserDetails userDetails, final String role) {
        if(userDetails.getAuthorities().stream().anyMatch(authority -> role.equals(authority.getAuthority()))) {
            return;
        }
        throw new UsernameNotFoundException("UserName: " + userDetails.getUsername() + " not found");
    }

    private Collection<? extends GrantedAuthority> getClientAuthorities() {
        final Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if(! (a instanceof UsernamePasswordAuthenticationToken)) {
            return Collections.emptyList();
        }
        return ((UsernamePasswordAuthenticationToken)a).getAuthorities();
    }
}
