-- ensure that the tables with these names are removed before creating a new one.
DROP TABLE IF EXISTS orders;

-- Create orders table
CREATE TABLE orders
(
    id       BIGINT AUTO_INCREMENT,
    time     DATETIME     NOT NULL,
    location VARCHAR(255) NOT NULL,
    comment  VARCHAR(255),
    user_id BIGINT REFERENCES users(id),
    PRIMARY KEY (id)
);
