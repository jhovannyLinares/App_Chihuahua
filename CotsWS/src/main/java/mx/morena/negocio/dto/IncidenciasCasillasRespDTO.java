package mx.morena.negocio.dto;

import java.util.List;

public class IncidenciasCasillasRespDTO {

	private Long idCasilla;
	//private String presentaIncidencias;
	private Integer tipoReporte;
	private List<IncidenciasResponseDTO> incidencia;
	private String horaReporte;
	private Long personasHanVotado;
	private Long boletasUtilizadas;
	private boolean recibioVisitaRg;
	private List<RepresentantePartidosRespDTO> partidos;

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public List<IncidenciasResponseDTO> getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(List<IncidenciasResponseDTO> incidencia) {
		this.incidencia = incidencia;
	}

	public String getHoraReporte() {
		return horaReporte;
	}

	public void setHoraReporte(String horaReporte) {
		this.horaReporte = horaReporte;
	}

	public Long getBoletasUtilizadas() {
		return boletasUtilizadas;
	}

	public void setBoletasUtilizadas(Long boletasUtilizadas) {
		this.boletasUtilizadas = boletasUtilizadas;
	}

	public List<RepresentantePartidosRespDTO> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<RepresentantePartidosRespDTO> partidos) {
		this.partidos = partidos;
	}

	public Integer getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(Integer tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	public Long getPersonasHanVotado() {
		return personasHanVotado;
	}

	public void setPersonasHanVotado(Long personasHanVotado) {
		this.personasHanVotado = personasHanVotado;
	}

	public boolean isRecibioVisitaRg() {
		return recibioVisitaRg;
	}

	public void setRecibioVisitaRg(boolean recibioVisitaRg) {
		this.recibioVisitaRg = recibioVisitaRg;
	}
	
}
