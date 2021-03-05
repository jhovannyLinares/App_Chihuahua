package mx.morena.persistencia.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "app_perfil")
public class Perfil {

	@Id
	@Column(unique = true, name = "id_perfil")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_PERFIL_SEQ")
	@SequenceGenerator(sequenceName = "T_PERFIL_SEQ", allocationSize = 1, name = "T_PERFIL_SEQ")
	private long id;

	@Column(name = "nombre")
	private String nombre;

	@OneToMany(mappedBy = "perfil", fetch = FetchType.LAZY)
	private List<Usuario> usuarios;

	@ManyToMany
	@JoinTable(name = "app_perfil_modulo", joinColumns = @JoinColumn(name = "id_perfil"), inverseJoinColumns = @JoinColumn(name = "id_modulo"))
	private List<Modulo> modulos;

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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Modulo> getModulos() {
		return modulos;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}

}
