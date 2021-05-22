package mx.morena.negocio.dto;

public class VotacionesDTO {

	private Long id;

	private String descripcion;

	private Boolean capturada = false;

	private Long tipoActa;

	private String descripcionTipo;

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

	public Boolean getCapturada() {
		return capturada;
	}

	public void setCapturada(Boolean capturada) {
		this.capturada = capturada;
	}

	public Long getTipoActa() {
		return tipoActa;
	}

	public void setTipoActa(Long tipoActa) {
		this.tipoActa = tipoActa;
	}

	public String getDescripcionTipo() {
		return descripcionTipo;
	}

	public void setDescripcionTipo(String descripcionTipo) {
		this.descripcionTipo = descripcionTipo;
	}

}
