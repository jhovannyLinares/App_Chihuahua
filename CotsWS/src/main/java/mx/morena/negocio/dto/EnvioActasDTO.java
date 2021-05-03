package mx.morena.negocio.dto;

public class EnvioActasDTO {

	// private Long idActa;
	private Long tipoVotacion;
	private String rutaActa;
	private Long idCasilla;

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
