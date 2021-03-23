package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.SeccionElectoral;

public interface ISeccionElectoralRepository {

	List<SeccionElectoral> findByCotId(Long cotId, Long tipo); 
	
	List<SeccionElectoral> findById(Long idSecciones);

//	void save(SeccionElectoral sec);
	
	void updateIdCot(Long idSeccion, Long idCot);

	SeccionElectoral getById(Long localidad);

	List<SeccionElectoral> getByMunicipio(Long idMunicipio);

	List<SeccionElectoral> getSeccionesLibresByMpo(Long idMunicipio);
}
