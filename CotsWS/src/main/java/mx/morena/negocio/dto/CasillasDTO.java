package mx.morena.negocio.dto;

import java.util.List;

public class CasillasDTO {

	private Long idCasilla;

	private String tipoCasilla;

	private String distritoFederal;

	private Long ruta;

	private Long zonaCrg;

	private String idZonaCrg;

	private Boolean open;

	private Long seccion;

	private String casilla;

	private List<VotacionesDTO> votaciones;
	
	private Boolean seInstalo;
	
	private String llegoRc;
	
	private Boolean comenzoVotacion; 
	
	private AfluenciasVotoDTO afluencia = new AfluenciasVotoDTO();
	
	private ActasVotoDTO actas = new ActasVotoDTO();
	
	

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public String getTipoCasilla() {
		return tipoCasilla;
	}

	public void setTipoCasilla(String tipoCasilla) {
		this.tipoCasilla = tipoCasilla;
	}

	public String getDistritoFederal() {
		return distritoFederal;
	}

	public void setDistritoFederal(String distritoFederal) {
		this.distritoFederal = distritoFederal;
	}

	public Long getRuta() {
		return ruta;
	}

	public void setRuta(Long ruta) {
		this.ruta = ruta;
	}

	public Long getZonaCrg() {
		return zonaCrg;
	}

	public void setZonaCrg(Long zonaCrg) {
		this.zonaCrg = zonaCrg;
	}

	public String getIdZonaCrg() {
		return idZonaCrg;
	}

	public void setIdZonaCrg(String idZonaCrg) {
		this.idZonaCrg = idZonaCrg;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Long getSeccion() {
		return seccion;
	}

	public void setSeccion(Long seccion) {
		this.seccion = seccion;
	}

	public String getCasilla() {
		return casilla;
	}

	public void setCasilla(String casilla) {
		this.casilla = casilla;
	}

	public List<VotacionesDTO> getVotaciones() {
		return votaciones;
	}

	public void setVotaciones(List<VotacionesDTO> votaciones) {
		this.votaciones = votaciones;
	}

	public Boolean getSeInstalo() {
		return seInstalo;
	}

	public void setSeInstalo(Boolean seInstalo) {
		this.seInstalo = seInstalo;
	}

	public String getLlegoRc() {
		return llegoRc;
	}

	public void setLlegoRc(String llegoRc) {
		this.llegoRc = llegoRc;
	}

	public Boolean getComenzoVotacion() {
		return comenzoVotacion;
	}

	public void setComenzoVotacion(Boolean comenzoVotacion) {
		this.comenzoVotacion = comenzoVotacion;
	}

	public AfluenciasVotoDTO getAfluencia() {
		return afluencia;
	}

	public void setAfluencia(AfluenciasVotoDTO afluencia) {
		this.afluencia = afluencia;
	}

	public ActasVotoDTO getActas() {
		return actas;
	}

	public void setActas(ActasVotoDTO actas) {
		this.actas = actas;
	}
}
