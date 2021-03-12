package mx.morena.negocio.dto;

import java.util.List;

public class EntidadDTO {

	private long id;

	private String nombre;

	private List<DistritoFederalDTO> distritosFederales;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DistritoFederalDTO> getDistritosFederales() {
		return distritosFederales;
	}

	public void setDistritosFederales(List<DistritoFederalDTO> distritosFederales) {
		this.distritosFederales = distritosFederales;
	}

	@Override
	public String toString() {
		return "EntidadDTO [id=" + id + ", nombre=" + nombre + ", distritosFederales=" + distritosFederales + "]";
	}
	
	

}
