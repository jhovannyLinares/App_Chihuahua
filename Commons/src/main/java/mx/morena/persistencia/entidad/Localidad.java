package mx.morena.persistencia.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "app_localidad")
public class Localidad {

	public Localidad() {
	}

	@Id
	@Column(unique = true, name = "id_localidad")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_localidad_SEQ")
	@SequenceGenerator(sequenceName = "T_localidad_SEQ", allocationSize = 1, name = "T_localidad_SEQ")
	private long id;

	@Column(name = "descripcion_localidad")
	private String descripcion;

	/** Relacion con Municipios */
	@ManyToOne
	@JoinColumn(name = "municipio_id")
	private Municipio municipio;
	
	@OneToMany(mappedBy = "localidad", fetch = FetchType.LAZY)
	private List<SeccionElectoral> seccionElectorales;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public List<SeccionElectoral> getSeccionElectorales() {
		return seccionElectorales;
	}

	public void setSeccionElectorales(List<SeccionElectoral> seccionElectorales) {
		this.seccionElectorales = seccionElectorales;
	}

}
