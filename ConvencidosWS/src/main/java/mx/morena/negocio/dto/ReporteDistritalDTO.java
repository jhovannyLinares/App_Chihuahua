package mx.morena.negocio.dto;

public class ReporteDistritalDTO {
	
	private Long idDistrito;
	
	private String distrito;
	
	private Long secciones;
	
	private Long urbanas;
	
	private Long noUrbanas;
	
	private Long metaCots;
	
	private Long cots;
	
	private Double porcentajeAvanceCots;
	
	private Long metaConvencidos;
	
	private Long totalConvencidos;
		
	private Double porcentajeAvanceConvencidos;

	public Long getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(Long idDistrito) {
		this.idDistrito = idDistrito;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public Long getSecciones() {
		return secciones;
	}

	public void setSecciones(Long secciones) {
		this.secciones = secciones;
	}

	public Long getUrbanas() {
		return urbanas;
	}

	public void setUrbanas(Long urbanas) {
		this.urbanas = urbanas;
	}

	public Long getNoUrbanas() {
		return noUrbanas;
	}

	public void setNoUrbanas(Long noUrbanas) {
		this.noUrbanas = noUrbanas;
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

	public Double getPorcentajeAvanceCots() {
		return porcentajeAvanceCots;
	}

	public void setPorcentajeAvanceCots(Double porcentajeAvanceCots) {
		this.porcentajeAvanceCots = porcentajeAvanceCots;
	}

	public Long getMetaConvencidos() {
		return metaConvencidos;
	}

	public void setMetaConvencidos(Long metaConvencidos) {
		this.metaConvencidos = metaConvencidos;
	}

	public Long getTotalConvencidos() {
		return totalConvencidos;
	}

	public void setTotalConvencidos(Long totalConvencidos) {
		this.totalConvencidos = totalConvencidos;
	}

	public Double getPorcentajeAvanceConvencidos() {
		return porcentajeAvanceConvencidos;
	}

	public void setPorcentajeAvanceConvencidos(Double porcentajeAvanceConvencidos) {
		this.porcentajeAvanceConvencidos = porcentajeAvanceConvencidos;
	}
	
}
