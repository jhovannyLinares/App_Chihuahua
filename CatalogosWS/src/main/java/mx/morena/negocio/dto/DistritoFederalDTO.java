package mx.morena.negocio.dto;

public class DistritoFederalDTO {

	private Long id;

	private String cabeceraFederal;
	
	private long entidadId;

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

	public void setEntidadId(long entidadId) {
		this.entidadId = entidadId;
	}

	public long getEntidadId() {
		return entidadId;
	}

	@Override
	public String toString() {
		return "DistritoFederalDTO [id=" + id + ", cabeceraFederal=" + cabeceraFederal + ", entidadId=" + entidadId
				+ "]";
	}
}
