INSERT INTO sm_user(username, password) values('admin', 'admin');
INSERT INTO user_role(username, role) values('admin', 'ROLE_ADMIN');

INSERT INTO sm_user(username, password) values('doctor', 'doctor');
INSERT INTO user_role(username, role) values('doctor', 'ROLE_DOCTOR');

INSERT INTO sm_user(username, password) values('patient', 'patient');
INSERT INTO user_role(username, role) values('patient', 'ROLE_PATIENT');