# AI Coding Agent Guide for `familia_plus`

Concise, project-specific rules to be immediately productive. Focus on how things are actually done here.

## 1. Architecture Snapshot

Monolithic Spring Boot (2.6.7) app; no layered service/repository pattern. Controllers (e.g. `FamiliaController`) call model classes (`Usuario`, `Residente`, etc.) which themselves open JDBC connections via `ConexionBBDD`. Startup initializer `DatabaseInitializer` runs SQL script depending on environment. Thymeleaf templates in `src/main/resources/templates`. Static assets (CSS/JS/images) in `src/main/resources/static`.

## 2. Data Access Pattern

Raw JDBC (PreparedStatement) inside model classes instead of Spring Data JPA repositories. Connection obtained by `ConexionBBDD.conectarBBDD()`; remember to close Statements/Connections (some current code leaks). Queries use simple `SELECT * FROM <table>` with string columns; no transactions. When adding data logic, stay consistent: new method inside the relevant model class; use prepared statements; catch `SQLException`; print/log concise error.

## 3. Environment & Configuration

Local: MySQL (see `application.properties`, port 9000). Production: Railway with PostgreSQL (see `application-production.properties` + env vars). `ConexionBBDD` currently always targets MySQL; PostgreSQL usage goes through different properties but JDBC helper is not switched—avoid introducing mixed assumptions unless refactoring. `DatabaseInitializer` chooses script name: `setup_postgresql.sql` vs `setup_simple.sql` (note: `setup_simple.sql` may be missing; verify before depending on it).

## 4. Build & Run

Use Maven.
Run: `mvn spring-boot:run` (port 9000 local). Packaging: default Spring Boot plugin. Tests are minimal/absent—before writing new ones, create a simple `@SpringBootTest` using H2 or testcontainers if needed (not present yet; keep additions minimal).

## 5. Templates & Static Assets

Templates: `index.html`, `personal.html`, `familiar.html`, etc. Reference static assets with Thymeleaf link expressions for robustness, e.g. replace `href="css/hoja1.css"` with `th:href='@{/css/hoja1.css}'`. Forms bind directly to model objects (`th:object="${usuario}"`). Error messaging uses conditional `<div th:if="${error}">`.

## 6. Styling & Scripts

Multiple CSS files (`hoja.css`, `hoja1.css`, `hoja2.css`) plus a missing referenced `hoja0.css`. Consolidation opportunity: create `main.css` to reduce duplication of layout/header/footer rules. JS (`scripts.js`) holds modal helpers and likely global functions—prefer creating a namespaced object or IIFE to avoid polluting global scope. Use `defer` in `<script>` tags when adding new scripts.

## 7. Login & Role Flow

`/login` POST handled in `FamiliaController.login`: calls `Usuario.buscar(id, pass)`; branches by `tipo` → `personal` and `administrador` share `personal.html`; `familiar` loads resident data via `Residente.mostrarResi`. When extending roles, modify this single method; add specialized template if diverging.

## 8. Adding Data Features (Pattern Example)

Example retrieval pattern (from `Usuario.buscar`):

```java
PreparedStatement stm = con.prepareStatement("SELECT * FROM usuario WHERE idUsuario = ? AND contrasena = ?");
stm.setString(1, idUsuario);
stm.setString(2, pass);
ResultSet rs = stm.executeQuery();
```

For new queries: keep parameters bound, check `rs.next()`, map fields, close resources in finally. Avoid inline string concatenation.

## 9. Common Pitfalls

- Missing resource cleanup (Statements/Connections) → risk of leaks.
- Mixed database assumptions (MySQL-specific driver while production points to PostgreSQL). Be explicit if refactoring.
- Hardcoded credentials in properties—prefer env variables for anything new.
- Direct model DB access couples persistence with domain; large changes may warrant introducing a repository/service layer—do NOT partially refactor; either keep existing pattern or plan full migration.
- Unused or missing static assets (e.g. `hoja0.css`). Verify before referencing.

## 10. Safe Extension Guidelines

- Put new HTML in `templates/`; keep semantic tags and add `th:*` attributes only where dynamic.
- For new CSS: prefer a single consolidated file; use BEM-like naming (`.login-container__title`) to avoid clashes.
- For new DB tables: add DDL to the appropriate setup script and optionally extend `DatabaseInitializer` logic.
- Logging: switch from `System.out.println` to Spring `Logger` if adding complex operations (existing code uses prints—stay consistent unless doing a focused logging enhancement).

## 11. Quick Reference Map

- Entry point: `inicio/FamiliaPlusApplication.java`
- Controller: `controllers/FamiliaController.java`
- DB helper: `bbdd/ConexionBBDD.java`
- DB bootstrap: `bbdd/DatabaseInitializer.java`
- Domain models: `model/*.java` (each may contain JDBC access)
- Templates: `src/main/resources/templates/*.html`
- Static assets: `src/main/resources/static/{css,js,media}`

## 12. When Uncertain

Prefer inspecting existing analogous methods (e.g. `Residente.mostrarResi`) and mirror their structure to maintain consistency. Keep changes minimal & coherent; note assumptions in comments.

---

Feedback requested: Are any sections unclear, missing key workflows (e.g. deployment, data initialization), or do you want deeper guidance on refactoring vs incremental changes?
