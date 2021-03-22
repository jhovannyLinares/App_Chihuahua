package mx.morena.negocio.dto;

public class CatalogoCrgDTO {

	private String nombre;
	
	private int tipo;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "CatalogoCrgDTO [nombre=" + nombre + ", tipo=" + tipo + "]";
	}
}
