package mx.morena.persistencia.entidad;

public class Partido {

	private Long id;

	private String partido;

	private String tipoPartido;

	private String candidato;

	private Long ubicacion;

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

	public String getTipoPartido() {
		return tipoPartido;
	}

	public void setTipoPartido(String tipoPartido) {
		this.tipoPartido = tipoPartido;
	}

	public String getCandidato() {
		return candidato;
	}

	public void setCandidato(String candidato) {
		this.candidato = candidato;
	}

	public Long getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Long ubicacion) {
		this.ubicacion = ubicacion;
	}

}
