-- Base de datos para FAMILIA-PLUS (MySQL)
-- Tablas necesarias para el sistema de gestión de residencia

-- Crear la base de datos (solo si no existe)
CREATE DATABASE IF NOT EXISTS familiaplus CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE familiaplus;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuario (
    idUsuario VARCHAR(50) PRIMARY KEY,
    contrasena VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL CHECK (tipo IN ('familiar', 'personal', 'administrador'))
);

-- Tabla de residentes  
CREATE TABLE IF NOT EXISTS residente (
    n_resi INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    edad INT CHECK (edad >= 0),
    n_hab INT,
    idUsuario VARCHAR(50),
    fecha_ingreso DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario)
);

-- Tabla de personal
CREATE TABLE IF NOT EXISTS personal (
    id_personal INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    idUsuario VARCHAR(50),
    fecha_contratacion DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario)
);

-- Tabla de familiares
CREATE TABLE IF NOT EXISTS familiar (
    id_familiar INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    parentesco VARCHAR(50),
    n_resi INT,
    idUsuario VARCHAR(50),
    FOREIGN KEY (n_resi) REFERENCES residente(n_resi),
    FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario)
);

-- Tabla de cuidados
CREATE TABLE IF NOT EXISTS cuidado (
    id_cuidado INT AUTO_INCREMENT PRIMARY KEY,
    n_resi INT NOT NULL,
    id_personal INT,
    descripcion TEXT NOT NULL,
    fecha DATE DEFAULT (CURRENT_DATE),
    hora TIME DEFAULT (CURRENT_TIME),
    FOREIGN KEY (n_resi) REFERENCES residente(n_resi),
    FOREIGN KEY (id_personal) REFERENCES personal(id_personal)
);

-- Tabla de actividades
CREATE TABLE IF NOT EXISTS actividades (
    id_actividad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    fecha_programada DATE,
    hora_inicio TIME,
    hora_fin TIME,
    estado VARCHAR(50) DEFAULT 'programada' CHECK (estado IN ('programada', 'en_curso', 'completada', 'cancelada'))
);

-- Tabla de participación en actividades
CREATE TABLE IF NOT EXISTS participacion_actividades (
    id_participacion INT AUTO_INCREMENT PRIMARY KEY,
    id_actividad INT NOT NULL,
    n_resi INT NOT NULL,
    fecha_participacion DATE DEFAULT (CURRENT_DATE),
    observaciones TEXT,
    FOREIGN KEY (id_actividad) REFERENCES actividades(id_actividad),
    FOREIGN KEY (n_resi) REFERENCES residente(n_resi)
);

-- Insertar datos de ejemplo
-- Usuarios de ejemplo
INSERT IGNORE INTO usuario (idUsuario, contrasena, nombre, apellido, tipo) VALUES 
('admin', 'admin123', 'Administrador', 'Sistema', 'administrador'),
('personal1', 'pass123', 'María', 'García', 'personal'),
('familiar1', 'pass123', 'Juan', 'Pérez', 'familiar');

-- Personal de ejemplo
INSERT IGNORE INTO personal (nombre, apellido, cargo, telefono, email, idUsuario) VALUES 
('María', 'García', 'Enfermera', '123456789', 'maria.garcia@residencia.com', 'personal1');

-- Residentes de ejemplo
INSERT IGNORE INTO residente (nombre, apellido, edad, n_hab, idUsuario) VALUES 
('Ana', 'Martínez', 85, 101, NULL),
('Carlos', 'López', 78, 102, NULL);

-- Familiares de ejemplo
INSERT IGNORE INTO familiar (nombre, apellido, telefono, email, parentesco, n_resi, idUsuario) VALUES 
('Juan', 'Pérez', '987654321', 'juan.perez@email.com', 'Hijo', 1, 'familiar1');

-- Actividades de ejemplo
INSERT IGNORE INTO actividades (nombre, descripcion, fecha_programada, hora_inicio, hora_fin) VALUES 
('Gimnasia Matutina', 'Ejercicios suaves para mantener la movilidad', CURDATE(), '09:00', '10:00'),
('Bingo Vespertino', 'Juego de bingo para estimular la mente', CURDATE(), '16:00', '17:30'),
('Taller de Manualidades', 'Creación de objetos decorativos', DATE_ADD(CURDATE(), INTERVAL 1 DAY), '10:30', '12:00');

-- Cuidados de ejemplo
INSERT IGNORE INTO cuidado (n_resi, id_personal, descripcion, fecha) VALUES 
(1, 1, 'Administración de medicamentos matutinos', CURDATE()),
(1, 1, 'Control de presión arterial', CURDATE()),
(2, 1, 'Asistencia en el aseo personal', CURDATE());