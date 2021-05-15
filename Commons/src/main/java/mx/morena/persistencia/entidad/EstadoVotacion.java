package mx.morena.persistencia.entidad;

public class EstadoVotacion {
	
	private Long idCasilla;
	
	private Boolean seInstalo;
	
	private String llegoRc;
	
	private Boolean comenzoVotacion;

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Boolean getSeInstalo() {
		return seInstalo;
	}

	public void setSeInstalo(Boolean seInstalo) {
		this.seInstalo = seInstalo;
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
