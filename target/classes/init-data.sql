INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO users (firstName, lastName, age, email, enabled, password)
VALUES ('Admin', 'Adminov', 10, 'admin@email.ru', true,'$2y$12$ewvWbdBj/34U5MC7wHFKZOeBMyc9xRPBMkdhqq/YzA7gT0z21J6oa');
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO users (firstName, lastName, age, email, enabled, password)
VALUES ('User', 'Userov', 11, 'user@email.ru', true, '$2y$12$xr1XtdcnDisJRd1XwJNUTOWXPijwJiVidRS750YfQUuay9zjCEEJy');
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);

INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);