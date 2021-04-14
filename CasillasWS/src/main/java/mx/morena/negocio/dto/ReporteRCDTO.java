package mx.morena.negocio.dto;

public class ReporteRCDTO {
	
	private Long metaRc;
	
	private Long avanceCapturadoRc;
	
	private Long avanceAsignadoRc;
	
	private Double porcentajeAvanceRc;


	public Long getMetaRc() {
		return metaRc;
	}

	public void setMetaRc(Long metaRc) {
		this.metaRc = metaRc;
	}

	public Long getAvanceCapturadoRc() {
		return avanceCapturadoRc;
	}

	public void setAvanceCapturadoRc(Long avanceCapturadoRc) {
		this.avanceCapturadoRc = avanceCapturadoRc;
	}

	public Long getAvanceAsignadoRc() {
		return avanceAsignadoRc;
	}

	public void setAvanceAsignadoRc(Long avanceAsignadoRc) {
		this.avanceAsignadoRc = avanceAsignadoRc;
	}
	
	public Double getPorcentajeAvanceRc() {
		return porcentajeAvanceRc;
	}

	public void setPorcentajeAvanceRc(Double porcentajeAvanceRc) {
		this.porcentajeAvanceRc = porcentajeAvanceRc;
	}
}
