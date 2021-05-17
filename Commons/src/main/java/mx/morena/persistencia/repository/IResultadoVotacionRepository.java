package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Preguntas;
import mx.morena.persistencia.entidad.Votacion;

public interface IResultadoVotacionRepository {

	public void save(List<Votacion> votaciones);

	public void save(Preguntas preguntas);

	public Preguntas getByIdCasilla(Long idCasilla, Long ambito);

	public List<Votacion> getVotosByCasilla(Long idCasilla, Long ambito);
}
