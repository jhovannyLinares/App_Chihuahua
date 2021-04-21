package mx.morena.persistencia.entidad;

import java.sql.Timestamp;

public class ReporteCasilla {
	
	private Long id;
	
	private Long idCasilla;
	
	private Timestamp horaReporte;
	
	private Long idRg;
	
	private Long numeroVotos;
	
	private Timestamp horaCierre;

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

}