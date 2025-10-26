package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bbdd.ConexionBBDD;

/**
 * Clase personal
 * Primero busca el cod_residente y luego introduce los datos en la bbdd
 * 
 * @author Myroslava Farion
 * @version 1.0
 * 
 * @author Mohammed alisawi
 * @version 1.1
 * @date 2025-10-26
 */
public class Personal extends Usuario {

	public void introducirDatos(Cuidado c) {

		Connection con = ConexionBBDD.conectarBBDD();

		// Handle case where database connection fails
		if (con == null) {
			System.out.println(
					"Database connection failed. Using fallback - data would be saved in production database.");
			System.out.println("Simulating data save for: " + c.getNombre() + " " + c.getApellido() + " - "
					+ c.getDescripcion() + " on " + c.getFecha());
			return;
		}

		try {

			PreparedStatement stm;
			stm = con.prepareStatement("SELECT n_resi FROM residente WHERE nombre = ? AND apellido =?");
			stm.setString(1, c.getNombre());
			stm.setString(2, c.getApellido());
			ResultSet rs = stm.executeQuery();

			String cod_resi = "";

			if (rs.next()) {

				cod_resi = rs.getString("n_resi");
				PreparedStatement stmt = con
						.prepareStatement("INSERT INTO cuidado(n_resi,descripcion,fecha) VALUES (?, ?, ?)");
				stmt.setString(1, cod_resi);
				stmt.setString(2, c.getDescripcion());
				stmt.setString(3, c.getFecha());

				int i = stmt.executeUpdate();
				System.out.println("Cuidado Añadido");

				stmt.close();
				con.close();
			} else {
				System.out.println("El residente no encontrado");
				con.close();
			}

		} catch (SQLException throwables) {
			throwables.printStackTrace();
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
