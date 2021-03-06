package mx.morena.negocio.servicios.impl;

import java.util.List;

import mx.morena.negocio.dto.VotacionDTO;

public class PreguntasCasillaDTO {

	private Long idCasilla;
	private Integer tipoVotacion;
	private Boolean isCapturado = false;
	private Integer boletasSobrantes;
	private Integer numeroPersonasVotaron;
	private Integer numeroRepresentantesVotaron;
	private Integer sumaVotantes;
	private Integer votosSacadosUrna;
	private Integer votosXPartidoYCoalicion;
	private Boolean esIgualVotosPersonaXVotosUrna;
	private Boolean esIgualVotosUrnaXTotalVotacion;

	List<VotacionDTO> votacion;

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

	public Boolean getIsCapturado() {
		return isCapturado;
	}

	public void setIsCapturado(Boolean isCapturado) {
		this.isCapturado = isCapturado;
	}

	public Integer getBoletasSobrantes() {
		return boletasSobrantes;
	}

	public void setBoletasSobrantes(Integer boletasSobrantes) {
		this.boletasSobrantes = boletasSobrantes;
	}

	public Integer getNumeroPersonasVotaron() {
		return numeroPersonasVotaron;
	}

	public void setNumeroPersonasVotaron(Integer numeroPersonasVotaron) {
		this.numeroPersonasVotaron = numeroPersonasVotaron;
	}

	public Integer getNumeroRepresentantesVotaron() {
		return numeroRepresentantesVotaron;
	}

	public void setNumeroRepresentantesVotaron(Integer numeroRepresentantesVotaron) {
		this.numeroRepresentantesVotaron = numeroRepresentantesVotaron;
	}

	public Integer getSumaVotantes() {
		return sumaVotantes;
	}

	public void setSumaVotantes(Integer sumaVotantes) {
		this.sumaVotantes = sumaVotantes;
	}

	public Integer getVotosSacadosUrna() {
		return votosSacadosUrna;
	}

	public void setVotosSacadosUrna(Integer votosSacadosUrna) {
		this.votosSacadosUrna = votosSacadosUrna;
	}

	public Integer getVotosXPartidoYCoalicion() {
		return votosXPartidoYCoalicion;
	}

	public void setVotosXPartidoYCoalicion(Integer votosXPartidoYCoalicion) {
		this.votosXPartidoYCoalicion = votosXPartidoYCoalicion;
	}

	public Boolean getEsIgualVotosPersonaXVotosUrna() {
		return esIgualVotosPersonaXVotosUrna;
	}

	public void setEsIgualVotosPersonaXVotosUrna(Boolean esIgualVotosPersonaXVotosUrna) {
		this.esIgualVotosPersonaXVotosUrna = esIgualVotosPersonaXVotosUrna;
	}

	public Boolean getEsIgualVotosUrnaXTotalVotacion() {
		return esIgualVotosUrnaXTotalVotacion;
	}

	public void setEsIgualVotosUrnaXTotalVotacion(Boolean esIgualVotosUrnaXTotalVotacion) {
		this.esIgualVotosUrnaXTotalVotacion = esIgualVotosUrnaXTotalVotacion;
	}

	public List<VotacionDTO> getVotacion() {
		return votacion;
	}

	public void setVotacion(List<VotacionDTO> votacion) {
		this.votacion = votacion;
	}

}
