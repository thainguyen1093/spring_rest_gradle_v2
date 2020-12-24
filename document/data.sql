DROP DATABASE IF EXISTS hulahooh;
CREATE DATABASE hulahooh;

USE hulahooh;

DROP TABLE IF EXISTS users;
create table users
(
    id     INT(11) NOT NULL AUTO_INCREMENT,
    name   VARCHAR(100),
    active TINYINT,
    PRIMARY KEY (id)
);