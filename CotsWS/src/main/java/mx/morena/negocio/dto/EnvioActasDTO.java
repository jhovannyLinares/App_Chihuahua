package mx.morena.negocio.dto;

import java.sql.Time;
import java.sql.Timestamp;

public class EnvioActasDTO {
	
	//private Long idActa;
	private Long tipoVotacion;
	private String rutaActa;
	private Long idCasilla;
	
	/*public Long getIdActa() {
		return idActa;
	}
	public void setIdActa(Long idActa) {
		this.idActa = idActa;
	}*/
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
