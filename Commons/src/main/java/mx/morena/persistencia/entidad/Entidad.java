package mx.morena.persistencia.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "c_entidad")
public class Entidad {

	@Id
	@Column(unique = true, name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_ENTIDAD_SEQ")
	@SequenceGenerator(sequenceName = "T_ENTIDAD_SEQ", allocationSize = 1, name = "T_ENTIDAD_SEQ")
	private long id;

	@Column( name = "nombre")
	private String nombre;

	@OneToMany(mappedBy = "entidad", fetch = FetchType.LAZY)
	private List<DistritoFederal> distritosFederales;

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

	public List<DistritoFederal> getDistritosFederales() {
		return distritosFederales;
	}

	public void setDistritosFederales(List<DistritoFederal> distritosFederales) {
		this.distritosFederales = distritosFederales;
	}

}
