package mx.morena.negocio.dto;

import java.util.Date;

public class RepresentanteDTO {

	private Date fechaRegistro;
	private Long idEstado;
	private Long idDistritoFederal;
	private Long idMunicipio;
	private Long idSeccionElectoral;
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
	private String propuesto;
	private String ineLado1;
	private String ineLado2;
	private String inePdf;
	private Boolean isClaveElector;
	private Boolean isCalle;
	private Integer tipo;

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	public Long getIdDistritoFederal() {
		return idDistritoFederal;
	}

	public void setIdDistritoFederal(Long idDistritoFederal) {
		this.idDistritoFederal = idDistritoFederal;
	}

	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Long getIdSeccionElectoral() {
		return idSeccionElectoral;
	}

	public void setIdSeccionElectoral(Long idSeccionElectoral) {
		this.idSeccionElectoral = idSeccionElectoral;
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

	public void setApellidoPaterno(String aPaterno) {
		this.apellidoPaterno = aPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String aMaterno) {
		this.apellidoMaterno = aMaterno;
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

	public String getPropuesto() {
		return propuesto;
	}

	public void setPropuesto(String propuesto) {
		this.propuesto = propuesto;
	}

	public String getIneLado1() {
		return ineLado1;
	}

	public void setIneLado1(String ineLado1) {
		this.ineLado1 = ineLado1;
	}

	public String getIneLado2() {
		return ineLado2;
	}

	public void setIneLado2(String ineLado2) {
		this.ineLado2 = ineLado2;
	}

	public String getInePdf() {
		return inePdf;
	}

	public void setInePdf(String inePdf) {
		this.inePdf = inePdf;
	}

	public Boolean getIsClaveElector() {
		return isClaveElector;
	}

	public void setIsClaveElector(Boolean isClaveElector) {
		this.isClaveElector = isClaveElector;
	}

	public Boolean getIsCalle() {
		return isCalle;
	}

	public void setIsCalle(Boolean isCalle) {
		this.isCalle = isCalle;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "RepresentanteDTO [fechaRegistro=" + fechaRegistro + ", idEstado=" + idEstado + ", idDistritoFederal="
				+ idDistritoFederal + ", idMunicipio=" + idMunicipio + ", idSeccionElectoral=" + idSeccionElectoral
				+ ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno
				+ ", claveElector=" + claveElector + ", calle=" + calle + ", numInterior=" + numInterior
				+ ", numExterior=" + numExterior + ", colonia=" + colonia + ", cp=" + cp + ", telCasa=" + telCasa
				+ ", telCelular=" + telCelular + ", correo=" + correo + ", propuesto=" + propuesto + ", ineLado1="
				+ ineLado1 + ", ineLado2=" + ineLado2 + ", inePdf=" + inePdf + ", isClaveElector=" + isClaveElector
				+ ", isCalle=" + isCalle + ", tipo=" + tipo + "]";
	}

}
