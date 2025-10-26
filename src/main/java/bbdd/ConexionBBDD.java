package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Usuario;
/** Clase de conexión a la bbdd de MySQL en puerto 3306
@author Irene Agea
*/
public class ConexionBBDD {

	private static final String CONTROLADOR = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://ec2-54-246-185-161.eu-west-1.compute.amazonaws.com:5432/dc7179ss4bmd8p";
	private static final String USUARIO = "gjilajvhgtbvqw";
	private static final String CLAVE = "3412cd34d7289ef9d24bfb83e11e5c56b876da0bc9be888a5d5b077c32fe5217";

	public static Connection conectar() {
		return conectarBBDD();
	}

	public static Connection conectarBBDD() {
		Connection conexion = null;

		try {
			Class.forName(CONTROLADOR);
			conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
			System.out.println("Conexión OK");

		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();

		} catch (SQLException e) {
			System.out.println("Error en la conexión");
			e.printStackTrace();
		}

		return conexion;
	}
	
}
