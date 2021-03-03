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
@Table(name = "c_municipio")
public class Municipio {
	@Id
	@Column(unique = true, name = "id_municipio")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_municipio_SEQ")
	@SequenceGenerator(sequenceName = "T_municipio_SEQ", allocationSize = 1, name = "T_municipio_SEQ")
	private long id;

	@Column(name = "descripcion_municipio")
	private String descripcion;

	@ManyToOne
	@JoinColumn(name = "id_distrito_local")
	private DistritoLocal distritoLocal;

	@OneToMany(mappedBy = "municipio", fetch = FetchType.LAZY)
	private List<Localidad> localidades;

	public Municipio() {
	}

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

	public DistritoLocal getDistritoLocal() {
		return distritoLocal;
	}

	public void setDistritoLocal(DistritoLocal distritoLocal) {
		this.distritoLocal = distritoLocal;
	}

	public List<Localidad> getLocalidades() {
		return localidades;
	}

	public void setLocalidades(List<Localidad> localidades) {
		this.localidades = localidades;
	}

}
