package mx.morena.negocio.dto;

import java.util.List;

public class AsignarRutasDTO {

	private List<Long> idRuta;
	
	private Long isCrg;
	
	public AsignarRutasDTO() {
		// TODO Auto-generated constructor stub
	}

	public List<Long> getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(List<Long> idRuta) {
		this.idRuta = idRuta;
	}

	public Long getIsCrg() {
		return isCrg;
	}

	public void setIsCrg(Long isCrg) {
		this.isCrg = isCrg;
	}
}
