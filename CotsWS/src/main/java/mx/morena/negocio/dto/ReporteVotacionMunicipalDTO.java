package mx.morena.negocio.dto;

import java.util.List;

public class ReporteVotacionMunicipalDTO {

	private Long idFederal;
	private String municipio;
	private Long listaNominal;
	private List<PartidosDTO> partidos;
	private List<CoalicionesDTO> coaliciones;
	private Double porcentajeCandidato;
	private Long total;
	private Double porcentajeTotal;

	public Long getIdFederal() {
		return idFederal;
	}

	public void setIdFederal(Long idFederal) {
		this.idFederal = idFederal;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Long getListaNominal() {
		return listaNominal;
	}

	public void setListaNominal(Long listaNominal) {
		this.listaNominal = listaNominal;
	}

	public List<PartidosDTO> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<PartidosDTO> partidos) {
		this.partidos = partidos;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Double getPorcentajeTotal() {
		return porcentajeTotal;
	}

	public void setPorcentajeTotal(Double porcentajeTotal) {
		this.porcentajeTotal = porcentajeTotal;
	}

	public Double getPorcentajeCandidato() {
		return porcentajeCandidato;
	}

	public void setPorcentajeCandidato(Double porcentajeCandidato) {
		this.porcentajeCandidato = porcentajeCandidato;
	}

	public List<CoalicionesDTO> getCoaliciones() {
		return coaliciones;
	}

	public void setCoaliciones(List<CoalicionesDTO> coaliciones) {
		this.coaliciones = coaliciones;
	}

}
