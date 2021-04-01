package mx.morena.negocio.dto;

public class ReporteDistritalDTO {
	
	private Long idDistrito;
	
	private String distrito;
	
	private Long secciones;
	
	private Long urbanas;
	
	private Long noUrbanas;
	
	private Long metaCots;
	
	private Long cots;
	
	private Double AvanceCots;
	
	private Long metaConvencidos;
	
	private Long convencidos;
		
	private Double AvanceConvencidos;

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

	public Double getAvanceCots() {
		return AvanceCots;
	}

	public void setAvanceCots(Double avanceCots) {
		AvanceCots = avanceCots;
	}

	public Long getMetaConvencidos() {
		return metaConvencidos;
	}

	public void setMetaConvencidos(Long metaConvencidos) {
		this.metaConvencidos = metaConvencidos;
	}

	public Long getConvencidos() {
		return convencidos;
	}

	public void setConvencidos(Long convencidos) {
		this.convencidos = convencidos;
	}

	public Double getAvanceConvencidos() {
		return AvanceConvencidos;
	}

	public void setAvanceConvencidos(Double avanceConvencidos) {
		AvanceConvencidos = avanceConvencidos;
	}

}
