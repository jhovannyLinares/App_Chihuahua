package mx.morena.persistencia.entidad;

public class Usuario {

	private long id;

	private String nombre;

	private String usuario;

	private String aPaterno;

	private String aMaterno;

	private String password;

	private String email;

	private Long entidad;

	private Long federal;

	private Long municipio;

	private Long localidad;

	private Long seccionElectoral;

	private Long perfil;
	
	private Long representante;

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

	public Long getEntidad() {
		return entidad;
	}

	public void setEntidad(Long entidad) {
		this.entidad = entidad;
	}

	public Long getFederal() {
		return federal;
	}

	public void setFederal(Long federal) {
		this.federal = federal;
	}

	public Long getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Long municipio) {
		this.municipio = municipio;
	}

	public Long getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Long localidad) {
		this.localidad = localidad;
	}

	public Long getSeccionElectoral() {
		return seccionElectoral;
	}

	public void setSeccionElectoral(Long seccionElectoral) {
		this.seccionElectoral = seccionElectoral;
	}

	public Long getPerfil() {
		return perfil;
	}

	public void setPerfil(Long perfil) {
		this.perfil = perfil;
	}

	public Long getRepresentante() {
		return representante;
	}

	public void setRepresentante(Long representante) {
		this.representante = representante;
	}
	
}
