package mx.morena.negocio.dto;

public class DistritoLocalDTO {

	private Long id;

	private String cabecera;

	private long federalId;

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

	public long getFederalId() {
		return federalId;
	}

	public void setFederalId(long federalId) {
		this.federalId = federalId;
	}

}