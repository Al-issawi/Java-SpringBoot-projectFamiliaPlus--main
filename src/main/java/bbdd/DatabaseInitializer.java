package bbdd;

import java.io.BufferedReader;
import java.io.FileReader;
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
        System.out.println("Iniciando configuración de base de datos...");

        Connection connection = ConexionBBDD.conectarBBDD();
        if (connection != null) {
            try {
                executeSqlScript(connection);
                System.out.println("Base de datos inicializada correctamente");
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
        // Leer el archivo SQL desde el classpath
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database_init.sql");

        if (inputStream == null) {
            System.out.println(
                    "Archivo database_init.sql no encontrado en resources, intentando desde directorio raíz...");
            // Si no está en resources, intentar desde el directorio raíz del proyecto
            try (BufferedReader reader = new BufferedReader(new FileReader("database_init.sql"))) {
                executeSqlFromReader(connection, reader);
            }
        } else {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                executeSqlFromReader(connection, reader);
            }
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