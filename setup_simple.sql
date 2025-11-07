-- Simple MySQL Database Setup for FAMILIA-PLUS
-- Create database
CREATE DATABASE IF NOT EXISTS familiaplus CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE familiaplus;

-- Drop existing tables if they exist (in correct order due to foreign keys)
DROP TABLE IF EXISTS cuidado;
DROP TABLE IF EXISTS participacion_actividad;
DROP TABLE IF EXISTS familiar;
DROP TABLE IF EXISTS personal;
DROP TABLE IF EXISTS actividad;
DROP TABLE IF EXISTS residente;
DROP TABLE IF EXISTS usuario;

-- Tabla usuarios
CREATE TABLE usuario (
    idUsuario VARCHAR(50) PRIMARY KEY,
    contrasena VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    tipo ENUM('familiar', 'personal', 'administrador') NOT NULL
);

-- Tabla residentes (without foreign key to usuario for simplicity)
CREATE TABLE residente (
    n_resi INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    edad INT CHECK (edad >= 0),
    n_hab INT,
    fecha_ingreso DATE DEFAULT (CURDATE())
);

-- Tabla personal (without foreign key to usuario for simplicity)
CREATE TABLE personal (
    id_personal INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    fecha_contratacion DATE DEFAULT (CURDATE())
);

-- Tabla familiar
CREATE TABLE familiar (
    id_familiar INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    parentesco VARCHAR(50),
    n_resi INT,
    FOREIGN KEY (n_resi) REFERENCES residente(n_resi)
);

-- Tabla cuidados
CREATE TABLE cuidado (
    id_cuidado INT AUTO_INCREMENT PRIMARY KEY,
    n_resi INT NOT NULL,
    id_personal INT,
    descripcion TEXT NOT NULL,
    fecha DATE DEFAULT (CURDATE()),
    hora TIME DEFAULT (CURTIME()),
    FOREIGN KEY (n_resi) REFERENCES residente(n_resi),
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal)
);

-- Tabla actividades
CREATE TABLE actividad (
    id_actividad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    fecha_programada DATE,
    hora_inicio TIME,
    hora_fin TIME,
    estado ENUM('programada', 'en_curso', 'completada', 'cancelada') DEFAULT 'programada'
);

-- Insert sample data
INSERT INTO usuario (idUsuario, contrasena, nombre, apellido, tipo) VALUES 
('admin', 'admin123', 'Administrador', 'Sistema', 'administrador'),
('personal1', 'pass123', 'Maria', 'Garcia', 'personal'),
('familiar1', 'pass123', 'Juan', 'Perez', 'familiar');

INSERT INTO personal (nombre, apellido, cargo, telefono, email) VALUES 
('Maria', 'Garcia', 'Enfermera', '123456789', 'maria.garcia@residencia.com');

INSERT INTO residente (nombre, apellido, edad, n_hab) VALUES 
('Ana', 'Martinez', 85, 101),
('Carlos', 'Lopez', 78, 102);

INSERT INTO familiar (nombre, apellido, telefono, email, parentesco, n_resi) VALUES 
('Juan', 'Perez', '987654321', 'juan.perez@email.com', 'Hijo', 1);

INSERT INTO actividad (nombre, descripcion, fecha_programada, hora_inicio, hora_fin) VALUES 
('Gimnasia Matutina', 'Ejercicios suaves para mantener la movilidad', CURDATE(), '09:00', '10:00'),
('Bingo Vespertino', 'Juego de bingo para estimular la mente', CURDATE(), '16:00', '17:30');

INSERT INTO cuidado (n_resi, id_personal, descripcion, fecha) VALUES 
(1, 1, 'Administracion de medicamentos matutinos', CURDATE()),
(1, 1, 'Control de presion arterial', CURDATE()),
(2, 1, 'Asistencia en el aseo personal', CURDATE());

-- Show created tables
SHOW TABLES;
SELECT 'Database setup completed successfully!' as Message;