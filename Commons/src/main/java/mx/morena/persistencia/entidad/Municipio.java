package mx.morena.persistencia.entidad;

public class Municipio {

	private Long id;

	private String descripcion;
	
	private Long federalId;

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

	public Long getFederalId() {
		return federalId;
	}

	public void setFederalId(Long federalId) {
		this.federalId = federalId;
	}

}
