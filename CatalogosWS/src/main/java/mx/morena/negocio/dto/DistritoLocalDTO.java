package mx.morena.negocio.dto;

import java.util.List;

public class DistritoLocalDTO {

	private Long id;

	private String cabecera;

	private List<MunicipioDTO> municipios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCabecera() {
		return cabecera;
	}

	public void setCabecera(String cabecera) {
		this.cabecera = cabecera;
	}

	public List<MunicipioDTO> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<MunicipioDTO> municipios) {
		this.municipios = municipios;
	}

	@Override
	public String toString() {
		return "DistritoLocalDTO [id=" + id + ", cabecera=" + cabecera + ", municipios=" + municipios + "]";
	}



}