package mx.morena.negocio.dto;

public class ReporteCasillaDTO {

	private String horaReporte;
	private Integer tipoReporte;
	private Boolean capturado;

	public String getHoraReporte() {
		return horaReporte;
	}

	public void setHoraReporte(String horaReporte) {
		this.horaReporte = horaReporte;
	}

	public Integer getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(Integer tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	public Boolean getCapturado() {
		return capturado;
	}

	public void setCapturado(Boolean capturado) {
		this.capturado = capturado;
	}

}
