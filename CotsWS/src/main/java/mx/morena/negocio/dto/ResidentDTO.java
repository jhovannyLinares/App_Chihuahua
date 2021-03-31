package mx.morena.negocio.dto;

public class ResidentDTO {

	String nombre;
	String materno;

	public ResidentDTO(String nombre, String materno) {
		this.nombre = nombre;
		this.materno = materno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}

}
