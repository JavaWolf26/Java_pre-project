INSERT INTO `roles` (`id`, `name`)
VALUES
    (1,'ROLE_ADMIN'),
    (2,'ROLE_USER');

INSERT INTO `users` (`age`, `email`, `password`, `lastname`, `firstname`)
VALUES
    (10,'admin@email.ru',
     '$2y$12$ewvWbdBj/34U5MC7wHFKZOeBMyc9xRPBMkdhqq/YzA7gT0z21J6oa',
     'Adminov', 'Admin'),
    (11,'user@email.ru',
     '$2y$12$xr1XtdcnDisJRd1XwJNUTOWXPijwJiVidRS750YfQUuay9zjCEEJy',
     'Userov', 'User'),
    (12,'ford@email.ru',
    '$2y$12$sKpAQe75c3LU8CG5eccCDeMNn3J9W6KueFM2SzF3X15ydEe4dJbuW',
    'Fordov', 'Ford');

insert into users_roles
values
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 1),
    (3, 2);

