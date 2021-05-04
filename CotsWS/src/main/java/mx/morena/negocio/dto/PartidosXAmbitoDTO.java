package mx.morena.negocio.dto;

import java.util.List;

public class PartidosXAmbitoDTO {

	private Long id;

	private String descripcion;
	
	private List<PartidoDTO> partidos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<PartidoDTO> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<PartidoDTO> partidos) {
		this.partidos = partidos;
	}
	
	



}
