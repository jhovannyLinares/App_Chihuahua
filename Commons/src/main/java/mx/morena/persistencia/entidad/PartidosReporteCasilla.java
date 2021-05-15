package mx.morena.persistencia.entidad;

public class PartidosReporteCasilla {

	private Long id;
	private Long idCasilla;
	private Long idPartido;
	private boolean tieneRepresentante;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Long getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(Long idPartido) {
		this.idPartido = idPartido;
	}

	public boolean isTieneRepresentante() {
		return tieneRepresentante;
	}

	public void setTieneRepresentante(boolean tieneRepresentante) {
		this.tieneRepresentante = tieneRepresentante;
	}

}
