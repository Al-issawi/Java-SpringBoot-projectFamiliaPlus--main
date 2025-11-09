package bbdd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * Inicializador de la base de datos
 * Ejecuta el script SQL para crear las tablas necesarias
 */
@Component
public class DatabaseInitializer {

    @PostConstruct
    public void initDatabase() {
        // Detect environment based on presence of MySQL env vars instead of
        // DATABASE_URL
        boolean mysqlEnv = System.getenv("MYSQL_URL") != null;
        String environment = mysqlEnv ? "Producción (MySQL)" : "Local (MySQL)";
        System.out.println("Iniciando configuración de base de datos - " + environment);

        Connection connection = ConexionBBDD.conectarBBDD();
        if (connection != null) {
            try {
                executeSqlScript(connection);
                System.out.println("Base de datos inicializada correctamente - " + environment);
            } catch (Exception e) {
                System.err.println("Error al inicializar la base de datos: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("No se pudo establecer conexión con la base de datos");
        }
    }

    private void executeSqlScript(Connection connection) throws Exception {
        // Prefer MySQL init script; fall back to legacy simple script
        String scriptName = "database_init_mysql.sql";

        // Intentar cargar scriptName; si no existe, intentar setup_simple.sql

        // Leer el archivo SQL desde el classpath
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(scriptName);

        if (inputStream == null) {
            // Intentar script alternativo
            inputStream = getClass().getClassLoader().getResourceAsStream("setup_simple.sql");
        }

        if (inputStream == null) {
            System.out.println("[DatabaseInitializer] No se encontró ningún script de inicialización (omitido).");
            return; // Silenciar ausencia de script
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            executeSqlFromReader(connection, reader);
        }
    }

    private void executeSqlFromReader(Connection connection, BufferedReader reader) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            // Ignorar comentarios y líneas vacías
            line = line.trim();
            if (line.isEmpty() || line.startsWith("--")) {
                continue;
            }

            sqlBuilder.append(line).append("\n");

            // Si la línea termina con ;, ejecutar la sentencia
            if (line.endsWith(";")) {
                String sql = sqlBuilder.toString().trim();
                if (!sql.isEmpty()) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(sql);
                        System.out.println("Ejecutado: " + sql.substring(0, Math.min(50, sql.length())) + "...");
                    } catch (SQLException e) {
                        // Si es un error de "ya existe", lo ignoramos
                        if (!e.getMessage().toLowerCase().contains("already exists") &&
                                !e.getMessage().toLowerCase().contains("ya existe") &&
                                !e.getMessage().toLowerCase().contains("duplicate")) {
                            System.err.println("Error ejecutando SQL: " + sql);
                            throw e;
                        }
                    }
                }
                sqlBuilder = new StringBuilder();
            }
        }
    }
}