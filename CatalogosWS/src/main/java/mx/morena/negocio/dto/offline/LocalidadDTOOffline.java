package mx.morena.negocio.dto.offline;

public class LocalidadDTOOffline {

	private String id;

	private String descripcion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "LocalidadDTO [id=" + id + ", descripcion=" + descripcion + "]";
	}

}
