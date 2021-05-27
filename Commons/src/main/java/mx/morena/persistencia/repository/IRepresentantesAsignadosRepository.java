package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.DatosRc;
import mx.morena.persistencia.entidad.RepresentanteCargo;
import mx.morena.persistencia.entidad.RepresentantesAsignados;

public interface IRepresentantesAsignadosRepository {
	
	void save (RepresentantesAsignados reAsigandos);
	
	List<RepresentantesAsignados> getByEntidadAndIdRepresentante(Long entidad, Long id);

	Long getRutaIdByRepresentante(Long usuario);

	DatosRc getRepresentanteById(long usuario);

	List<RepresentanteCargo> getNombreRepresentanteByCasilla(Long idCasilla);
}
