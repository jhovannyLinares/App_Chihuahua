package mx.morena.negocio.dto;

public class MunicipioDTO {
	
	private Long id;
	
	private String descripcion;
	
	private long local;

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

	public long getLocal() {
		return local;
	}

	public void setLocal(long local) {
		this.local = local;
	}

}
