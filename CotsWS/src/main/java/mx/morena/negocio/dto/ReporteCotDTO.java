package mx.morena.negocio.dto;

public class ReporteCotDTO {
	
	private Long distritoId;
	
	private String nombreDistrito;
	
	private Long secciones;
	
	private Long cubiertas;
	
	private Double porcentajeCobertura;
	
	private Long metaCots;
	
	private Long cots;
	
	private Double porcetajeAvance;
	
	private Long total;

	public Long getDistritoId() {
		return distritoId;
	}

	public void setDistritoId(Long distritoId) {
		this.distritoId = distritoId;
	}

	public String getNombreDistrito() {
		return nombreDistrito;
	}

	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}

	public Long getSecciones() {
		return secciones;
	}

	public void setSecciones(Long secciones) {
		this.secciones = secciones;
	}

	public Long getCubiertas() {
		return cubiertas;
	}

	public void setCubiertas(Long cubiertas) {
		this.cubiertas = cubiertas;
	}

	public Double getPorcentajeCobertura() {
		return porcentajeCobertura;
	}

	public void setPorcentajeCobertura(Double porcentajeCobertura) {
		this.porcentajeCobertura = porcentajeCobertura;
	}

	public Long getMetaCots() {
		return metaCots;
	}

	public void setMetaCots(Long metaCots) {
		this.metaCots = metaCots;
	}

	public Long getCots() {
		return cots;
	}

	public void setCots(Long cots) {
		this.cots = cots;
	}

	public Double getPorcetajeAvance() {
		return porcetajeAvance;
	}

	public void setPorcetajeAvance(Double porcetajeAvance) {
		this.porcetajeAvance = porcetajeAvance;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}
