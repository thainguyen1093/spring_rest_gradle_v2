DROP DATABASE IF EXISTS hulahooh;
CREATE DATABASE hulahooh;

USE hulahooh;

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users
(
    id       INT(11) NOT NULL AUTO_INCREMENT,
    username VARCHAR(100),
    role_id  INT(11),
    active   TINYINT(1),
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE IF NOT EXISTS roles
(
    id          INT(11)      NOT NULL AUTO_INCREMENT,
    role_name   VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (id)
);