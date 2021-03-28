package mx.morena.negocio.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RutaResponseDTO {

	private Long idDistrito;
	
	@JsonIgnore
	private Long id;

	private String nombreDistrito;

	private String idRutaRg;

	private String idZonaCrg;

	private Long ruta;

	private Long seccionId;

	private String zona;

	private Long zonaCrg;

	private Long idMunicipio;

	private String municipio;

	private List<TipoCasillaDTO> lstTipoCasilla;

	public Long getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(Long idDistrito) {
		this.idDistrito = idDistrito;
	}

	public String getNombreDistrito() {
		return nombreDistrito;
	}

	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}

	public String getIdRutaRg() {
		return idRutaRg;
	}

	public void setIdRutaRg(String idRutaRg) {
		this.idRutaRg = idRutaRg;
	}

	public String getIdZonaCrg() {
		return idZonaCrg;
	}

	public void setIdZonaCrg(String idZonaCrg) {
		this.idZonaCrg = idZonaCrg;
	}

	public Long getRuta() {
		return ruta;
	}

	public void setRuta(Long ruta) {
		this.ruta = ruta;
	}

	public Long getSeccionId() {
		return seccionId;
	}

	public void setSeccionId(Long seccionId) {
		this.seccionId = seccionId;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public Long getZonaCrg() {
		return zonaCrg;
	}

	public void setZonaCrg(Long zonaCrg) {
		this.zonaCrg = zonaCrg;
	}

	public List<TipoCasillaDTO> getLstTipoCasilla() {
		return lstTipoCasilla;
	}

	public void setLstTipoCasilla(List<TipoCasillaDTO> lstTipoCasilla) {
		this.lstTipoCasilla = lstTipoCasilla;
	}

	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	

}
