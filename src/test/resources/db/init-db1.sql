CREATE TABLE users
(
    id    VARCHAR(255) PRIMARY KEY,
    username      VARCHAR(255),
    name VARCHAR(255),
    surname  VARCHAR(255)
);

INSERT INTO users
VALUES ('example-user-id-1', 'user-1', 'User', 'Userenko'),
       ('example-user-id-2', 'user-2', 'Testuser', 'Testov'),
       ('example-user-id-3', 'john_doe', 'John', 'Doe'),
       ('example-user-id-4', 'jane_smith', 'Jane', 'Smith');