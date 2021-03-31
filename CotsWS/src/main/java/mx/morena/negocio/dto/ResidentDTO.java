package mx.morena.presentacion.controlador;

public class Resident {

	String nombre;
	String aMaterno;

	public Resident(String nombre, String aMaterno) {
		this.nombre = nombre;
		this.aMaterno = aMaterno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getaMaterno() {
		return aMaterno;
	}

	public void setaMaterno(String aMaterno) {
		this.aMaterno = aMaterno;
	}

}
