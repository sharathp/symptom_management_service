package com.sharathp.service.symptom_management.auth;

import com.sharathp.service.symptom_management.model.SmUser;
import com.sharathp.service.symptom_management.repo.SmUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SmUserDetailsService implements UserDetailsService {

    @Autowired
    private SmUserRepository smUserRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        SmUser smUser = smUserRepository.findByUsername(username);
        if(smUser != null){
            return smUser;
        }
        throw new UsernameNotFoundException("UserName: " + username + " not found");
    }

    public void setSmUserRepository(final SmUserRepository smUserRepository) {
        this.smUserRepository = smUserRepository;
    }
}
