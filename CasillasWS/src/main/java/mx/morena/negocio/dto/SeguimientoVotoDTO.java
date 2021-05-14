package mx.morena.negocio.dto;

public class SeguimientoVotoDTO {

	private Long id;
	private String nombreCompleto;
	private String domicilio;
	private String telefono;
	private String ubicacionCasilla;
	private String referenciaCasilla;
	private String tipoCasilla;
	private Boolean isNotificado;

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipoCasilla() {
		return tipoCasilla;
	}

	public void setTipoCasilla(String tipoCasilla) {
		this.tipoCasilla = tipoCasilla;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
