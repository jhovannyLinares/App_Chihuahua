package mx.morena.negocio.dto;

import javax.persistence.Entity;

@Entity
public class DistritoFederalDTO {

	private Long id;

	private String cabeceraFederal;

	private long entidad;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCabeceraFederal() {
		return cabeceraFederal;
	}

	public void setCabeceraFederal(String cabeceraFederal) {
		this.cabeceraFederal = cabeceraFederal;
	}

	public long getEntidad() {
		return entidad;
	}

	public void setEntidad(long entidad) {
		this.entidad = entidad;
	}
	
	
}
