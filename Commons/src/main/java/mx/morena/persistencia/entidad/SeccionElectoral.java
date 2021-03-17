package mx.morena.persistencia.entidad;

//@Entity
//@Table(name = "app_seccionElectoral")
public class SeccionElectoral {

	//@Id
	//@Column(unique = true, name = "id")
	private Long id;

	//@Column(name = "descripcion")
	private String descripcion;

	//@ManyToOne
	//private Localidad localidad;
	private Long localidad;

	//@ManyToOne
	//private Convencidos cot;
	private Long cot;

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

	public Long getCot() {
		return cot;
	}

	public void setCot(Long cot) {
		this.cot = cot;
	}

}
