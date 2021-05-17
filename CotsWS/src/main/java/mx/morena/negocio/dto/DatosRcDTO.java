package mx.morena.negocio.dto;

import java.util.List;

public class DatosRcDTO {
	
	////////////////////////    get representantes
	
	private List<RepresentanteDTO> representante;
	
	///////////////////////    get informacion rc
	
	private String entidad;
	
	private String distritoFederal;
	
	private String distritoLocal;
	
	private Long seccion;
	
	private Long idCasilla;
	
	private String casilla;
	
	private String TipoCasilla;
	
	/////////////////////     get ubicasion casilla
	
	private String calle;
	
	private String numero;
	
	private String colonia;
	
	private String localidad;
	
	private String ubicacion;

	public List<RepresentanteDTO> getRepresentante() {
		return representante;
	}

	public void setRepresentante(List<RepresentanteDTO> representante) {
		this.representante = representante;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getDistritoFederal() {
		return distritoFederal;
	}

	public void setDistritoFederal(String distritoFederal) {
		this.distritoFederal = distritoFederal;
	}

	public String getDistritoLocal() {
		return distritoLocal;
	}

	public void setDistritoLocal(String distritoLocal) {
		this.distritoLocal = distritoLocal;
	}

	public Long getSeccion() {
		return seccion;
	}

	public void setSeccion(Long seccion) {
		this.seccion = seccion;
	}

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public String getCasilla() {
		return casilla;
	}

	public void setCasilla(String casilla) {
		this.casilla = casilla;
	}

	public String getTipoCasilla() {
		return TipoCasilla;
	}

	public void setTipoCasilla(String tipoCasilla) {
		TipoCasilla = tipoCasilla;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

}
