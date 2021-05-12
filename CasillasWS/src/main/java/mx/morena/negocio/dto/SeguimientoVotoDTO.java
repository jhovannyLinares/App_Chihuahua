package mx.morena.negocio.dto;

public class SeguimientoVotoDTO {
	
	private String nombreCompleto;
	private String domicilio;
	private String ubicacionCasilla;
	private String referenciaCasilla;
	private Boolean isNotificado;
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	 
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getUbicacionCasilla() {
		return ubicacionCasilla;
	}
	public void setUbicacionCasilla(String ubicacionCasilla) {
		this.ubicacionCasilla = ubicacionCasilla;
	}
	public String getReferenciaCasilla() {
		return referenciaCasilla;
	}
	public void setReferenciaCasilla(String referenciaCasilla) {
		this.referenciaCasilla = referenciaCasilla;
	}
	public Boolean getIsNotificado() {
		return isNotificado;
	}
	public void setIsNotificado(Boolean isNotificado) {
		this.isNotificado = isNotificado;
	}
	
}
