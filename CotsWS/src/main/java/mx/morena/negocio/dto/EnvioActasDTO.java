package mx.morena.negocio.dto;

public class EnvioActasDTO {

	// private Long idActa;
	private Long tipoVotacion;
	private String rutaActa;
	private Long idCasilla;
	private Long tipoActa;
	private boolean copiaRespuestaGobernador;
	private boolean copiaRespuestaDiputadoLocal;
	private boolean copiaRespuestaSindico;
	private boolean copiaRespuestaDiputadoFederal;

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
