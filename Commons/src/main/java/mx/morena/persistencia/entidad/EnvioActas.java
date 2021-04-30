package mx.morena.persistencia.entidad;

import java.sql.Time;
import java.sql.Timestamp;

public class EnvioActas {

	private Long id_acta;
	private Long tipo_votacion;
	private String ruta_acta;
	private Long id_casilla;
	private Timestamp registro_acta;

	public Long getId_acta() {
		return id_acta;
	}

	public void setId_acta(Long id_acta) {
		this.id_acta = id_acta;
	}

	public Long getTipo_votacion() {
		return tipo_votacion;
	}

	public void setTipo_votacion(Long tipo_votacion) {
		this.tipo_votacion = tipo_votacion;
	}

	public String getRuta_acta() {
		return ruta_acta;
	}

	public void setRuta_acta(String ruta_acta) {
		this.ruta_acta = ruta_acta;
	}

	public Long getId_casilla() {
		return id_casilla;
	}

	public void setId_casilla(Long id_casilla) {
		this.id_casilla = id_casilla;
	}

	public Timestamp getRegistro_acta() {
		return registro_acta;
	}

	public void setRegistro_acta(Timestamp registro_acta) {
		this.registro_acta = registro_acta;
	}

}
