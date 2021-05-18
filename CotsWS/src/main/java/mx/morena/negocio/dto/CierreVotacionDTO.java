package mx.morena.negocio.dto;

import java.sql.Timestamp;
import java.util.List;

public class CierreVotacionDTO {
	
	private Long idCasilla;
	private Timestamp horaCierre;
	private Long idMotivoCierre;
	private boolean presentaIncidencias;
	private List<listIncidenciasDTO> incidencia;

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

	public Long getIdMotivoCierre() {
		return idMotivoCierre;
	}

	public void setIdMotivoCierre(Long idMotivoCierre) {
		this.idMotivoCierre = idMotivoCierre;
	}

	public boolean isPresentaIncidencias() {
		return presentaIncidencias;
	}

	public void setPresentaIncidencias(boolean presentaIncidencias) {
		this.presentaIncidencias = presentaIncidencias;
	}

	public List<listIncidenciasDTO> getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(List<listIncidenciasDTO> incidencia) {
		this.incidencia = incidencia;
	}
	
}
