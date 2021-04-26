package mx.morena.negocio.dto;

public class ReporteSeguimintoVotoDTO {
	
	private Long idDistrito;
	
	private String distrito;
	
	private Long secciones;
	
	private Long urbanas;
	
	private Long noUrbanas;

	private Long metaConvencidos;
	
	private Long totalConvencidos;
		
	private Double porcentajeAvanceConvencidos;
	
	private Long notificado;
	
	private Double porcentajeAvanceNotificado;

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

	public Long getNotificado() {
		return notificado;
	}

	public void setNotificado(Long notificado) {
		this.notificado = notificado;
	}

	public Double getPorcentajeAvanceNotificado() {
		return porcentajeAvanceNotificado;
	}

	public void setPorcentajeAvanceNotificado(Double porcentajeAvanceNotificado) {
		this.porcentajeAvanceNotificado = porcentajeAvanceNotificado;
	}

}
