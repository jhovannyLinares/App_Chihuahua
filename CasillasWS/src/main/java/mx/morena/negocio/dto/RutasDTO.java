package mx.morena.negocio.dto;

public class RutasDTO {

    private Long idDistrito;
	
	private Long zonaCrg;
	
	private String idZonaCrg;
	
	private Long ruta;
	
	private Long idCasilla;
	
	private String nombreDistrito;

	public Long getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(Long idDistrito) {
		this.idDistrito = idDistrito;
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

	public Long getRuta() {
		return ruta;
	}

	public void setRuta(Long ruta) {
		this.ruta = ruta;
	}

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public String getNombreDistrito() {
		return nombreDistrito;
	}

	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}
	
	
	
}
