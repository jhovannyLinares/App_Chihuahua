package mx.morena.negocio.dto;

import java.util.List;

public class IncidenciasCasillasRespDTO {

	private Long idCasilla;
	//private String presentaIncidencias;
	private Integer tipoReporte;
	private List<IncidenciasResponseDTO> incidencia;
	private String horaReporte;
	private Long cantidadPersonasHanVotado;
	private Long boletasUtilizadas;
	private boolean recibioVisitaRepresentante;
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

	public Long getCantidadPersonasHanVotado() {
		return cantidadPersonasHanVotado;
	}

	public void setCantidadPersonasHanVotado(Long cantidadPersonasHanVotado) {
		this.cantidadPersonasHanVotado = cantidadPersonasHanVotado;
	}

	public Long getBoletasUtilizadas() {
		return boletasUtilizadas;
	}

	public void setBoletasUtilizadas(Long boletasUtilizadas) {
		this.boletasUtilizadas = boletasUtilizadas;
	}

	public boolean isRecibioVisitaRepresentante() {
		return recibioVisitaRepresentante;
	}

	public void setRecibioVisitaRepresentante(boolean recibioVisitaRepresentante) {
		this.recibioVisitaRepresentante = recibioVisitaRepresentante;
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

}
