package mx.morena.persistencia.entidad;

public class Votacion {

	private Long idPartido;
	private Integer votos;
	private Long idCasilla;
	private Integer tipoVotacion;

	private boolean isCoalicion;
	private Integer idCoalicion;

	public Long getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(Long idPartido) {
		this.idPartido = idPartido;
	}

	public Integer getVotos() {
		return votos;
	}

	public void setVotos(Integer votos) {
		this.votos = votos;
	}

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Integer getTipoVotacion() {
		return tipoVotacion;
	}

	public void setTipoVotacion(Integer tipoVotacion) {
		this.tipoVotacion = tipoVotacion;
	}

	public boolean isCoalicion() {
		return isCoalicion;
	}

	public void setCoalicion(boolean isCoalicion) {
		this.isCoalicion = isCoalicion;
	}

	public Integer getIdCoalicion() {
		return idCoalicion;
	}

	public void setIdCoalicion(Integer idCoalicion) {
		this.idCoalicion = idCoalicion;
	}

}
