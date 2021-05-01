package mx.morena.negocio.dto;

public class ReporteAsistenciaCrgDTO {
	
	private Long idFederal;
	
	private Long ruta;
	
	private Long casillas;
	
	private Long rgMeta;
	
	private Long rcMeta;
	
	private Long rgAsistencia;
	
	private Long rcAsistencia;
	
	private Double rgPorcentaje;
	
	private Double rcPorcentaje;

	public Long getIdFederal() {
		return idFederal;
	}

	public void setIdFederal(Long idFederal) {
		this.idFederal = idFederal;
	}

	public Long getRuta() {
		return ruta;
	}

	public void setRuta(Long ruta) {
		this.ruta = ruta;
	}

	public Long getCasillas() {
		return casillas;
	}

	public void setCasillas(Long casillas) {
		this.casillas = casillas;
	}

	public Long getRgMeta() {
		return rgMeta;
	}

	public void setRgMeta(Long rgMeta) {
		this.rgMeta = rgMeta;
	}

	public Long getRcMeta() {
		return rcMeta;
	}

	public void setRcMeta(Long rcMeta) {
		this.rcMeta = rcMeta;
	}

	public Long getRgAsistencia() {
		return rgAsistencia;
	}

	public void setRgAsistencia(Long rgAsistencia) {
		this.rgAsistencia = rgAsistencia;
	}

	public Long getRcAsistencia() {
		return rcAsistencia;
	}

	public void setRcAsistencia(Long rcAsistencia) {
		this.rcAsistencia = rcAsistencia;
	}

	public Double getRgPorcentaje() {
		return rgPorcentaje;
	}

	public void setRgPorcentaje(Double rgPorcentaje) {
		this.rgPorcentaje = rgPorcentaje;
	}

	public Double getRcPorcentaje() {
		return rcPorcentaje;
	}

	public void setRcPorcentaje(Double rcPorcentaje) {
		this.rcPorcentaje = rcPorcentaje;
	}

}
