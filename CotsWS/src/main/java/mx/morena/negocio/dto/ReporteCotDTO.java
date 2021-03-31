package mx.morena.negocio.dto;

public class ReporteCotDTO {
	
	private Long idDistritoFederal;
	
	private String distritoFederal;
	
	private String secciones;
	
	private Long cubiertas;
	
	private Long porcentajeCobertura;
	
	private Long metaCots;
	
	private String cots;
	
	private Long porcetajeAvance;

	public Long getIdDistritoFederal() {
		return idDistritoFederal;
	}

	public void setIdDistritoFederal(Long idDistritoFederal) {
		this.idDistritoFederal = idDistritoFederal;
	}

	public String getDistritoFederal() {
		return distritoFederal;
	}

	public void setDistritoFederal(String distritoFederal) {
		this.distritoFederal = distritoFederal;
	}

	public String getSecciones() {
		return secciones;
	}

	public void setSecciones(String secciones) {
		this.secciones = secciones;
	}

	public Long getCubiertas() {
		return cubiertas;
	}

	public void setCubiertas(Long cubiertas) {
		this.cubiertas = cubiertas;
	}

	public Long getPorcentajeCobertura() {
		return porcentajeCobertura;
	}

	public void setPorcentajeCobertura(Long porcentajeCobertura) {
		this.porcentajeCobertura = porcentajeCobertura;
	}

	public Long getMetaCots() {
		return metaCots;
	}

	public void setMetaCots(Long metaCots) {
		this.metaCots = metaCots;
	}

	public String getCots() {
		return cots;
	}

	public void setCots(String cots) {
		this.cots = cots;
	}

	public Long getPorcetajeAvance() {
		return porcetajeAvance;
	}

	public void setPorcetajeAvance(Long porcetajeAvance) {
		this.porcetajeAvance = porcetajeAvance;
	}
}
