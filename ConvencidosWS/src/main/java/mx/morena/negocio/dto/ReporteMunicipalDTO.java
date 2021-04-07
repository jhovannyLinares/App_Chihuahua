package mx.morena.negocio.dto;

public class ReporteMunicipalDTO {
	
	private Long idMunicipio;
	
	private String municipio;
	
	private Long secciones;
	
	private Long urbanas;
	
	private Long noUrbanas;
	
	private Long metaCots;
	
	private Long cots;
	
	private Double porcentajeAvanceCots;
	
	private Long metaConvencidos;
	
	private Long totalConvencidos;
	
	private Double porcentajeAvanceConvencidos;

	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
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

	public Double getPorcentajeAvanceConvencidos() {
		return porcentajeAvanceConvencidos;
	}

	public void setPorcentajeAvanceConvencidos(Double porcentajeAvanceConvencidos) {
		this.porcentajeAvanceConvencidos = porcentajeAvanceConvencidos;
	}

	public Long getSecciones() {
		return secciones;
	}

	public void setSecciones(Long secciones) {
		this.secciones = secciones;
	}

	public Long getTotalConvencidos() {
		return totalConvencidos;
	}

	public void setTotalConvencidos(Long totalConvencidos) {
		this.totalConvencidos = totalConvencidos;
	}

}
