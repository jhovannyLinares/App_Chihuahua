package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Incidencias;
import mx.morena.persistencia.entidad.IncidenciasCasillas;

public interface IIncidenciasCasillasRepository {

	int save(IncidenciasCasillas ic);
	
	List<Incidencias> getByIdCasilla(Long idCasilla, Integer tipoReporte);
}
