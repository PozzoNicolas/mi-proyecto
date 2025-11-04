INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Veterinaria (nombre, direccion)
VALUES ('Vet Uno', 'Calle Falsa 123');

INSERT INTO Veterinaria (nombre, direccion)
VALUES ('Vet Dos', 'Juan B. Justo');

INSERT INTO Profesional (id, nombre, dni)
VALUES
(null, 'Dr. Juan Martinez', 12345678),
(null, 'Dra. Ana Lopez', 23456789),
(null, 'Dr. Carlos Gomez', 34567890);

INSERT INTO veterinaria_profesional_horario (veterinaria_id, profesional_id, horario)
VALUES
-- Vet Uno
(1, 1, '09:00'),
(1, 1, '10:00'),
(1, 2, '09:00'),
(1, 2, '11:00'),

-- Vet Dos
(2, 2, '10:00'),
(2, 3, '08:00'),
(2, 3, '09:00');
