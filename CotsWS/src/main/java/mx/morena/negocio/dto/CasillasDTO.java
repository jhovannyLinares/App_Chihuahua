package mx.morena.negocio.dto;

public class CasillasDTO {
	
	private Long idCasilla;
	
	private String tipoCasilla;
	
	private String distritoFederal;
	
	private Long ruta;
	
	private Long zonaCrg;
	
	private String idZonaCrg;

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
}
