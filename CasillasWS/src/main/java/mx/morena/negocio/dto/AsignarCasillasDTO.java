package mx.morena.negocio.dto;

import java.util.List;

public class AsignarCasillasDTO {
	
	private List<Long> idCasillas;
	
	private Long idRuta;

	public List<Long> getIdCasillas() {
		return idCasillas;
	}

	public void setIdCasillas(List<Long> idCasillas) {
		this.idCasillas = idCasillas;
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}
	
}
