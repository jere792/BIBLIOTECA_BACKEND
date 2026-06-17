INSERT INTO Usuario(usuario, contrasena, rol) VALUES('admin', '1234', 3);
INSERT INTO Usuario(usuario, contrasena, rol) VALUES('jperez', '1234', 1);
INSERT INTO Categoria (nombreCategoria) VALUES ('Ficcion'), ('Tecnologia'), ('Infantil'), ('Ciencia'), ('Historia');
INSERT INTO Libro(titulo, autor, anoPublicacion, descripcion, precio, stock, idCategoria) VALUES('Cien anios de soledad', 'Gabriel Garcia Marquez', 1967, 'Obra maestra', 45.00, 10, 1);
INSERT INTO Libro(titulo, autor, anoPublicacion, descripcion, precio, stock, idCategoria) VALUES('Clean Code', 'Robert C. Martin', 2008, 'Guia de codigo limpio', 55.00, 5, 2);
INSERT INTO Libro(titulo, autor, anoPublicacion, descripcion, precio, stock, idCategoria) VALUES('El Principito', 'Antoine de Saint-Exupery', 1943, 'Libro infantil', 25.00, 20, 3);
INSERT INTO Libro(titulo, autor, anoPublicacion, descripcion, precio, stock, idCategoria) VALUES('Breve historia del tiempo', 'Stephen Hawking', 1988, 'Divulgacion cientifica', 60.00, 8, 4);
INSERT INTO Libro(titulo, autor, anoPublicacion, descripcion, precio, stock, idCategoria) VALUES('Sapiens', 'Yuval Noah Harari', 2011, 'Historia de la humanidad', 50.00, 15, 5);
INSERT INTO Libro(titulo, autor, anoPublicacion, descripcion, precio, stock, idCategoria) VALUES('El arte de la guerra', 'Sun Tzu', -500, 'Estrategia militar', 20.00, 12, 5);
