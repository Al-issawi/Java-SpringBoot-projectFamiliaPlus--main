package model;
/**Clase cuidado con constructor y getters y setters
 * @author Mohammed Alisawi
 */
public class Cuidado {
	private String descripcion;
	private String fecha;
	private String nombre;
	private String apellido;

	public Cuidado() {
	}

	public Cuidado(String descripcion, String fecha) {
		this.descripcion = descripcion;
		this.fecha = fecha;
	}

//Getters y Setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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

}