package mx.morena.persistencia.entidad;

import java.sql.Time;
import java.sql.Timestamp;

public class IncidenciasCasillas {

	private Long id;

	private Long idCasilla;

	private Long idIncidencia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Long getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(Long idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

}
