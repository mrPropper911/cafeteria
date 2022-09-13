-- ensure that the table with this username is removed before creating a new one.
DROP TABLE IF EXISTS users;

-- Create user table
CREATE TABLE users
(
    id   BIGINT AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    phone BIGINT NOT NULL UNIQUE ,
    email VARCHAR(255) NOT NULL UNIQUE ,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);