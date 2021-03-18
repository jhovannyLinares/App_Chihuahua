package mx.morena.negocio.dto;

import java.util.Date;

public class ConvencidosResponseDTO {
	
	private Date fechaRegistro;

	private Long estado;

	private Long distritoFederal;

	private Long municipio;

	private Long seccionElectoral;

	private String nombre;

	private String apellidoPaterno;

	private String apellidoMaterno;

	private String claveElector;

	private String calle;

	private String numInterior;

	private String numExterior;

	private String colonia;

	private String cp;

	private String telCasa;

	private String telCelular;

	private String correo;
	
	private boolean dv;
	
	private boolean mov;

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Long getDistritoFederal() {
		return distritoFederal;
	}

	public void setDistritoFederal(Long distritoFederal) {
		this.distritoFederal = distritoFederal;
	}

	public Long getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Long municipio) {
		this.municipio = municipio;
	}

	public Long getSeccionElectoral() {
		return seccionElectoral;
	}

	public void setSeccionElectoral(Long seccionElectoral) {
		this.seccionElectoral = seccionElectoral;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getClaveElector() {
		return claveElector;
	}

	public void setClaveElector(String claveElector) {
		this.claveElector = claveElector;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumInterior() {
		return numInterior;
	}

	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}

	public String getNumExterior() {
		return numExterior;
	}

	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getTelCasa() {
		return telCasa;
	}

	public void setTelCasa(String telCasa) {
		this.telCasa = telCasa;
	}

	public String getTelCelular() {
		return telCelular;
	}

	public void setTelCelular(String telCelular) {
		this.telCelular = telCelular;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public boolean isDv() {
		return dv;
	}

	public void setDv(boolean dv) {
		this.dv = dv;
	}

	public boolean isMov() {
		return mov;
	}

	public void setMov(boolean mov) {
		this.mov = mov;
	}


	
	

}
