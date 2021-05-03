package mx.morena.persistencia.entidad;

import java.sql.Time;
import java.sql.Timestamp;

public class RegistroCapacitados {
	
	private String tomoCapacitacion;
	
	private Timestamp fechaCapacitaion;
	
	private Time horaCapacitacion;
	
	private String lugarCapacitacion;
	
	private String calle;

	private String numExt;
	
	private String numInt;
	
	private String colonia;
	
	private String municipio;

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
