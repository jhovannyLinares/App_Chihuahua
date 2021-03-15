package mx.morena.negocio.dto;

public class SeccionDTO {

	private Long id;

	private String descripcion;

	private String localidadId;

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

	public String getLocalidadId() {
		return localidadId;
	}

	public void setLocalidadId(String localidadId) {
		this.localidadId = localidadId;
	}

}
