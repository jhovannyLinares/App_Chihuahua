package mx.morena.negocio.dto;

public class PartidosDTO {

	private Long id;
	private String partido;
	private Long votos;
	private String candidato;
	private Double porcentajePartido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartido() {
		return partido;
	}

	public void setPartido(String partido) {
		this.partido = partido;
	}

	public String getCandidato() {
		return candidato;
	}

	public void setCandidato(String candidato) {
		this.candidato = candidato;
	}

	public Long getVotos() {
		return votos;
	}

	public void setVotos(Long votos) {
		this.votos = votos;
	}

	public Double getPorcentajePartido() {
		return porcentajePartido;
	}

	public void setPorcentajePartido(Double porcentajePartido) {
		this.porcentajePartido = porcentajePartido;
	}
}
