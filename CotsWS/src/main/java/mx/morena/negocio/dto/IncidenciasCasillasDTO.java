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

}
