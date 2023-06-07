INSERT INTO author (id, firstname, lastname, biography, is_active) VALUES (1, 'John', 'Doe', 'Biografía de John Doe',true);
INSERT INTO author (id, firstname, lastname, biography, is_active) VALUES (2, 'Jane', 'Smith', 'Biografía de Jane Smith',true);
INSERT INTO author (id, firstname, lastname, biography, is_active) VALUES (3, 'Michael', 'Johnson', 'Biografía de Michael Johnson',true);
INSERT INTO author (id, firstname, lastname, biography, is_active) VALUES (4, 'Emily', 'Williams', 'Biografía de Emily Williams',true);
INSERT INTO author (id, firstname, lastname, biography, is_active) VALUES (5, 'David', 'Brown', 'Biografía de David Brown',true);

INSERT INTO book (id, title, author_id, description, price, quantity_available) VALUES (1, 'Libro 1', 1, 'Descripción del libro 1', 10.99, 100);
INSERT INTO book (id, title, author_id, description, price, quantity_available) VALUES (2, 'Libro 2', 2, 'Descripción del libro 2', 15.99, 50);
INSERT INTO book (id, title, author_id, description, price, quantity_available) VALUES (3, 'Libro 3', 3, 'Descripción del libro 3', 12.50, 80);
INSERT INTO book (id, title, author_id, description, price, quantity_available) VALUES (4, 'Libro 4', 4, 'Descripción del libro 4', 9.99, 120);
INSERT INTO book (id, title, author_id, description, price, quantity_available) VALUES (5, 'Libro 5', 5, 'Descripción del libro 5', 14.75, 60);

INSERT INTO user_bookify (id, username, password) VALUES (1, 'usuario1', 'password1');
INSERT INTO user_bookify (id, username, password) VALUES (2, 'usuario2', 'password2');
INSERT INTO user_bookify (id, username, password) VALUES (3, 'usuario3', 'password3');
INSERT INTO user_bookify (id, username, password) VALUES (4, 'usuario4', 'password4');
INSERT INTO user_bookify (id, username, password) VALUES (5, 'usuario5', 'password5');

INSERT INTO purchase (id, book_id, user_id, saledate, totalprice) VALUES (3,3,3, '2023-03-01', 200.00);
INSERT INTO purchase (id, book_id, user_id, saledate, totalprice) VALUES (2,2,2, '2023-02-01', 150.00);
INSERT INTO purchase (id, book_id, user_id, saledate, totalprice) VALUES (1,1,1, '2023-01-01', 100.00);
INSERT INTO purchase (id, book_id, user_id, saledate, totalprice) VALUES (4,4,4, '2023-04-01', 120.00);
INSERT INTO purchase (id, book_id, user_id, saledate, totalprice) VALUES (5,5,5, '2023-05-01', 180.00);

CREATE SEQUENCE secuencia START WITH 100;
