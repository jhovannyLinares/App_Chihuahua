package mx.morena.negocio.dto;

import java.sql.Timestamp;

public class CierreCasillaDTO {
	
	private Long idCasilla;
	
	private Timestamp horaCierre;

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Timestamp getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(Timestamp horaCierre) {
		this.horaCierre = horaCierre;
	}

}
