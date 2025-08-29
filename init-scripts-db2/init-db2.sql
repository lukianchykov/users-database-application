CREATE TABLE user_table
(
    ldap_login VARCHAR(255) PRIMARY KEY,
    name       VARCHAR(255),
    surname    VARCHAR(255)
);

INSERT INTO user_table
VALUES ('admin_user', 'Admin', 'Administrator'),
       ('manager_user', 'Manager', 'Managerov'),
       ('dev_user', 'Developer', 'Devops'),
       ('test_user', 'Tester', 'Quality');