package mx.morena.negocio.dto.offline;

import java.util.List;

public class DistritoFederalDTOOffline {

	private String id;

	private String cabeceraFederal;

	private List<MunicipioDTOOffline> municipios;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCabeceraFederal() {
		return cabeceraFederal;
	}

	public void setCabeceraFederal(String cabeceraFederal) {
		this.cabeceraFederal = cabeceraFederal;
	}

	public List<MunicipioDTOOffline> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<MunicipioDTOOffline> municipios) {
		this.municipios = municipios;
	}

	
}
