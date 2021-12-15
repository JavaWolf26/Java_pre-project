DROP TABLE IF EXISTS `users_roles`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `roles`;

CREATE TABLE IF NOT EXISTS `users`
(
    `id`        BIGINT AUTO_INCREMENT NOT NULL,
    `firstname` VARCHAR(255)          NULL,
    `lastname`  VARCHAR(255)          NULL,
    `age`       SMALLINT              NULL,
    `email`     VARCHAR(255)          NULL,
    `password`  VARCHAR(255)          NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `roles`
(
    `id`   BIGINT AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(255)          NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `users_roles`
(
    `user_id` bigint not null,
    `role_id` bigint not null,
    primary key (user_id, role_id),
    constraint FK2o0jvgh89lemvvo17cbqvdxaa
        foreign key (user_id) references users (id),
    constraint FKj6m8fwv7oqv74fcehir1a9ffy
        foreign key (role_id) references roles (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

