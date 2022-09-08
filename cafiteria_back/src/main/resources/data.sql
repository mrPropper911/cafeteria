DELETE FROM orders WHERE orders.id>0;
DELETE FROM roles WHERE id>0;
DELETE FROM users WHERE id>0;

ALTER TABLE orders AUTO_INCREMENT=1;
ALTER TABLE roles AUTO_INCREMENT=1;
ALTER TABLE users AUTO_INCREMENT=1;

INSERT INTO users(name, surname, location, phone, email, password)
VALUES ( 'Vadim', 'Belyakhovich', 'Minsk', '375336712956', 'vadim@gmail.com', 'qwer4321'),
       ('Lina', 'Jagernatovna', 'Lipen', '375239992956', 'Lima@mail.com', '12345'),
       ( 'Lusa', 'Chebotina', 'Washington', '375441712386', 'cheebooy@gmail.com', '555qqq');

INSERT INTO roles(name, user_id)
VALUES ( 'ROLE_USER', 1),
       ( 'ROLE_ADMIN', 2),
       ( 'ROLE_USER', 3);

INSERT INTO orders(time, location, comment, user_id)
VALUES ( '2020-01-01 10:10:10', 'Minsk', 'Need more cola', 1),
       ( '2021-01-01 12:10:12', 'Baranovichi', 'Please faster', 1),
       ( '2022-02-04 22:10:12', 'Moskov', 'I need big souce for pizza', 3);