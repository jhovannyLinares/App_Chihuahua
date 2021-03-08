package mx.morena.security.dto;

import java.util.List;

public class UsuarioDTO {

	private Long id;
	private String usuario;
	private String perfil;
	private List<ModuloDTO> modulos;
	private String token;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<ModuloDTO> getModulos() {
		return modulos;
	}

	public void setModulos(List<ModuloDTO> modulos) {
		this.modulos = modulos;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

}