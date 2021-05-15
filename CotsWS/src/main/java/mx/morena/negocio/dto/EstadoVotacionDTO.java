package mx.morena.negocio.dto;

public class EstadoVotacionDTO {
	
	private Long idCasilla;
	
	private Boolean seInicio;
	
	private String llegoRc;
	
	private Boolean comenzoVotacion;

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Boolean getSeInicio() {
		return seInicio;
	}

	public void setSeInicio(Boolean seInicio) {
		this.seInicio = seInicio;
	}

	public String getLlegoRc() {
		return llegoRc;
	}

	public void setLlegoRc(String llegoRc) {
		this.llegoRc = llegoRc;
	}

	public Boolean getComenzoVotacion() {
		return comenzoVotacion;
	}

	public void setComenzoVotacion(Boolean comenzoVotacion) {
		this.comenzoVotacion = comenzoVotacion;
	}

}
