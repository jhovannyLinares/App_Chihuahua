package mx.morena.negocio.dto;

import java.util.List;

public class ResultadoVotacionDTO { 
	
	private Long idCasilla;
	private Integer tipoVotacion;
	List<VotosPartidoDTO> votos;
	

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

	public List<VotosPartidoDTO> getVotos() {
		return votos;
	}

	public void setVotos(List<VotosPartidoDTO> votos) {
		this.votos = votos;
	}

}
