package mx.morena.negocio.dto.offline;

import java.util.List;

public class DistritoFederalDTOOffline {

	private Long id;

	private String cabeceraFederal;

	private List<DistritoLocalDTOOffline> distritosLocales;

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

	public List<DistritoLocalDTOOffline> getDistritosLocales() {
		return distritosLocales;
	}

	public void setDistritosLocales(List<DistritoLocalDTOOffline> distritosLocales) {
		this.distritosLocales = distritosLocales;
	}

	@Override
	public String toString() {
		return "DistritoFederalDTO [id=" + id + ", cabeceraFederal=" + cabeceraFederal + ", distritosLocales="
				+ distritosLocales + "]";
	}

}
