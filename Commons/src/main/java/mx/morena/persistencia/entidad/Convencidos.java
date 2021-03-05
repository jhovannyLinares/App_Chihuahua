package mx.morena.persistencia.entidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "app_convencidos")
public class Convencidos {

	@Id
	@Column(unique = true, name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "convencidos_SEQ")
	@SequenceGenerator(sequenceName = "convencidos_SEQ", allocationSize = 1, name = "convencidos_SEQ	")
	private Long id;

	@Column(name = "fecha_registro")
	private Date fechaRegistro;

	@Column(name = "id_estado")
	private Long idEstado;

	@OneToOne
	@JoinColumn(name = "id_distrito_federal")
	@Column(name = "id_federal")
	private Long idFederal;

	@OneToOne
	@JoinColumn(name = "id_municipio")
	@Column(name = "id_municipio")
	private Long idMunicipio;

	@OneToOne
	@JoinColumn(name = "id_seccion")
	@Column(name = "id_seccion")
	private Long idSeccion;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido_paterno")
	private String aPaterno;

	@Column(name = "apellido_materno")
	private String aMaterno;

	@Column(name = "clave_elector")
	private String claveElector;

	@Column(name = "calle")
	private String calle;

	@Column(name = "numero_interior")
	private String numInterior;

	@Column(name = "numero_exterior")
	private String numExterior;

	@Column(name = "colonia")
	private String colonia;

	@Column(name = "codigo_postal")
	private String cp;

	@Column(name = "telefono_casa")
	private String telCasa;

	@Column(name = "telefono_celular")
	private String telCelular;
	
	@Column(name = "correo")
	private String correo;
	
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

	public Long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	public Long getIdFederal() {
		return idFederal;
	}

	public void setIdFederal(Long idFederal) {
		this.idFederal = idFederal;
	}

	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Long getIdSeccion() {
		return idSeccion;
	}

	public void setIdSeccion(Long idSeccion) {
		this.idSeccion = idSeccion;
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
	
	

}
//