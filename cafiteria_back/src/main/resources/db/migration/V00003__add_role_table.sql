-- ensure that the tables with these names are removed before creating a new one.
DROP TABLE IF EXISTS roles;

-- Create orders table
CREATE TABLE roles
(
    id       BIGINT AUTO_INCREMENT,
    name     VARCHAR(255)     NOT NULL,
    user_id BIGINT REFERENCES users(id),
    PRIMARY KEY (id)
);
