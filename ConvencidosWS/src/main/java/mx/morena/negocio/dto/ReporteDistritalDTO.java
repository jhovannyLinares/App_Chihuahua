package mx.morena.negocio.dto;

public class ReporteDistritalDTO {
	
	private Long distritoId;
	
	private String nombreDistrito;
	
	private Long secciones;
	
	private Long urbanas;
	
	private Long noUrbanas;
	
	private Long metaCots;
	
	private Long cots;
	
	private Double AvanceCots;
	
	private Long metaConvencidos;
	
	private Long convencidos;
		
	private Double AvanceConvencidos;

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
