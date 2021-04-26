package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.SeccionElectoral;

public interface ISeguimientoVotoRepository {
	
	Long countByDistAndTipo(Long distritoId, Long tipo);
	
	Long countByLocalAndTipo(Long perdil, Long tipo);
	
	Long countByNotificados(Long distrito, Long tipo);
	
	Long countNotificados(Long distritoId);

	List<SeccionElectoral> getDistritos();

}
