INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO users (firstName, lastName, age, email, enabled, password)
VALUES ('admin', 'admin', 10, 'admin', true,'admin');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO users (firstName, lastName, age, email, enabled, password)
VALUES ('user', 'user', 11, 'user', true,'user');
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);