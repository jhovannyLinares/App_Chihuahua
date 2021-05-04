package mx.morena.negocio.dto;

import java.util.List;

public class ReporteCrgAsignadoDTO {
	
	private Long idDistrito;
	private Long idLocal;
	private String municipio;
	private Long crg;
	private List<Long> rutas;
	private Long casillas;
	private Long propietarioUno;
	private Long propietarioDos;
	private Long suplenteUno;
	private Long suplenteDos;

	public Long getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(Long idDistrito) {
		this.idDistrito = idDistrito;
	}

	public Long getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Long idLocal) {
		this.idLocal = idLocal;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Long getCrg() {
		return crg;
	}

	public void setCrg(Long crg) {
		this.crg = crg;
	}

	public List<Long> getRutas() {
		return rutas;
	}

	public void setRutas(List<Long> rutas) {
		this.rutas = rutas;
	}

	public Long getCasillas() {
		return casillas;
	}

	public void setCasillas(Long casillas) {
		this.casillas = casillas;
	}

	public Long getPropietarioUno() {
		return propietarioUno;
	}

	public void setPropietarioUno(Long propietarioUno) {
		this.propietarioUno = propietarioUno;
	}

	public Long getPropietarioDos() {
		return propietarioDos;
	}

	public void setPropietarioDos(Long propietarioDos) {
		this.propietarioDos = propietarioDos;
	}

	public Long getSuplenteUno() {
		return suplenteUno;
	}

	public void setSuplenteUno(Long suplenteUno) {
		this.suplenteUno = suplenteUno;
	}

	public Long getSuplenteDos() {
		return suplenteDos;
	}

	public void setSuplenteDos(Long suplenteDos) {
		this.suplenteDos = suplenteDos;
	}
}