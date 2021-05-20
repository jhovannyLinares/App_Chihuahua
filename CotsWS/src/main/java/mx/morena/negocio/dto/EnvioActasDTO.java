package mx.morena.negocio.dto;

public class EnvioActasDTO {

	// private Long idActa;
	private Long tipoVotacion;
	private String rutaActa;
	private Long idCasilla;
	private Long tipoActa;
	private String tipoMime;
	private String extensionActa;
	private boolean copiaRespuestaGobernador;
	private boolean copiaRespuestaDiputadoLocal;
	private boolean copiaRespuestaSindico;
	private boolean copiaRespuestaDiputadoFederal;
	private boolean copiaRespuestaPresidenteMunicipal;

	public String getTipoMime() {
		return tipoMime;
	}

	public void setTipoMime(String tipoMime) {
		this.tipoMime = tipoMime;
	}

	public String getExtensionActa() {
		return extensionActa;
	}

	public void setExtensionActa(String extensionActa) {
		this.extensionActa = extensionActa;
	}

	public boolean isCopiaRespuestaPresidenteMunicipal() {
		return copiaRespuestaPresidenteMunicipal;
	}

	public void setCopiaRespuestaPresidenteMunicipal(boolean copiaRespuestaPresidenteMunicipal) {
		this.copiaRespuestaPresidenteMunicipal = copiaRespuestaPresidenteMunicipal;
	}

	public Long getTipoActa() {
		return tipoActa;
	}

	public void setTipoActa(Long tipoActa) {
		this.tipoActa = tipoActa;
	}

	public boolean isCopiaRespuestaGobernador() {
		return copiaRespuestaGobernador;
	}

	public void setCopiaRespuestaGobernador(boolean copiaRespuestaGobernador) {
		this.copiaRespuestaGobernador = copiaRespuestaGobernador;
	}

	public boolean isCopiaRespuestaDiputadoLocal() {
		return copiaRespuestaDiputadoLocal;
	}

	public void setCopiaRespuestaDiputadoLocal(boolean copiaRespuestaDiputadoLocal) {
		this.copiaRespuestaDiputadoLocal = copiaRespuestaDiputadoLocal;
	}

	public boolean isCopiaRespuestaSindico() {
		return copiaRespuestaSindico;
	}

	public void setCopiaRespuestaSindico(boolean copiaRespuestaSindico) {
		this.copiaRespuestaSindico = copiaRespuestaSindico;
	}

	public boolean isCopiaRespuestaDiputadoFederal() {
		return copiaRespuestaDiputadoFederal;
	}

	public void setCopiaRespuestaDiputadoFederal(boolean copiaRespuestaDiputadoFederal) {
		this.copiaRespuestaDiputadoFederal = copiaRespuestaDiputadoFederal;
	}

	public Long getTipoVotacion() {
		return tipoVotacion;
	}

	public void setTipoVotacion(Long tipoVotacion) {
		this.tipoVotacion = tipoVotacion;
	}

	public String getRutaActa() {
		return rutaActa;
	}

	public void setRutaActa(String rutaActa) {
		this.rutaActa = rutaActa;
	}

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

}
