package mx.morena.negocio.dto.offline;

import java.util.List;

public class EntidadDTOOffline {

	private long id;

	private String nombre;

	private List<DistritoFederalDTOOffline> distritosFederales;

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

	public List<DistritoFederalDTOOffline> getDistritosFederales() {
		return distritosFederales;
	}

	public void setDistritosFederales(List<DistritoFederalDTOOffline> distritosFederales) {
		this.distritosFederales = distritosFederales;
	}

	@Override
	public String toString() {
		return "EntidadDTO [id=" + id + ", nombre=" + nombre + ", distritosFederales=" + distritosFederales + "]";
	}
	
	

}
