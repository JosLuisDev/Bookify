INSERT INTO authors (id, firstname, lastname, email, date_of_birth, nationality, biography, is_active) VALUES (1, 'John', 'Doe', 'email1@mmail.com', '1997-01-31', 'Mexico', 'Biografía de John Doe',true);
INSERT INTO authors (id, firstname, lastname, email, date_of_birth, nationality, biography, is_active) VALUES (2, 'Jane', 'Smith', 'email2@mmail.com', '1997-01-31', 'Mexico', 'Biografía de Jane Smith',true);
INSERT INTO authors (id, firstname, lastname, email, date_of_birth, nationality, biography, is_active) VALUES (3, 'Michael', 'Johnson', 'email3@mmail.com', '1997-01-31', 'Mexico', 'Biografía de Michael Johnson',true);
INSERT INTO authors (id, firstname, lastname, email, date_of_birth, nationality, biography, is_active) VALUES (4, 'Emily', 'Williams', 'email4@mmail.com', '1997-01-31', 'Mexico', 'Biografía de Emily Williams',true);
INSERT INTO authors (id, firstname, lastname, email, date_of_birth, nationality, biography, is_active) VALUES (5, 'David', 'Brown', 'email5@mmail.com', '1997-01-31', 'Mexico', 'Biografía de David Brown',true);

INSERT INTO books (id, title, author_id, description, price, quantity_available, is_active) VALUES (1, 'Libro 1', 1, 'Descripción del libro 1', 10.99, 100, true);
INSERT INTO books (id, title, author_id, description, price, quantity_available, is_active) VALUES (2, 'Libro 2', 2, 'Descripción del libro 2', 15.99, 50, true);
INSERT INTO books (id, title, author_id, description, price, quantity_available, is_active) VALUES (3, 'Libro 3', 3, 'Descripción del libro 3', 12.50, 80, true);
INSERT INTO books (id, title, author_id, description, price, quantity_available, is_active) VALUES (4, 'Libro 4', 4, 'Descripción del libro 4', 9.99, 120, true);
INSERT INTO books (id, title, author_id, description, price, quantity_available, is_active) VALUES (5, 'Libro 5', 5, 'Descripción del libro 5', 14.75, 60, true);

INSERT INTO users_bookify (id, username, password) VALUES (1, 'usuario1', 'password1');
INSERT INTO users_bookify (id, username, password) VALUES (2, 'usuario2', 'password2');
INSERT INTO users_bookify (id, username, password) VALUES (3, 'usuario3', 'password3');
INSERT INTO users_bookify (id, username, password) VALUES (4, 'usuario4', 'password4');
INSERT INTO users_bookify (id, username, password) VALUES (5, 'usuario5', 'password5');

--INSERT INTO purchase (id, book_id, user_id, saledate, totalprice, is_active) VALUES (3,3,3, '2023-03-01', 200.00, true);
--INSERT INTO purchase (id, book_id, user_id, saledate, totalprice, is_active) VALUES (2,2,2, '2023-02-01', 150.00, true);
--INSERT INTO purchase (id, book_id, user_id, saledate, totalprice, is_active) VALUES (1,1,1, '2023-01-01', 100.00, true);
--INSERT INTO purchase (id, book_id, user_id, saledate, totalprice, is_active) VALUES (4,4,4, '2023-04-01', 120.00, true);
--INSERT INTO purchase (id, book_id, user_id, saledate, totalprice, is_active) VALUES (5,5,5, '2023-05-01', 180.00, true);

CREATE SEQUENCE secuencia START WITH 100;
