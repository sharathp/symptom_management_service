package com.sharathp.service.symptom_management.util;

import com.sharathp.service.symptom_management.model.Role;
import com.sharathp.service.symptom_management.model.SmUser;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;

public class SmUserUtil {

    // singleton
    private SmUserUtil() {
        // no-op
    }

    public static void addDoctorRoles(SmUser smUser) {
        addRoles(smUser, Role.DOCTOR_ROLE);
    }

    public static void addPatientRoles(SmUser smUser) {
        addRoles(smUser, Role.PATIENT_ROLE);
    }

    public static void addAdminRoles(SmUser smUser) {
        addRoles(smUser, Role.ADMIN_ROLE);
    }

    public static void addRoles(final SmUser smUser, final String... roles) {
        if(smUser.getRoles() == null) {
            smUser.setRoles(new HashSet<>());
        }
        for(final String role: roles) {
            smUser.getRoles().add(role);
        }
    }
}
