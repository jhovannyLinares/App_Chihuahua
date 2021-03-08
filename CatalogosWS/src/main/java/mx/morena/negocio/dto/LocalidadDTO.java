package mx.morena.negocio.dto;

public class LocalidadDTO {
	
	private Long id;
	
	private String descripcion;
	
	private long municipio;

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

	public long getMunicipio() {
		return municipio;
	}

	public void setMunicipio(long municipio) {
		this.municipio = municipio;
	}

}
