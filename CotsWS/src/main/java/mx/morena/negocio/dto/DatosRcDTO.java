package mx.morena.negocio.dto;

public class DatosRcDTO {
	
	private Long idUsuario;
	
	private Long entidad;
	
	private Long distFederal;
	
	private Long distLocal;
	
	private Long seccion;
	
	private Long casilla;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getEntidad() {
		return entidad;
	}

	public void setEntidad(Long entidad) {
		this.entidad = entidad;
	}

	public Long getDistFederal() {
		return distFederal;
	}

	public void setDistFederal(Long distFederal) {
		this.distFederal = distFederal;
	}

	public Long getDistLocal() {
		return distLocal;
	}

	public void setDistLocal(Long distLocal) {
		this.distLocal = distLocal;
	}

	public Long getSeccion() {
		return seccion;
	}

	public void setSeccion(Long seccion) {
		this.seccion = seccion;
	}

	public Long getCasilla() {
		return casilla;
	}

	public void setCasilla(Long casilla) {
		this.casilla = casilla;
	}

}
