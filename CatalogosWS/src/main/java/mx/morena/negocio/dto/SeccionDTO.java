package mx.morena.negocio.dto;

public class SeccionDTO {

	private Long id;
	
	private String descripcion;
	
	private long localidad;

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

	public long getLocalidad() {
		return localidad;
	}

	public void setLocalidad(long localidad) {
		this.localidad = localidad;
	}
	
}
