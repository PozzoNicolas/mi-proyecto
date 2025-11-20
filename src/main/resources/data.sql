INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Veterinaria (nombre, direccion)
VALUES ('Vet Uno', 'Calle Falsa 123');

INSERT INTO Veterinaria (nombre, direccion)
VALUES ('Vet Dos', 'Juan B. Justo');

INSERT INTO Veterinaria (nombre, direccion)
VALUES ('Vet Tres', 'Juan B. Justo');

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
(1, 2, '09:30'),
(1, 2, '09:45'),
(1, 2, '11:00'),

-- Vet Dos
(2, 2, '10:00'),
(2, 3, '08:00'),
(2, 3, '09:00'),

-- Vet Tres
(3, 2, '10:00'),
(3, 3, '08:00'),
(3, 3, '09:00'),
(3, 1, '11:00');

INSERT INTO Recomendacion (titulo, descripcion, tipo, etapa, sexo, link)

VALUES ('Felicidades por tu cachorro!', 'Asegurate que tu perrito conozca su ambiente, donde tiene que hacer sus necesidades y asegurate que tenga agua a mano. No le dejes toda la comida junta que se la puede llegar a comer toda junta',
        'Perro', 'Cachorro', 'Macho', 'https://articulo.mercadolibre.com.ar/MLA-854035124-kong-puppy-large-juguete-perros-cachorros-_JM');

INSERT INTO Recomendacion (titulo, descripcion, tipo, etapa, sexo, link)
VALUES ('Vacunacion y Desparasitacion', 'Los primeros meses son claves. Sigue el calendario de vacunas y desparasita regularmente.',
        'Perro', 'Cachorro', 'Ambos', 'https://www.lamatanza.gov.ar/saludpublica/centro-zoonosis');


 INSERT INTO Recomendacion (titulo, descripcion, tipo, etapa, sexo, link)
VALUES ('Alimento Sieger', 'Alimento específico para gatitos hasta 1 año. Ayuda al desarrollo de huesos y vista.',
       'Gato', 'Cachorro', 'Ambos', 'https://sieger.com.ar/producto/sieger-katze-kitten/'); --

INSERT INTO Recomendacion (titulo, descripcion, tipo, etapa, sexo, link)
VALUES ('Ejercicio y Paseos', 'Manten una rutina de ejercicio para prevenir el sobrepeso y problemas de conducta.',
        'Perro', 'Adulto', 'Ambos', 'https://www.mercadolibre.com.ar/correa-retractil-doble-para-2-perros-linterna-y-porta-bolsa-color-negro/p/MLA61388099');


INSERT INTO Recomendacion (titulo, descripcion, tipo, etapa, sexo, link)
VALUES ('Control de Peso', 'Si tu perra está esterilizada, es vital controlar las porciones para evitar el aumento de peso.',
        'Perro', 'Adulto', 'Hembra', 'https://www.mercadolibre.com.ar/alimento-cinovita-base-bajo-tenor-graso-pollo-y-carne/up/MLAU232184904');


INSERT INTO Recomendacion (titulo, descripcion, tipo, etapa, sexo, link)
VALUES ('Cuidado Urinario', 'Los gatos machos son propensos a problemas urinarios. Ofrece fuentes de agua y alimento bajo en magnesio.',
        'Gato', 'Adulto', 'Macho', 'https://www.tienda.vitalcrops.com.ar/alimento-catpro-premium-castradosindoor-para-gato-adulto-x-75kg/p/MLA20882150?');


INSERT INTO Recomendacion (titulo, descripcion, tipo, etapa, sexo, link)
VALUES ('Juego Diario', 'Asegurate de jugar al menos 15 minutos al día con tu gato. Es esencial para su salud mental.',
        'Gato', 'Adulto', 'Ambos', 'https://www.mercadolibre.com.ar/gimnasio-rascador-para-gatos-juguete-bajo-dakota-felpa-sisal/p/MLA45654436');


INSERT INTO Recomendacion (titulo, descripcion, tipo, etapa, sexo, link)
VALUES ('Suplemento Articular', 'Considera suplementos de glucosamina y condroitina para mejorar la salud de las articulaciones.',
        'Perro', 'Senior', 'Ambos', 'https://www.mercadolibre.com.ar/condroprotector-sosten-cg-perrosgatos-x-80-comprimidos/p/MLA53751157');

INSERT INTO Recomendacion (titulo, descripcion, tipo, etapa, sexo, link)
VALUES ('Monitoreo Renal', 'En gatos mayores, es crucial realizar análisis de sangre anuales para detectar problemas renales a tiempo.',
        'Gato', 'Senior', 'Ambos', 'https://www.zooplus.es/magazine/gatos/salud-del-gato-y-cuidados/hemograma-en-gatos');


INSERT INTO EnlaceAdopcion (nombre, url, descripcion, provincia) VALUES
    ('Proyecto Cuatro Patas', 'https://proyecto4patas.org',
     'Organizacion dedicada al rescate, rehabilitacion y adopcion responsable de perros y gatos',
    'Buenos Aires');

INSERT INTO EnlaceAdopcion (nombre, url, descripcion, provincia) VALUES
    ('El Campito Refugio', 'https://elcampitorefugio.org',
     'El refugio mas grande de perrros rescatados, ubicado en Esteban Echeverria', 'Buenos Aires');

INSERT INTO EnlaceAdopcion (nombre, url, descripcion, provincia) VALUES
    ('Fundacion Viva la Vida', 'https://www.fundacionvivalavida.com.ar/',
    'Promueve la adopcion, esterilizacion y educacion para la convivencia responsable con mascotas', 'Buenos Aires' );

INSERT INTO EnlaceAdopcion (nombre, url, descripcion, provincia) VALUES
    ('APRANI', 'https://aprani.org/', 'Asociacion Proyectora de Animales en todo el pais', 'Buenos Aires');


