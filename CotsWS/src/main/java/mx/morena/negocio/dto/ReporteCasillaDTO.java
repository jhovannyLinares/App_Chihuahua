package mx.morena.negocio.dto;

import java.sql.Timestamp;

public class ReporteCasillaDTO {

	private String horaReporte;
	private Long tipoReporte;
	private Boolean capturado;

	public String getHoraReporte() {
		return horaReporte;
	}

	public void setHoraReporte(String horaReporte) {
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
