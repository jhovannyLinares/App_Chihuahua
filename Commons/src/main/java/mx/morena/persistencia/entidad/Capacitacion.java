package mx.morena.persistencia.entidad;

public class Capacitacion {

	private Long idRepresentante;
	
	private String claveElector;
	
	private String tipoRepresentante;
	
	private Long asignado;
	
	private String cargo;
	
	private Boolean isNombramiento;
	
	private RegistroCapacitados capacitacion;

	public Long getIdRepresentante() {
		return idRepresentante;
	}

	public void setIdRepresentante(Long idRepresentante) {
		this.idRepresentante = idRepresentante;
	}

	public String getClaveElector() {
		return claveElector;
	}

	public void setClaveElector(String claveElector) {
		this.claveElector = claveElector;
	}

	public String getTipoRepresentante() {
		return tipoRepresentante;
	}

	public void setTipoRepresentante(String tipoRepresentante) {
		this.tipoRepresentante = tipoRepresentante;
	}

	public Long getAsignado() {
		return asignado;
	}

	public void setAsignado(Long asignado) {
		this.asignado = asignado;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Boolean getIsNombramiento() {
		return isNombramiento;
	}

	public void setIsNombramiento(Boolean isNombramiento) {
		this.isNombramiento = isNombramiento;
	}

	public RegistroCapacitados getCapacitacion() {
		return capacitacion;
	}

	public void setCapacitacion(RegistroCapacitados capacitacion) {
		this.capacitacion = capacitacion;
	}
}
