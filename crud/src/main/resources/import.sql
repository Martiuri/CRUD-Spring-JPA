INSERT INTO usuarios (username, password, enable, nombre, domicilio) VALUES('administrador', '$2a$10$9SexlJhurOO9Aywukh6qjePWGRUoDlsMHCbD9xCIpqidgGZ5KQk3e', 1, 'Martin Lopez', 'Guadalajara');

INSERT INTO authorities (user_id, authority) VALUES(1, 'ROLE_ADMIN');