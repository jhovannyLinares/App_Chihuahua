package mx.morena.negocio.dto;

public class DistritoFederalDTO {

	private Long id;

	private String cabeceraFederal;

	private Long entidadId;

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


	public Long getEntidadId() {
		return entidadId;
	}

	public void setEntidadId(Long entidadId) {
		this.entidadId = entidadId;
	}
}
