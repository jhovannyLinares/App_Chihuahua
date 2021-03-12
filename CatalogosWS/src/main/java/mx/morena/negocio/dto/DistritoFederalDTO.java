package mx.morena.negocio.dto;

import java.util.List;

public class DistritoFederalDTO {

	private Long id;

	private String cabeceraFederal;

	private List<DistritoLocalDTO> distritosLocales;

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

	public List<DistritoLocalDTO> getDistritosLocales() {
		return distritosLocales;
	}

	public void setDistritosLocales(List<DistritoLocalDTO> distritosLocales) {
		this.distritosLocales = distritosLocales;
	}

	@Override
	public String toString() {
		return "DistritoFederalDTO [id=" + id + ", cabeceraFederal=" + cabeceraFederal + ", distritosLocales="
				+ distritosLocales + "]";
	}

}
