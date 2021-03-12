package mx.morena.negocio.dto;

import java.util.List;

public class MunicipioDTO {

	private Long id;

	private String descripcion;

	private List<LocalidadDTO> localidades;

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

	public List<LocalidadDTO> getLocalidades() {
		return localidades;
	}

	public void setLocalidades(List<LocalidadDTO> localidades) {
		this.localidades = localidades;
	}

	@Override
	public String toString() {
		return "MunicipioDTO [id=" + id + ", descripcion=" + descripcion + ", localidades=" + localidades + "]";
	}

}
