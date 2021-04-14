package mx.morena.negocio.dto;

public class ReporteAsignacionCasillasDTO {
	
	private Long idDistrito;
	
	private Long idLocal;
	
	private Long idMunicipio;
	
	private Long crg;
	
	private Long idRuta;
	
	private Long idSeccion;
	
	private String tipoCasilla;
	
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

	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Long getCrg() {
		return crg;
	}

	public void setCrg(Long crg) {
		this.crg = crg;
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}

	public Long getIdSeccion() {
		return idSeccion;
	}

	public void setIdSeccion(Long idSeccion) {
		this.idSeccion = idSeccion;
	}

	public String getTipoCasilla() {
		return tipoCasilla;
	}

	public void setTipoCasilla(String tipoCasilla) {
		this.tipoCasilla = tipoCasilla;
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
