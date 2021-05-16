package mx.morena.persistencia.entidad;

public class RepresentacionPartidosReporte {

	private Long id;
	private String partido;
	private boolean tieneRepresentante;

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

	public boolean isTieneRepresentante() {
		return tieneRepresentante;
	}

	public void setTieneRepresentante(boolean tieneRepresentante) {
		this.tieneRepresentante = tieneRepresentante;
	}

}
