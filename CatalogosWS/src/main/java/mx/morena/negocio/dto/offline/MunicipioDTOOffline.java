package mx.morena.negocio.dto.offline;

import java.util.List;

public class MunicipioDTOOffline {

	private Long id;

	private String descripcion;

	private List<LocalidadDTOOffline> secciones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<LocalidadDTOOffline> getSecciones() {
		return secciones;
	}

	public void setSecciones(List<LocalidadDTOOffline> secciones) {
		this.secciones = secciones;
	}

}
