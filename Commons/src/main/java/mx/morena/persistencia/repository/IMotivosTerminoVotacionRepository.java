package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.MotivosTerminoVotacion;

public interface IMotivosTerminoVotacionRepository {
	
	List<MotivosTerminoVotacion> findAll();
	
	MotivosTerminoVotacion getById(Long id);
}
