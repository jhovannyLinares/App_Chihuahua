package mx.morena.persistencia.entidad;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "app_distrito_federal")
public class DistritoFederal {

	@Id
	@Column(unique = true, name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_federal_SEQ")
	@SequenceGenerator(sequenceName = "T_federal_SEQ", allocationSize = 1, name = "T_federal_SEQ")
	private Long id;

	@Column(name = "cabecera_federal")
	private String cabeceraFederal;

	/* Relación con entidad Usuario */

	@ManyToOne
	@JoinColumn(name = "entidad_id")
	private Entidad entidad;

	@OneToMany(mappedBy = "distritoFederal",fetch = FetchType.LAZY)
	private List<DistritoLocal> distritosLocales;

	public DistritoFederal() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCabeceraFederal() {
		return cabeceraFederal;
	}

	public void setCabeceraFederal(String cabeceraFederal) {
		this.cabeceraFederal = cabeceraFederal;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

	public List<DistritoLocal> getDistritosLocales() {
		return distritosLocales;
	}

	public void setDistritosLocales(List<DistritoLocal> distritosLocales) {
		this.distritosLocales = distritosLocales;
	}

	@Override
	public String toString() {
		return "DistritoFederal [id=" + id + ", cabeceraFederal=" + cabeceraFederal + ", entidad=" + entidad
				+ ", distritosLocales=" + distritosLocales + "]";
	}
	
	

}
