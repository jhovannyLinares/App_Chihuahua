package mx.morena.negocio.dto;

public class ReporteAsistenciaLocalDTO {

	private Long idLocal;

	private Long rgMeta;

	private Long rcMeta;

	private Long rgAsistencia;

	private Long rcAsistencia;

	private Double rgPorcentaje;

	private Double rcPorcentaje;

	public Long getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Long idLocal) {
		this.idLocal = idLocal;
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
