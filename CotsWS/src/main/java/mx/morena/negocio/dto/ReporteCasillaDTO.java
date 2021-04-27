package mx.morena.negocio.dto;

import java.sql.Timestamp;

public class ReporteCasillaDTO {

	private Timestamp horaReporte;
	private Long tipoReporte;
	private Boolean capturado;

	public Timestamp getHoraReporte() {
		return horaReporte;
	}

	public void setHoraReporte(Timestamp horaReporte) {
		this.horaReporte = horaReporte;
	}

	public Long getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(Long tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	public Boolean getCapturado() {
		return capturado;
	}

	public void setCapturado(Boolean capturado) {
		this.capturado = capturado;
	}

}
