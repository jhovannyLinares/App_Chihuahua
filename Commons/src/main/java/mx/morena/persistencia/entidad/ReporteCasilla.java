package mx.morena.persistencia.entidad;

import java.sql.Timestamp;

public class ReporteCasilla {

	private Long id;

	private Long idCasilla;

	private Timestamp horaReporte;

	private Long idRg;

	private Long numeroVotos;

	private Timestamp horaCierre;

	private Integer tipoReporte;
	
	/* Se agregan columnas */
	private Long cantidadPersonasHanVotado;
	
	private Long boletasUtilizadas;
	
	private boolean recibioVisitaRepresentante;
	
	private boolean isRc;

	private Long idRc;
	
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

	public Long getIdRg() {
		return idRg;
	}

	public void setIdRg(Long idRg) {
		this.idRg = idRg;
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

	public boolean isRc() {
		return isRc;
	}

	public void setRc(boolean isRc) {
		this.isRc = isRc;
	}

	public Long getIdRc() {
		return idRc;
	}

	public void setIdRc(Long idRc) {
		this.idRc = idRc;
	}
	
}
