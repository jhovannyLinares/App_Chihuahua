package mx.morena.persistencia.entidad;

import java.sql.Timestamp;

public class EnvioActas {

	private Long idActa;
	private Long tipoVotacion;
	private String rutaActa;
	private Long idCasilla;
	private Timestamp registroActa;

	public Long getIdActa() {
		return idActa;
	}

	public void setIdActa(Long idActa) {
		this.idActa = idActa;
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

	public Timestamp getRegistroActa() {
		return registroActa;
	}

	public void setRegistroActa(Timestamp registroActa) {
		this.registroActa = registroActa;
	}

}
