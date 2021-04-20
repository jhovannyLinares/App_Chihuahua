package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.RepresentantesAsignados;

public interface IRepresentantesAsignadosRepository {
	
	void save (RepresentantesAsignados reAsigandos);
	
	List<RepresentantesAsignados> getByEntidadAndIdRepresentante(Long entidad, Long id);
}
