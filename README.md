# FAMILIA-PLUS 🏠 - Gestor de Residencias

## 📋 Descripción del Proyecto

Sistema integral de gestión para residencias de ancianos que permite administrar residentes, personal, familiares, actividades y cuidados médicos de manera eficiente y organizada.

## 🔧 Tecnologías Utilizadas

- **Backend:** Java 11 + Spring Boot 2.6.7
- **Base de Datos:** MySQL 8.0
- **Frontend:** HTML5 + CSS3 + JavaScript + Thymeleaf
- **Herramientas:** Maven, Git, Railway (Deployment)

## 📊 Estructura de la Base de Datos

### Tablas Principales:

- **`usuario`** - Gestión de usuarios del sistema (admin, personal, familiar)
- **`residente`** - Información de los residentes de la residencia
- **`personal`** - Datos del personal trabajador
- **`familiar`** - Información de familiares de los residentes
- **`cuidado`** - Registro de cuidados médicos y atención
- **`actividad`** - Programación de actividades y eventos

## 🚀 Configuración Local

### Prerrequisitos:

- Java 11 o superior
- MySQL 8.0
- Maven 3.6+

### Pasos de Instalación:

1. **Clonar el repositorio:**

   ```bash
   git clone https://github.com/Al-issawi/projectFamiliaPlus-main.git
   cd projectFamiliaPlus-master
   ```

2. **Configurar MySQL:**

   ```sql
   # Crear base de datos
   mysql -u root -p
   CREATE DATABASE familiaplus CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. **Ejecutar script de inicialización:**

   ```bash
   mysql -u root -p < setup_simple.sql
   ```

4. **Configurar credenciales:**

   - Editar `src/main/resources/application.properties`
   - Actualizar usuario y contraseña de MySQL

5. **Ejecutar la aplicación:**

   ```bash
   mvn spring-boot:run
   ```

6. **Acceder a la aplicación:**
   - URL: http://localhost:9000

## 🌐 Despliegue en Producción

### Railway Deployment:

- **URL de Producción:** [Tu URL de Railway]
- **Base de Datos:** PostgreSQL (Automática)
- **Variables de Entorno:**
  - `DATABASE_URL` - URL de conexión PostgreSQL
  - `PORT` - Puerto del servidor (automático)

### Configuración para Producción:

La aplicación detecta automáticamente el entorno y utiliza:

- **Local:** MySQL (localhost:3306)
- **Producción:** PostgreSQL (Railway)

## 📁 Estructura del Proyecto

```
projectFamiliaPlus-master/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── bbdd/              # Conexión y configuración BD
│   │   │   ├── controllers/       # Controladores Spring MVC
│   │   │   ├── inicio/            # Clase principal Spring Boot
│   │   │   └── model/             # Modelos de datos
│   │   └── resources/
│   │       ├── static/            # CSS, JS, imágenes
│   │       ├── templates/         # Plantillas Thymeleaf
│   │       └── application.properties
├── database_setup_mysql.sql      # Script inicialización MySQL
├── setup_simple.sql             # Script simplificado MySQL
└── README.md
```

## 🔑 Usuarios de Prueba

| Usuario   | Contraseña | Tipo          |
| --------- | ---------- | ------------- |
| admin     | admin123   | administrador |
| personal1 | pass123    | personal      |
| familiar1 | pass123    | familiar      |

## ✨ Funcionalidades Principales

### Para Administradores:

- 👥 Gestión completa de usuarios
- 🏥 Administración de residentes
- 👨‍⚕️ Control del personal
- 📊 Reportes y estadísticas

### Para Personal:

- 📝 Registro de cuidados
- 📅 Programación de actividades
- 👴 Gestión de residentes asignados

### Para Familiares:

- 👀 Visualización de información del residente
- 📋 Consulta de cuidados recibidos
- 📅 Ver actividades programadas

## 🔄 Historial de Cambios

### Version 2.0 (Noviembre 2025)

- ✅ **Migración completa a MySQL 8.0**
- ✅ **Configuración dual Local/Producción**
- ✅ **Optimización de conexiones BD**
- ✅ **Scripts de inicialización automatizados**
- ✅ **Mejora en la estructura de tablas**
- ✅ **Datos de prueba incluidos**
- ✅ **Documentación actualizada**
- ✅ **Compatible con Railway deployment**

### Mejoras Técnicas:

- 🔧 Actualizada configuración `application.properties`
- 🔧 Mejorada clase `ConexionBBDD.java`
- 🔧 Añadido `DatabaseInitializer.java`
- 🔧 Scripts SQL optimizados para MySQL
- 🔧 Variables de entorno para producción

## 🤝 Contribución

Para contribuir al proyecto:

1. Fork el repositorio
2. Crea una rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Añadir nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request

## 📞 Soporte

Para soporte o consultas sobre el proyecto, contactar al equipo de desarrollo.

---

**Equipo 06 - Proyecto DAW 2021/2023** ✨
