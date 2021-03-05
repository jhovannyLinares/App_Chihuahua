package mx.morena.persistencia.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "app_usuario")
public class Usuario {

	@Id
	@Column(unique = true, name = "id_usuario")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_USUARIO_SEQ")
	@SequenceGenerator(sequenceName = "T_USUARIO_SEQ", allocationSize = 1, name = "T_USUARIO_SEQ")
	private long id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "usuario")
	private String usuario;

	@Column(name = "aPaterno")
	private String aPaterno;

	@Column(name = "aMaterno")
	private String aMaterno;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@OneToOne
	private Entidad entidad;

	@OneToOne
	private DistritoFederal federal;

	@OneToOne
	private DistritoLocal local;

	@OneToOne
	private Municipio municipio;

	@OneToOne
	private Localidad localidad;

	@OneToOne
	private SeccionElectoral seccionElectoral;

	@ManyToOne
	private Perfil perfil;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

	public DistritoFederal getFederal() {
		return federal;
	}

	public void setFederal(DistritoFederal federal) {
		this.federal = federal;
	}

	public DistritoLocal getLocal() {
		return local;
	}

	public void setLocal(DistritoLocal local) {
		this.local = local;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public SeccionElectoral getSeccionElectoral() {
		return seccionElectoral;
	}

	public void setSeccionElectoral(SeccionElectoral seccionElectoral) {
		this.seccionElectoral = seccionElectoral;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}
