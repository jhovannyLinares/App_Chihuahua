package mx.morena.persistencia.entidad;

public class RepresentanteClaveElectoral {
	
	private Long idRepresentante;
	
	private String nombre;
	
	private Long idTipoRepresentante;
	
	private String tipoRepresentante;
	
	private Long idDistrito; 
	
	private String distrito;
	
	private Long idDistritoAsignado;
	
	private String distritoAsignado;;
	
	private boolean asignado;

	public Long getIdRepresentante() {
		return idRepresentante;
	}

	public void setIdRepresentante(Long idRepresentante) {
		this.idRepresentante = idRepresentante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdTipoRepresentante() {
		return idTipoRepresentante;
	}

	public void setIdTipoRepresentante(Long idTipoRepresentante) {
		this.idTipoRepresentante = idTipoRepresentante;
	}

	public String getTipoRepresentante() {
		return tipoRepresentante;
	}

	public void setTipoRepresentante(String tipoRepresentante) {
		this.tipoRepresentante = tipoRepresentante;
	}

	public Long getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(Long idDistrito) {
		this.idDistrito = idDistrito;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public Long getIdDistritoAsignado() {
		return idDistritoAsignado;
	}

	public void setIdDistritoAsignado(Long idDistritoAsignado) {
		this.idDistritoAsignado = idDistritoAsignado;
	}

	public String getDistritoAsignado() {
		return distritoAsignado;
	}

	public void setDistritoAsignado(String distritoAsignado) {
		this.distritoAsignado = distritoAsignado;
	}

	public boolean isAsignado() {
		return asignado;
	}

	public void setAsignado(boolean asignado) {
		this.asignado = asignado;
	}
}
