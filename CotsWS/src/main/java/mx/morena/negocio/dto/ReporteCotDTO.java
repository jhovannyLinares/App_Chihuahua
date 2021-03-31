package mx.morena.negocio.dto;

public class ReporteCotDTO {
	
	private Long distritoId;
	
	private String nombreDistrito;
	
	private Long secciones;
	
	private Long cubiertas;
	
	private Long porcentajeCobertura;
	
	private Long metaCots;
	
	private String cots;
	
	private Long porcetajeAvance;

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

	public Long getPorcentajeCobertura() {
		return porcentajeCobertura;
	}

	public void setPorcentajeCobertura(Long porcentajeCobertura) {
		this.porcentajeCobertura = porcentajeCobertura;
	}

	public Long getMetaCots() {
		return metaCots;
	}

	public void setMetaCots(Long metaCots) {
		this.metaCots = metaCots;
	}

	public String getCots() {
		return cots;
	}

	public void setCots(String cots) {
		this.cots = cots;
	}

	public Long getPorcetajeAvance() {
		return porcetajeAvance;
	}

	public void setPorcetajeAvance(Long porcetajeAvance) {
		this.porcetajeAvance = porcetajeAvance;
	}
}
