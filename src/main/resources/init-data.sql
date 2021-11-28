INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO users (age, email, enabled, firstName, lastName,  password) VALUES (10, 'admin@email.ru', true, 'Admin', 'Adminov', '$2y$12$ewvWbdBj/34U5MC7wHFKZOeBMyc9xRPBMkdhqq/YzA7gT0z21J6oa'), (11, 'user@email.ru', true, 'User', 'Userov', '$2y$12$xr1XtdcnDisJRd1XwJNUTOWXPijwJiVidRS750YfQUuay9zjCEEJy');

INSERT INTO users_roles (user_id, role_id) VALUES (2, 2), (1, 1), (1, 2);