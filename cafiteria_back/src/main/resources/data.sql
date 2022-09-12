DELETE FROM orders WHERE orders.id>0;
DELETE FROM roles WHERE id>0;
DELETE FROM users WHERE id>0;

ALTER TABLE orders AUTO_INCREMENT=1;
ALTER TABLE roles AUTO_INCREMENT=1;
ALTER TABLE users AUTO_INCREMENT=1;

INSERT INTO users(name, surname, location, phone, email, password)
VALUES ( 'Vadim', 'Belyakhovich', 'Minsk', '2222', 'vadim@gmail.com', '$2y$10$4HBKgn/t6Un7SgEd6UOF4.sT0qNBTeWAwPEeHCSOlvD2tNRFVlU9G'),
       ('Lina', 'Jagernatovna', 'Lipen', '1111', 'Lima@mail.com', '$2y$10$ybyXjlkHB5/VFXG1zIGwCe5KXwNKQjvRyHfK0JzlGxP7MIOOtsFna'),
       ( 'Lusa', 'Chebotina', 'Washington', '3333', 'cheebooy@gmail.com', '$2y$10$4HBKgn/t6Un7SgEd6UOF4.sT0qNBTeWAwPEeHCSOlvD2tNRFVlU9G');

INSERT INTO roles(name, user_id)
VALUES ( 'USER', 1),
       ( 'ADMIN', 2),
       ( 'USER', 3);

INSERT INTO orders(time, location, comment, user_id)
VALUES ( '2020-01-01 10:10:10', 'Minsk', 'Need more cola', 1),
       ( '2021-01-01 12:10:12', 'Baranovichi', 'Please faster', 1),
       ( '2022-02-04 22:10:12', 'Moskov', 'I need big souce for pizza', 3);