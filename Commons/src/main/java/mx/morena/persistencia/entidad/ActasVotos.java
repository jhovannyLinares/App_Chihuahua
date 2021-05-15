package mx.morena.persistencia.entidad;

public class ActasVotos {
	
	private Long idCasilla;
	
	private Boolean gobernador;
	
	private Boolean diputadoLocal;
	
	private Boolean diputadoFedaral;
	
	private Boolean presidenteMunicipal;
	
	private Boolean sindico;

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Boolean getGobernador() {
		return gobernador;
	}

	public void setGobernador(Boolean gobernador) {
		this.gobernador = gobernador;
	}

	public Boolean getDiputadoLocal() {
		return diputadoLocal;
	}

	public void setDiputadoLocal(Boolean diputadoLocal) {
		this.diputadoLocal = diputadoLocal;
	}

	public Boolean getDiputadoFedaral() {
		return diputadoFedaral;
	}

	public void setDiputadoFedaral(Boolean diputadoFedaral) {
		this.diputadoFedaral = diputadoFedaral;
	}

	public Boolean getPresidenteMunicipal() {
		return presidenteMunicipal;
	}

	public void setPresidenteMunicipal(Boolean presidenteMunicipal) {
		this.presidenteMunicipal = presidenteMunicipal;
	}

	public Boolean getSindico() {
		return sindico;
	}

	public void setSindico(Boolean sindico) {
		this.sindico = sindico;
	}

}
