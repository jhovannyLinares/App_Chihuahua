package mx.morena.persistencia.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "app_modulo")
public class Modulo {

	@Id
	@Column(unique = true, name = "id_modulo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_MODULO_SEQ")
	@SequenceGenerator(sequenceName = "T_MODULO_SEQ", allocationSize = 1, name = "v")
	private long id;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "url")
	private String url;
	
	@ManyToMany(mappedBy = "modulos")
	private List<Perfil> perfiles;

	@ManyToOne
	private Modulo moduloPadre;
	
	@OneToMany(mappedBy = "moduloPadre", fetch = FetchType.LAZY)
	private List<Modulo> subModulo;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Modulo getModuloPadre() {
		return moduloPadre;
	}

	public void setModuloPadre(Modulo moduloPadre) {
		this.moduloPadre = moduloPadre;
	}

}
