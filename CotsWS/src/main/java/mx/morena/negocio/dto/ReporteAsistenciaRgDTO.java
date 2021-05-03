package mx.morena.negocio.dto;

public class ReporteAsistenciaRgDTO {
	
	private String municipio;
	
	private Long idFederal;
	
	private Long secion;

	private String casillas;

	private Long rcMeta;

	private Long rcAsistencia;

	private Double rcPorcentaje;

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Long getIdFederal() {
		return idFederal;
	}

	public void setIdFederal(Long idFederal) {
		this.idFederal = idFederal;
	}

	public Long getSecion() {
		return secion;
	}

	public void setSecion(Long secion) {
		this.secion = secion;
	}

	public String getCasillas() {
		return casillas;
	}

	public void setCasillas(String casillas) {
		this.casillas = casillas;
	}

	public Long getRcMeta() {
		return rcMeta;
	}

	public void setRcMeta(Long rcMeta) {
		this.rcMeta = rcMeta;
	}

	public Long getRcAsistencia() {
		return rcAsistencia;
	}

	public void setRcAsistencia(Long rcAsistencia) {
		this.rcAsistencia = rcAsistencia;
	}

	public Double getRcPorcentaje() {
		return rcPorcentaje;
	}

	public void setRcPorcentaje(Double rcPorcentaje) {
		this.rcPorcentaje = rcPorcentaje;
	}

}
