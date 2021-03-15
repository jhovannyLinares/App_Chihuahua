package mx.morena.negocio.dto.offline;

import java.util.List;

public class MunicipioDTOOffline {

	private Long id;

	private String descripcion;

	private List<LocalidadDTOOffline> localidades;

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

	public List<LocalidadDTOOffline> getLocalidades() {
		return localidades;
	}

	public void setLocalidades(List<LocalidadDTOOffline> localidades) {
		this.localidades = localidades;
	}

	@Override
	public String toString() {
		return "MunicipioDTO [id=" + id + ", descripcion=" + descripcion + ", localidades=" + localidades + "]";
	}

}
