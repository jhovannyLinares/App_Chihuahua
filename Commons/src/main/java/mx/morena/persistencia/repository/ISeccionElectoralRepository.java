package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.SeccionElectoral;

public interface ISeccionElectoralRepository {

	List<SeccionElectoral> findByCotId(Long cotId);

}
