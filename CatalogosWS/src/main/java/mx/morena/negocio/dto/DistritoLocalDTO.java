package mx.morena.negocio.dto;

public class DistritoLocalDTO {
	
	private Long id;
	
	private String cabecera;
	
	private long federal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCabecera() {
		return cabecera;
	}

	public void setCabecera(String cabecera) {
		this.cabecera = cabecera;
	}

	public long getFederal() {
		return federal;
	}

	public void setFederal(long federal) {
		this.federal = federal;
	}

}
