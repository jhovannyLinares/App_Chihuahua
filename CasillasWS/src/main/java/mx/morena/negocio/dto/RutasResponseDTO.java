package mx.morena.negocio.dto;

public class RutasResponseDTO {

    private Long idDistrito;
    
    private String nombreDistrito;
	
	private String idRutaRg;
	
	private Long ruta;
	
    private String tipoCasilla;
	
	private Long seccionId;
	
	private Long zonaCrg;
	
	private String idZonaCrg;
	
	private String Zona;

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

	public Long getRuta() {
		return ruta;
	}

	public void setRuta(Long ruta) {
		this.ruta = ruta;
	}

	public String getTipoCasilla() {
		return tipoCasilla;
	}

	public void setTipoCasilla(String tipoCasilla) {
		this.tipoCasilla = tipoCasilla;
	}

	public Long getSeccionId() {
		return seccionId;
	}

	public void setSeccionId(Long seccionId) {
		this.seccionId = seccionId;
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

	public String getZona() {
		return Zona;
	}

	public void setZona(String zona) {
		Zona = zona;
	}
	
}
