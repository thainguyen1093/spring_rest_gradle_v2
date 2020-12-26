DROP DATABASE IF EXISTS hulahooh;
CREATE DATABASE hulahooh;

USE hulahooh;

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users
(
    id       INT(11) NOT NULL AUTO_INCREMENT,
    username VARCHAR(100),
    active   TINYINT(1),
    PRIMARY KEY (id)
);