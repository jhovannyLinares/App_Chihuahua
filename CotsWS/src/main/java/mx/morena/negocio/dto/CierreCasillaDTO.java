package mx.morena.negocio.dto;

import java.sql.Time;
import java.sql.Timestamp;

public class CierreCasillaDTO {
	
	private Long idCasilla;
	
	private Time horaCierre;

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Time getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(Time horaCierre) {
		this.horaCierre = horaCierre;
	}

}
