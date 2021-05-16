package mx.morena.negocio.dto;

import java.sql.Timestamp;
import java.util.List;

public class IncidenciasCasillasDTO {

	private Long idCasilla;

//	private Long idRg;

	private Integer tipoReporte;

	private Long numero;

	private String presentaIncidencias;

	private List<listIncidenciasDTO> incidencia;

	private Timestamp horaReporte;
	
	/*Se agregan campos para preguntas*/
	private Long personasHanVotado;
	
	private Long boletasUtilizadas;
	
	private boolean recibioVisitaRg;
	
	private List<RepresentantePartidosDTO> partidos;

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Integer getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(Integer tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getPresentaIncidencias() {
		return presentaIncidencias;
	}

	public void setPresentaIncidencias(String presentaIncidencias) {
		this.presentaIncidencias = presentaIncidencias;
	}

	public List<listIncidenciasDTO> getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(List<listIncidenciasDTO> incidencia) {
		this.incidencia = incidencia;
	}

	public Timestamp getHoraReporte() {
		return horaReporte;
	}

	public void setHoraReporte(Timestamp horaReporte) {
		this.horaReporte = horaReporte;
	}

	public Long getBoletasUtilizadas() {
		return boletasUtilizadas;
	}

	public void setBoletasUtilizadas(Long boletasUtilizadas) {
		this.boletasUtilizadas = boletasUtilizadas;
	}

	public List<RepresentantePartidosDTO> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<RepresentantePartidosDTO> partidos) {
		this.partidos = partidos;
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
