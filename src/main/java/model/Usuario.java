package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bbdd.ConexionBBDD;

public class Usuario {

	private String idUsuario;
	private String contrasena;
	private String nombre;
	private String apellido;
	private String tipo;
		
	
	/**
	 * Busca información del usuario en función del login y pass
	 * @author Irene Agea
	 * @param idUsuario
	 * @param pass
	 * @return Usuario
	 */
	public Usuario buscar(String idUsuario, String pass) {
		Usuario usuario = new Usuario();

		Connection con = ConexionBBDD.conectarBBDD();
		
		try {
			PreparedStatement stm;
			stm = con.prepareStatement("SELECT * FROM usuario WHERE idUsuario = ? AND contraseña = ?");

			stm.setString(1, idUsuario);
			stm.setString(2, pass);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				usuario.setIdUsuario(idUsuario);
				usuario.setNombre(rs.getString("nombre"));
				usuario.setApellido(rs.getString("apellido"));
				usuario.setTipo(rs.getString("tipo"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return usuario;

	}
	
	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

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

	public String getTipoUsuario() {
		return getTipo();
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipoUsuario(String tipo) {
		setTipo(tipo);
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	

	
}
