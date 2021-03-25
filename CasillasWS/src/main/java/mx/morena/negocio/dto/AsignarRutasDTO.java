package mx.morena.negocio.dto;

import java.util.List;

public class AsignarRutasDTO {

//	private Long id;
	
	private List<Long> idRuta;
	
	private Long idCrg;
	
	public AsignarRutasDTO() {
		// TODO Auto-generated constructor stub
	}

	public List<Long> getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(List<Long> idRuta) {
		this.idRuta = idRuta;
	}

	public Long getIdCrg() {
		return idCrg;
	}

	public void setIdCrg(Long idCrg) {
		this.idCrg = idCrg;
	}

	@Override
	public String toString() {
		return "AsignarRutasDTO [idRuta=" + idRuta + ", idCrg=" + idCrg + "]";
	}
}
