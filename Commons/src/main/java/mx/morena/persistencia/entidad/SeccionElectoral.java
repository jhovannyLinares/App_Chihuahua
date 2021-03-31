package mx.morena.persistencia.entidad;

public class SeccionElectoral {

	private Long id;

	private String descripcion;

	private Long localidad;

	private Long municipioId;
	
	private String municipio;

	private Long cot;
	
	private Long distritoId;
	
	private String nombreDistrito;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Long localidad) {
		this.localidad = localidad;
	}

	public Long getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(Long municipioId) {
		this.municipioId = municipioId;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Long getCot() {
		return cot;
	}

	public void setCot(Long cot) {
		this.cot = cot;
	}

	public Long getDistritoId() {
		return distritoId;
	}

	public void setDistritoId(Long distritoId) {
		this.distritoId = distritoId;
	}

	public String getNombreDistrito() {
		return nombreDistrito;
	}

	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}

	

}
