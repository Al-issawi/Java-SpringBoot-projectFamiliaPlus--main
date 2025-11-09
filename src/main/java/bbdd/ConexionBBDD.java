package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de conexión a la bbdd MySQL
 * Funciona tanto en local como en producción
 * 
 * @author Irene Agea
 */
public class ConexionBBDD {

	// Configuración para MySQL
	private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";

	// Variables de conexión que se obtienen del entorno o valores por defecto para
	// MySQL
	private static final String URL = System.getenv("MYSQL_URL") != null ? System.getenv("MYSQL_URL")
			: "jdbc:mysql://localhost:3306/familiaplus";

	private static final String USUARIO = System.getenv("MYSQL_USER") != null ? System.getenv("MYSQL_USER")
			: "root";

	private static final String CLAVE = System.getenv("MYSQL_PASSWORD") != null ? System.getenv("MYSQL_PASSWORD")
			: "User1234";

	public static Connection conectar() {
		return conectarBBDD();
	}

	public static Connection conectarBBDD() {
		Connection conexion = null;

		try {
			Class.forName(CONTROLADOR);

			// Usar la URL directamente para MySQL
			String urlConexion = URL + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

			conexion = DriverManager.getConnection(urlConexion, USUARIO, CLAVE);
			System.out.println(
					"Conexión MySQL OK - " + (System.getenv("MYSQL_URL") != null ? "Producción" : "Local"));

		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador MySQL");
			e.printStackTrace();

		} catch (SQLException e) {
			System.out.println("Error en la conexión MySQL");
			e.printStackTrace();
		}

		return conexion;
	}

}
