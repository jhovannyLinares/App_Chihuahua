package mx.morena.negocio.dto;

import java.util.List;

public class AsignarSeccionesDTO {
	
	private List<String> idSecciones;
	
	private Long idCot;
	
	public List<String> getIdSecciones() {
		return idSecciones;
	}
	
	public void setIdSecciones(List<String> idSecciones) {
		this.idSecciones = idSecciones;
	}
	
	public Long getIdCot() {
		return idCot;
	}
	
	public void setIdCot(Long idCot) {
		this.idCot = idCot;
	}

	@Override
	public String toString() {
		return "AsignarSeccionesDTO [idSecciones=" + idSecciones + ", idCot=" + idCot + "]";
	}
	
}
