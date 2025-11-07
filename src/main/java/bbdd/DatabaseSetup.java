package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase para probar y crear la base de datos MySQL
 */
public class DatabaseSetup {
    
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "familiaplus";
    private static final String USER = "root";
    private static final String PASSWORD = "User123";
    
    public static void main(String[] args) {
        createDatabase();
    }
    
    public static void createDatabase() {
        Connection connection = null;
        Statement statement = null;
        
        try {
            // Conectar a MySQL sin especificar base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                MYSQL_URL + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", 
                USER, 
                PASSWORD
            );
            
            statement = connection.createStatement();
            
            // Crear la base de datos si no existe
            String createDB = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME + 
                             " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
            statement.executeUpdate(createDB);
            
            System.out.println("Base de datos '" + DATABASE_NAME + "' creada exitosamente (o ya existía)");
            
            // Usar la base de datos
            statement.executeUpdate("USE " + DATABASE_NAME);
            
            System.out.println("Conexión a MySQL exitosa con la base de datos: " + DATABASE_NAME);
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver MySQL no encontrado");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}