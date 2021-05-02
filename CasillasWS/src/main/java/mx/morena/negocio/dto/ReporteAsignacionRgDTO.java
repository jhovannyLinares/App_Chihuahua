package mx.morena.negocio.dto;

public class ReporteAsignacionRgDTO {
	
	private Long idDistrito;
	
	private Long idLocal;
	
	private String municipio;
	
	private Long idRuta;
	
	private Long idCasilla;
	
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

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
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
