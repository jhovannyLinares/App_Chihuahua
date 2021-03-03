package mx.morena.persistencia.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "app_seccionElectoral")
public class SeccionElectoral {

	@Id
	@Column(unique = true, name = "id_seccion")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_SECCION_SEQ")
	@SequenceGenerator(sequenceName = "T_SECCION_SEQ", allocationSize = 1, name = "T_SECCION_SEQ")
	private long id;

	@Column(name = "descripcion_seccion")
	private String descripcion;

	@ManyToOne
	@JoinColumn(name = "id_localidad")
	private Localidad localidad;

	public SeccionElectoral() {
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

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}
	
	

}
