package mx.morena.negocio.dto;

import java.util.List;

public class CierreVotacionResponseDTO {
	
	private Long idCasilla;
	private String horaCierre;
	private MotivoCierreDTO motivoCierre;
	private List<IncidenciasResponseDTO> incidencia;

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public String getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(String horaCierre) {
		this.horaCierre = horaCierre;
	}

	public MotivoCierreDTO getMotivoCierre() {
		return motivoCierre;
	}

	public void setMotivoCierre(MotivoCierreDTO motivoCierre) {
		this.motivoCierre = motivoCierre;
	}

	public List<IncidenciasResponseDTO> getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(List<IncidenciasResponseDTO> incidencia) {
		this.incidencia = incidencia;
	}
	
}
