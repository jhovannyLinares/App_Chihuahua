package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Incidencias;

public interface IIncidenciasRepository {
	
	List<Incidencias> findAll();

}
