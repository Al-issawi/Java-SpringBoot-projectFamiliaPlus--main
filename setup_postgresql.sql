-- PostgreSQL Database Setup for FAMILIA-PLUS (Production)
-- This script initializes the database structure for Railway deployment

-- Create tables for PostgreSQL
CREATE TABLE IF NOT EXISTS usuario (
    idUsuario VARCHAR(50) PRIMARY KEY,
    contrasena VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL CHECK (tipo IN ('familiar', 'personal', 'administrador'))
);

CREATE TABLE IF NOT EXISTS residente (
    n_resi SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    edad INTEGER CHECK (edad >= 0),
    n_hab INTEGER,
    fecha_ingreso DATE DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS personal (
    id_personal SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    fecha_contratacion DATE DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS familiar (
    id_familiar SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    parentesco VARCHAR(50),
    n_resi INTEGER,
    FOREIGN KEY (n_resi) REFERENCES residente(n_resi)
);

CREATE TABLE IF NOT EXISTS cuidado (
    id_cuidado SERIAL PRIMARY KEY,
    n_resi INTEGER NOT NULL,
    id_personal INTEGER,
    descripcion TEXT NOT NULL,
    fecha DATE DEFAULT CURRENT_DATE,
    hora TIME DEFAULT CURRENT_TIME,
    FOREIGN KEY (n_resi) REFERENCES residente(n_resi),
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal)
);

CREATE TABLE IF NOT EXISTS actividad (
    id_actividad SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    fecha_programada DATE,
    hora_inicio TIME,
    hora_fin TIME,
    estado VARCHAR(50) DEFAULT 'programada' CHECK (estado IN ('programada', 'en_curso', 'completada', 'cancelada'))
);

-- Insert sample data if tables are empty
INSERT INTO usuario (idUsuario, contrasena, nombre, apellido, tipo) 
SELECT 'admin', 'admin123', 'Administrador', 'Sistema', 'administrador'
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE idUsuario = 'admin');

INSERT INTO usuario (idUsuario, contrasena, nombre, apellido, tipo) 
SELECT 'personal1', 'pass123', 'Maria', 'Garcia', 'personal'
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE idUsuario = 'personal1');

INSERT INTO usuario (idUsuario, contrasena, nombre, apellido, tipo) 
SELECT 'familiar1', 'pass123', 'Juan', 'Perez', 'familiar'
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE idUsuario = 'familiar1');

INSERT INTO personal (nombre, apellido, cargo, telefono, email) 
SELECT 'Maria', 'Garcia', 'Enfermera', '123456789', 'maria.garcia@residencia.com'
WHERE NOT EXISTS (SELECT 1 FROM personal WHERE email = 'maria.garcia@residencia.com');

INSERT INTO residente (nombre, apellido, edad, n_hab) 
SELECT 'Ana', 'Martinez', 85, 101
WHERE NOT EXISTS (SELECT 1 FROM residente WHERE nombre = 'Ana' AND apellido = 'Martinez');

INSERT INTO residente (nombre, apellido, edad, n_hab) 
SELECT 'Carlos', 'Lopez', 78, 102
WHERE NOT EXISTS (SELECT 1 FROM residente WHERE nombre = 'Carlos' AND apellido = 'Lopez');

INSERT INTO familiar (nombre, apellido, telefono, email, parentesco, n_resi) 
SELECT 'Juan', 'Perez', '987654321', 'juan.perez@email.com', 'Hijo', 1
WHERE NOT EXISTS (SELECT 1 FROM familiar WHERE email = 'juan.perez@email.com');

INSERT INTO actividad (nombre, descripcion, fecha_programada, hora_inicio, hora_fin) 
SELECT 'Gimnasia Matutina', 'Ejercicios suaves para mantener la movilidad', CURRENT_DATE, '09:00', '10:00'
WHERE NOT EXISTS (SELECT 1 FROM actividad WHERE nombre = 'Gimnasia Matutina');

INSERT INTO actividad (nombre, descripcion, fecha_programada, hora_inicio, hora_fin) 
SELECT 'Bingo Vespertino', 'Juego de bingo para estimular la mente', CURRENT_DATE, '16:00', '17:30'
WHERE NOT EXISTS (SELECT 1 FROM actividad WHERE nombre = 'Bingo Vespertino');

INSERT INTO cuidado (n_resi, id_personal, descripcion, fecha) 
SELECT 1, 1, 'Administracion de medicamentos matutinos', CURRENT_DATE
WHERE EXISTS (SELECT 1 FROM residente WHERE n_resi = 1) 
AND EXISTS (SELECT 1 FROM personal WHERE id_personal = 1)
AND NOT EXISTS (SELECT 1 FROM cuidado WHERE n_resi = 1 AND descripcion = 'Administracion de medicamentos matutinos');