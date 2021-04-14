package mx.morena.negocio.dto;

public class ReporteRgDTO {
	
	private Long metaRg;
	
	private Long avanceCapturadoRg;
	
	private Long avanceAsignadoRg;
	
	private Double porcentajeAvanceRg;

	public Long getMetaRg() {
		return metaRg;
	}

	public void setMetaRg(Long metaRg) {
		this.metaRg = metaRg;
	}

	public Long getAvanceCapturadoRg() {
		return avanceCapturadoRg;
	}

	public void setAvanceCapturadoRg(Long avanceCapturadoRg) {
		this.avanceCapturadoRg = avanceCapturadoRg;
	}

	public Long getAvanceAsignadoRg() {
		return avanceAsignadoRg;
	}

	public void setAvanceAsignadoRg(Long avanceAsignadoRg) {
		this.avanceAsignadoRg = avanceAsignadoRg;
	}

	public Double getPorcentajeAvanceRg() {
		return porcentajeAvanceRg;
	}

	public void setPorcentajeAvanceRg(Double porcentajeAvanceRg) {
		this.porcentajeAvanceRg = porcentajeAvanceRg;
	}

}
