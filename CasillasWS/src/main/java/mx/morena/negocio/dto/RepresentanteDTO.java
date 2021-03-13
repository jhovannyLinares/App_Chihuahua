package mx.morena.negocio.dto;

import java.util.Date;

public class RepresentanteDTO {

	private Long id;
	private Long idEstado;
	private Long idDistritoFederal;
	private Long idMunicipio;
	private Long idSeccionElectoral;
	private Date fechaRegistro;
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
	private String propuesto;
	private String ineLado1;
	private String ineLado2;
	private String inePdf;
	private String ruta;
	private byte tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public byte getTipo() {
		return tipo;
	}

	public void setTipo(byte tipo) {
		this.tipo = tipo;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	@Override
	public String toString() {
		return "RepresentanteDTO [id=" + id + ", idEstado=" + idEstado + ", idDistritoFederal=" + idDistritoFederal
				+ ", idMunicipio=" + idMunicipio + ", idSeccionElectoral=" + idSeccionElectoral + ", fechaRegistro="
				+ fechaRegistro + ", nombre=" + nombre + ", aPaterno=" + aPaterno + ", aMaterno=" + aMaterno
				+ ", claveElector=" + claveElector + ", calle=" + calle + ", numInterior=" + numInterior
				+ ", numExterior=" + numExterior + ", colonia=" + colonia + ", cp=" + cp + ", telCasa=" + telCasa
				+ ", telCelular=" + telCelular + ", correo=" + correo + ", propuesto=" + propuesto + ", ineLado1="
				+ ineLado1 + ", ineLado2=" + ineLado2 + ", inePdf=" + inePdf + ", ruta=" + ruta + ", tipo=" + tipo
				+ "]";
	}
	
}
