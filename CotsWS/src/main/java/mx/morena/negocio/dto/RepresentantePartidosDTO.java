package mx.morena.negocio.dto;

public class RepresentantePartidosDTO {
	
	private Long idPartido;
	
	private boolean tieneRepresentante;

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
