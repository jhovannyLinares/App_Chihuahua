package mx.morena.negocio.dto.offline;

import java.util.List;

public class DistritoLocalDTOOffline {

	private Long id;

	private String cabecera;

	private List<MunicipioDTOOffline> municipios;

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

	public List<MunicipioDTOOffline> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<MunicipioDTOOffline> municipios) {
		this.municipios = municipios;
	}

	@Override
	public String toString() {
		return "DistritoLocalDTO [id=" + id + ", cabecera=" + cabecera + ", municipios=" + municipios + "]";
	}



}