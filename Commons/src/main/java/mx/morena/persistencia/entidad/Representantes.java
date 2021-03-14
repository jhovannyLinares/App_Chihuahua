package mx.morena.persistencia.entidad;

import java.util.Date;

//@Entity
//@Table(name = "app_representantes")
public class Representantes {

	private Long id;

	private Entidad estado;

	private DistritoFederal distritoFederal;

	private Municipio municipio;

	private SeccionElectoral seccionElectoral;

	private Usuario usuario;

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
	}

	public SeccionElectoral getSeccionElectoral() {
		return seccionElectoral;
	}

	public void setSeccionElectoral(SeccionElectoral seccionElectoral) {
		this.seccionElectoral = seccionElectoral;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

}
