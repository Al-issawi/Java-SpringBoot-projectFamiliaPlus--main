package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bbdd.ConexionBBDD;
/**Clase residente
 * 
 */
public class Residente {
	static String n_resi;
	private String nombre;
	private String apellido;
	private int edad;
	private int n_hab;
	private static List<Cuidado> cuidados;

	/**
	 * Recupera la información del residente pasando como parámetro el código de
	 * usuario de tipo familiar.
	 * @author Germán Carrasco
	 * @param idUsuario
	 * @return
	 */
	public static String buscarResi(String idUsuario) {

		Connection con = ConexionBBDD.conectarBBDD();

		String numResi = "-1";

		try {
			PreparedStatement stm;
			stm = con.prepareStatement("SELECT n_resi FROM residente WHERE idUsuario = ?");

			stm.setString(1, idUsuario);

			ResultSet rs = stm.executeQuery();

			if (rs.next()) {
				numResi = rs.getString("n_resi");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numResi;
	}

	/**Saca los datos de BBDD del nombre, apellidos y numero de habitacion de la
	tabla Residente
	@author German Carrasco
	@param numResi
	@see buscarResi
	@see mostrarCuidados
	*/
	
	public static Residente mostrarResi(String idUsuario) {


		Residente residente = new Residente();

		Connection con = ConexionBBDD.conectarBBDD();

		try {
			String numResi = buscarResi(idUsuario);

			PreparedStatement stm1 = con.prepareStatement("SELECT * FROM residente WHERE n_resi = ?");
			stm1.setString(1, numResi);
			ResultSet rs1 = stm1.executeQuery();
			if (rs1.next()) {
				residente.setNombre(rs1.getString("nombre"));
				residente.setApellido(rs1.getString("apellido"));
				residente.setEdad(rs1.getInt("edad"));
				residente.setN_hab(rs1.getInt("n_hab"));
			}
			cuidados = mostrarCuidados(numResi); 

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Llamamos a función para mostrar cuidados de cada Residente
		
		return residente;

	}
	/**Saca los datos de BBDD de la descripción y la fecha de la
	tabla Cuidados
	@author Irene Agea
	@param numResi
	@see Cuidado
	*/
	public static List<Cuidado> mostrarCuidados(String numResi) throws SQLException {

		List<Cuidado> cuidados = new ArrayList<Cuidado>();

		Connection con = ConexionBBDD.conectarBBDD();

		try {
			PreparedStatement stm = con.prepareStatement("SELECT * FROM cuidado WHERE n_resi = ?");
			stm.setString(1, numResi);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				 String descripcion = rs.getString("descripcion"); 
				 String fecha =  rs.getString("fecha");
				 Cuidado cuidado = new Cuidado(descripcion, fecha);
				 
				 cuidados.add(cuidado);
			}
			stm.close();
			con.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return cuidados;

	}
	
	/** Getters y Setters*/
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getN_hab() {
		return n_hab;
	}

	public void setN_hab(int n_hab) {
		this.n_hab = n_hab;
	}

	public String getN_resi() {
		return n_resi;
	}

	public void setN_resi(String n_resi) {
		this.n_resi = n_resi;
	}

	public static List<Cuidado> getCuidados() {
		return cuidados;
	}

	public static void setCuidados(List<Cuidado> cuidados) {
		Residente.cuidados = cuidados;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

}
