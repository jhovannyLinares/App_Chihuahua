package mx.morena.persistencia.entidad;

import java.sql.Timestamp;

public class ReporteCasilla {

	private Long id;

	private Long idCasilla;

	private Timestamp horaReporte;

//	private Long idRg;

	private Long numeroVotos;

	private Timestamp horaCierre;

	private Integer tipoReporte;
	
	/* Se agregan columnas */
	private Long personasHanVotado;
	
	private Long boletasUtilizadas;
	
	private boolean recibioVisitaRg;
	
//	private boolean isRc;
	private Long idRc;
	
	private Long idMotivoCierre;
	
	private boolean isCerrada;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Timestamp getHoraReporte() {
		return horaReporte;
	}

	public void setHoraReporte(Timestamp horaReporte) {
		this.horaReporte = horaReporte;
	}

	public Long getNumeroVotos() {
		return numeroVotos;
	}

	public void setNumeroVotos(Long numeroVotos) {
		this.numeroVotos = numeroVotos;
	}

	public Timestamp getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(Timestamp horaCierre) {
		this.horaCierre = horaCierre;
	}

	public Integer getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(Integer tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	public Long getBoletasUtilizadas() {
		return boletasUtilizadas;
	}

	public void setBoletasUtilizadas(Long boletasUtilizadas) {
		this.boletasUtilizadas = boletasUtilizadas;
	}

	public Long getIdRc() {
		return idRc;
	}

	public void setIdRc(Long idRc) {
		this.idRc = idRc;
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

	public Long getIdMotivoCierre() {
		return idMotivoCierre;
	}

	public void setIdMotivoCierre(Long idMotivoCierre) {
		this.idMotivoCierre = idMotivoCierre;
	}

	public boolean isCerrada() {
		return isCerrada;
	}

	public void setCerrada(boolean isCerrada) {
		this.isCerrada = isCerrada;
	}
	
}
