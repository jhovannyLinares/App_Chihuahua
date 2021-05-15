package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Preguntas;
import mx.morena.persistencia.entidad.Votacion;

public interface IResultadoVotacionRepository {

	public void save(List<Votacion> votaciones);

	public void save(Preguntas preguntas);

	public List<Preguntas> getByIdCasilla(Long idCasilla);
}
