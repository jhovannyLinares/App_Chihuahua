package mx.morena.persistencia.entidad;

//@Entity
//@Table(name = "app_seccionElectoral")
public class SeccionElectoral {

	//@Id
	//@Column(unique = true, name = "id")
	private String id;

	//@Column(name = "descripcion")
	private String descripcion;

	//@ManyToOne
	private Localidad localidad;

	//@ManyToOne
	private Convencidos cot;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public Convencidos getCot() {
		return cot;
	}

	public void setCot(Convencidos cot) {
		this.cot = cot;
	}

}
