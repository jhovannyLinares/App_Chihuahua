package mx.morena.persistencia.entidad;

public class Rutas {
	
	private Long id;
	
	private Long idDistrito;
	
	private Long zonaCrg;
	
	private String idZonaCrg;
	
	private Long ruta;
	
	private Long idCasilla;
	
	private Long status;
	
	private String nombreDistrito;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getNombreDistrito() {
		return nombreDistrito;
	}

	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}
	
}
