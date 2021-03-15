package mx.morena.persistencia.entidad;

import java.util.List;

//@Entity
//@Table(name = "app_entidad")
public class Entidad {

	//@Id
	//@Column(unique = true, name = "id")
	private Long id;

	//@Column( name = "nombre")
	private String nombre;

	//@OneToMany(mappedBy = "entidad", fetch = FetchType.LAZY)
	private List<DistritoFederal> distritosFederales;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DistritoFederal> getDistritosFederales() {
		return distritosFederales;
	}

	public void setDistritosFederales(List<DistritoFederal> distritosFederales) {
		this.distritosFederales = distritosFederales;
	}
	

}
