package com.sharathp.service.symptom_management.model;

public class Role {

    private Role() {
        // singleton
    }

    public static final String CLIENT = "CLIENT";
    public static final String DOCTOR = "DOCTOR";
    public static final String PATIENT = "PATIENT";
    public static final String ADMIN = "ADMIN";

    public static final String CLIENT_ROLE = role(CLIENT);
    public static final String DOCTOR_ROLE = role(DOCTOR);
    public static final String PATIENT_ROLE = role(PATIENT);
    public static final String ADMIN_ROLE = role(ADMIN);

    private static final String ROLE_PREFIX = "ROLE_";

    private static String role(String name) {
        return ROLE_PREFIX + name;
    }
}