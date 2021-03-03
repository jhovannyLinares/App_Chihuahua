package mx.morena.security.dto;

import java.util.List;

public class ModuloDTO {

	private long moduloId;
	private String descripcion;
	private String url;
	private long moduloPadre;
	private List<ModuloDTO> subModulos;

	public long getModuloId() {
		return moduloId;
	}

	public void setModuloId(long moduloId) {
		this.moduloId = moduloId;
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

	public long getModuloPadre() {
		return moduloPadre;
	}

	public void setModuloPadre(long moduloPadre) {
		this.moduloPadre = moduloPadre;
	}

	public List<ModuloDTO> getSubModulos() {
		return subModulos;
	}

	public void setSubModulos(List<ModuloDTO> subModulos) {
		this.subModulos = subModulos;
	}

}