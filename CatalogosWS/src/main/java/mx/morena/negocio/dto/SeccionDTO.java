package mx.morena.negocio.dto;

public class SeccionDTO {

	private Long id;

	private String descripcion;

	private long localidadId;

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

	public long getLocalidadId() {
		return localidadId;
	}

	public void setLocalidadId(long localidadId) {
		this.localidadId = localidadId;
	}

}
