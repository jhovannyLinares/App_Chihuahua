package mx.morena.persistencia.entidad;

import java.sql.Time;
import java.sql.Timestamp;

public class Capacitacion {

	private Long idRepresentante;
	
	private String claveElector;
	
	private String tipoRepresentante;
	
	private Long asignado;
	
	private String cargo;
	
	private Boolean isNombramiento;
	
	private String tomoCapacitacion;
	
	private Timestamp fechaCapacitaion;
	
	private Time horaCapacitacion;
	
	private String lugarCapacitacion;
	
	private String calle;

	private String numExt;
	
	private String numInt;
	
	private String colonia;
	
	private String municipio;

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

	public String getTomoCapacitacion() {
		return tomoCapacitacion;
	}

	public void setTomoCapacitacion(String tomoCapacitacion) {
		this.tomoCapacitacion = tomoCapacitacion;
	}

	public Timestamp getFechaCapacitaion() {
		return fechaCapacitaion;
	}

	public void setFechaCapacitaion(Timestamp fechaCapacitaion) {
		this.fechaCapacitaion = fechaCapacitaion;
	}

	public Time getHoraCapacitacion() {
		return horaCapacitacion;
	}

	public void setHoraCapacitacion(Time horaCapacitacion) {
		this.horaCapacitacion = horaCapacitacion;
	}

	public String getLugarCapacitacion() {
		return lugarCapacitacion;
	}

	public void setLugarCapacitacion(String lugarCapacitacion) {
		this.lugarCapacitacion = lugarCapacitacion;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumExt() {
		return numExt;
	}

	public void setNumExt(String numExt) {
		this.numExt = numExt;
	}

	public String getNumInt() {
		return numInt;
	}

	public void setNumInt(String numInt) {
		this.numInt = numInt;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
}
