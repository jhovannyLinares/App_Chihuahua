package mx.morena.persistencia.entidad;

import java.util.List;

public class Modulo {

	private long id;

	private String descripcion;

	private String url;
	
	private List<Perfil> perfiles;

	private Long moduloPadre;
	
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

	public Long getModuloPadre() {
		return moduloPadre;
	}

	public void setModuloPadre(Long moduloPadre) {
		this.moduloPadre = moduloPadre;
	}

}
