package mx.morena.persistencia.entidad;

import java.sql.Time;
import java.sql.Timestamp;

public class ReporteCasilla {

	private Long id;

	private Long idCasilla;

	private Time horaReporte;

	private Long idRg;

	private Long numeroVotos;

	private Time horaCierre;

	private Long tipoReporte;

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

	public Time getHoraReporte() {
		return horaReporte;
	}

	public void setHoraReporte(Time horaReporte) {
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

	public Time getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(Time horaCierre) {
		this.horaCierre = horaCierre;
	}

	public Long getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(Long tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

}
