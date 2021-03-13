package mx.morena.negocio.dto;

import java.util.Date;

public class CotDTO {
	private Long id;
	private Date fechaRegistro;
	//private Entidad estado;
	//private DistritoFederal distritoFederal;
	//private Municipio municipio;
	private String idEstado;
	private String idDistritoFederal;
	private String idMunicipio;
	private String nombre;
	private String aPaterno;
	private String aMaterno;
	private String claveElector;
	private String calle;
	private String numInterior;
	private String numExterior;
	private String colonia;
	private String cp;
	private String telCasa;
	private String telCelular;
	private String correo;
	private String curp;
	private Date fechaBaja;
	private Date fechaReactivacion;
	private String banco;
	private String clabeInterbancaria;
	private char estatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getaPaterno() {
		return aPaterno;
	}

	public void setaPaterno(String aPaterno) {
		this.aPaterno = aPaterno;
	}

	public String getaMaterno() {
		return aMaterno;
	}

	public void setaMaterno(String aMaterno) {
		this.aMaterno = aMaterno;
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

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Date getFechaReactivacion() {
		return fechaReactivacion;
	}

	public void setFechaReactivacion(Date fechaReactivacion) {
		this.fechaReactivacion = fechaReactivacion;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getClabeInterbancaria() {
		return clabeInterbancaria;
	}

	public void setClabeInterbancaria(String clabeInterbancaria) {
		this.clabeInterbancaria = clabeInterbancaria;
	}

	public char getEstatus() {
		return estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}
/*
	public Entidad getEstado() {
		return estado;
	}

	public void setEstado(Entidad estado) {
		this.estado = estado;
	}

	public DistritoFederal getDistritoFederal() {
		return distritoFederal;
	}

	public void setDistritoFederal(DistritoFederal distritoFederal) {
		this.distritoFederal = distritoFederal;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}*/

	public String getIdDistritoFederal() {
		return idDistritoFederal;
	}

	public void setIdDistritoFederal(String idDistritoFederal) {
		this.idDistritoFederal = idDistritoFederal;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	
}
