# FAMILIA-PLUS - Sistema de Gestión para Residencias

Aplicación monolítica Spring Boot 2.6.7 con Thymeleaf para gestionar residentes, personal, familiares, actividades y cuidados. Diseño modernizado (hero en portada, tarjetas, pie unificado) y favicon sanitario (heart-pulse) en azul/teal.

## Flujo de ramas

- Desarrollo local: rama `master` (localhost)
- Despliegue: rama `main` (Railway)
- Procedimiento:
  1. Trabaja/commitea en `master`
  2. `git checkout main && git merge master`
  3. Asegura privacidad (sin credenciales en texto plano)
  4. `git push origin main` (despliegue se dispara en Railway)

## Configuración de entorno (privacidad)

La app usa MySQL mediante variables de entorno con valores por defecto solo para local:

- `MYSQL_URL` (p.ej. jdbc:mysql://HOST:PUERTO/familiaplus?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true)
- `MYSQL_USER`
- `MYSQL_PASSWORD`
- `PORT` (lo inyecta Railway automáticamente)

Archivo: `src/main/resources/application.properties` (sin secretos en la rama `main`). Existe `application-production.properties` para PostgreSQL, pero el conector actual del proyecto usa MySQL; recomendamos MySQL también en producción para evitar refactor.

## Ejecutar en local

Requisitos: Java 11+, Maven 3.6+, MySQL 8.0

1. Clonar y entrar en el proyecto

```bash
git clone https://github.com/Al-issawi/projectFamiliaPlus-main.git
cd projectFamiliaPlus-master
```

2. Crear base de datos

```sql
CREATE DATABASE familiaplus CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. Variables de entorno opcionales (si no usas los valores por defecto)

```bash
# Windows PowerShell
$env:MYSQL_URL="jdbc:mysql://localhost:3306/familiaplus?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
$env:MYSQL_USER="root"
$env:MYSQL_PASSWORD="<tu_password>"
```

4. Arrancar

```bash
mvn spring-boot:run
```

App en http://localhost:9000

## Despliegue en Railway (desde cero)

Este repo incluye `railway.toml`:

- build: `mvn clean package -DskipTests`
- start: `java -Dserver.port=$PORT -jar target/*.jar`

Pasos:

1. En Railway, crea un nuevo proyecto y conecta el repo de GitHub.
2. En el servicio web, define variables de entorno:
   - `MYSQL_URL` → URL JDBC de tu MySQL gestionado por Railway u otro proveedor
   - `MYSQL_USER`
   - `MYSQL_PASSWORD`
3. Asegúrate de que el servicio expone el puerto `$PORT` (Railway lo inyecta). No definas un puerto fijo.
4. Branch de deploy: `main`. Cada push a `main` redepliega.
5. Pulsa “Deploy” o “Redeploy” y revisa logs.

Comprobación en logs: debería mostrarse algo como

- `Conexión MySQL OK - Producción`
- mensajes de inicialización de base de datos (DatabaseInitializer) si los scripts se ejecutan.

Si prefieres PostgreSQL en Railway, hace falta un pequeño ajuste en `ConexionBBDD` para usar `DATABASE_URL` y el driver `org.postgresql.Driver`. Podemos realizarlo si lo solicitas.

### Checklist rápido de despliegue

- [ ] Repo conectado en Railway apuntando a rama `main`
- [ ] `MYSQL_URL`, `MYSQL_USER`, `MYSQL_PASSWORD` configurados
- [ ] Esquema MySQL importado una vez (`src/main/resources/database_init_mysql.sql`)
- [ ] Push a `main` realizado
- [ ] Logs muestran “Conexión MySQL OK - Producción”

## Estructura del proyecto

- `src/main/java/`
  - `bbdd/` JDBC helper e inicialización
  - `controllers/` Controladores Spring MVC
  - `model/` Modelos con acceso JDBC
  - `inicio/` Clase principal Spring Boot
- `src/main/resources/`
  - `templates/` Vistas Thymeleaf (index, actividades, sobreNosotros, menú, personal, familiar, contacto)
  - `static/` CSS/JS/media (incluye `favicon.svg` y `css/main.css`)
  - `application.properties` (usa env vars)
- `railway.toml` Configuración de deploy
- `pom.xml` Dependencias y plugin Spring Boot

## Usuarios de prueba (ejemplo)

| Usuario   | Contraseña | Rol           |
| --------- | ---------- | ------------- |
| admin     | admin123   | administrador |
| personal1 | pass123    | personal      |
| familiar1 | pass123    | familiar      |

## Cambios recientes (Nov 2025)

- Unificación de títulos `<title>` y favicon sanitario (azul/teal)
- Rediseño de páginas y pie unificado
- Consolidación de estilos en `css/main.css`
- Privacidad: uso de variables de entorno para credenciales
- Flujo de ramas: `master` (local) → `main` (deploy)

## Contribuir

1. Fork
2. Rama de feature: `git checkout -b feature/mi-cambio`
3. Commits y push
4. Pull Request

## Contacto

GitHub Issues o equipo de desarrollo.# FAMILIA-PLUS - Sistema de Gestión para Residencias

Hey! Este es nuestro proyecto final para el curso de DAW. Desarrollamos un sistema web para gestionar residencias de ancianos.

## ¿Qué hace la aplicación?

Básicamente, permite administrar toda la información de una residencia: los residentes, el personal que trabaja allí, sus familiares, las actividades que se realizan y llevar un registro de los cuidados médicos. La idea es facilitar el trabajo del día a día en este tipo de centros.

## Stack tecnológico

Decidimos usar:

- **Backend:** Java con Spring Boot (versión 2.6.7)
- **Base de datos:** MySQL 8.0
- **Frontend:** HTML, CSS, JavaScript y Thymeleaf para las plantillas
- **Otras herramientas:** Maven para gestión de dependencias, Git para control de versiones y Railway para el deploy

## Base de datos

Creamos 6 tablas principales para organizar toda la información:

- **usuario** → Los diferentes tipos de usuarios (administradores, personal, familiares)
- **residente** → Información personal de cada residente
- **personal** → Datos de empleados y trabajadores
- **familiar** → Contactos familiares de los residentes
- **cuidado** → Historial de cuidados y tratamientos médicos
- **actividad** → Actividades programadas (ejercicios, talleres, etc.)

## Cómo configurarlo en local

### Lo que necesitas tener instalado:

- Java 11 (o más reciente)
- MySQL 8.0
- Maven 3.6+

### Pasos para hacerlo funcionar:

1. **Clona el repo:**

   ```bash
   git clone https://github.com/Al-issawi/projectFamiliaPlus-main.git
   cd projectFamiliaPlus-master
   ```

2. **Configura MySQL:**
   Primero crea la base de datos:

   ```sql
   mysql -u root -p
   CREATE DATABASE familiaplus CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. **Inicializa las tablas:**
   Ejecuta nuestro script que crea todo automáticamente:

   ```bash
   mysql -u root -p < setup_simple.sql
   ```

4. **Ajusta las credenciales:**
   Edita el archivo `src/main/resources/application.properties` y pon tu usuario/contraseña de MySQL

5. **Arranca la app:**

   ```bash
   mvn spring-boot:run
   ```

6. **¡Listo!**
   Abre http://localhost:9000 en tu navegador

## Deploy en producción

Usamos Railway para el despliegue porque es súper fácil. La app detecta automáticamente si está en local o producción:

- **Local:** Usa MySQL en tu máquina (localhost:3306)
- **Producción:** Se conecta automáticamente a la base de datos que Railway te asigna

Solo tienes que conectar tu repo de GitHub con Railway y se despliega automáticamente cada vez que haces push.

## Estructura del proyecto

El proyecto está organizado más o menos así:

- `src/main/java/` - Todo el código Java
  - `bbdd/` - Las clases para conectar con la base de datos
  - `controllers/` - Los controladores web (Spring MVC)
  - `model/` - Las clases modelo (Usuario, Residente, etc.)
  - `inicio/` - La clase principal que arranca Spring Boot
- `src/main/resources/` - Recursos del proyecto
  - `static/` - CSS, JavaScript e imágenes
  - `templates/` - Las páginas HTML (Thymeleaf)
  - `application.properties` - Configuración de la app
- `setup_simple.sql` - Script para crear la base de datos
- Y algunos archivos más de configuración (pom.xml, etc.)

## Usuarios para probar

Creamos estos usuarios de ejemplo para que puedas probar todas las funciones:

| Usuario   | Contraseña | Tipo que es   |
| --------- | ---------- | ------------- |
| admin     | admin123   | administrador |
| personal1 | pass123    | personal      |
| familiar1 | pass123    | familiar      |

## Qué puedes hacer según tu rol

**Si eres administrador:**

- Crear y gestionar todos los usuarios
- Ver toda la información de residentes
- Controlar el personal que trabaja
- Generar reportes (cuando lo terminemos)

**Si eres personal/trabajador:**

- Registrar cuidados que das a los residentes
- Programar actividades y eventos
- Ver la info de tus residentes asignados

**Si eres familiar:**

- Ver la información de tu familiar residente
- Consultar qué cuidados ha recibido
- Ver las actividades programadas

## Últimos cambios

### Noviembre 2025 - Versión actual

Hicimos bastantes mejoras importantes:

**Lo más destacado:**

- Cambiamos toda la base de datos a MySQL (antes teníamos PostgreSQL)
- Ahora funciona tanto en local como en producción sin problemas
- Creamos scripts automáticos para inicializar la BD
- Añadimos datos de prueba para que sea más fácil testear
- Mejoré mucho la documentación (este README)

**Cambios técnicos que hicimos:**

- Actualizamos `application.properties` para MySQL
- Reescribimos la clase `ConexionBBDD.java` para que sea más robusta
- Creamos `DatabaseInitializer.java` para que se configure todo automáticamente
- Los scripts SQL ahora están optimizados para MySQL
- Configuramos variables de entorno para el deploy

## Quieres contribuir?

Si quieres ayudar a mejorar el proyecto:

1. Haz fork del repo
2. Crea tu rama para la nueva función (`git checkout -b mi-nueva-funcion`)
3. Haz commit de tus cambios (`git commit -am 'Añado esta función genial'`)
4. Sube tus cambios (`git push origin mi-nueva-funcion`)
5. Abre un Pull Request

## Contacto

Si tienes algún problema o pregunta, puedes contactarnos por aquí mismo (GitHub Issues) o buscar al equipo de desarrollo.

---

**Equipo 06 - DAW 2021/2023**  
_"Espero que os guste cómo quedó!"_ 😊
