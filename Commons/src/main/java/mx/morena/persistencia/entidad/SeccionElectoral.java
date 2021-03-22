package mx.morena.persistencia.entidad;

public class SeccionElectoral {

	private Long id;

	private String descripcion;

	private Long localidad;

	private Long cot;

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

	public Long getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Long localidad) {
		this.localidad = localidad;
	}

	public Long getCot() {
		return cot;
	}

	public void setCot(Long cot) {
		this.cot = cot;
	}

}
