package mx.morena.negocio.dto;

public class CasillasCatalogoDto {

	private Long idCasilla;

	private String tipoCasilla;
	
	private String idRutaRg;
	
	private Long ruta;

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

	public String getIdRutaRg() {
		return idRutaRg;
	}

	public void setIdRutaRg(String idRutaRg) {
		this.idRutaRg = idRutaRg;
	}

	public Long getRuta() {
		return ruta;
	}

	public void setRuta(Long ruta) {
		this.ruta = ruta;
	}

}
