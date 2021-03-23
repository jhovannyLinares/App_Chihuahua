package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.Rutas;

public interface IRutasRepository {
	
	List<Rutas> findByCrgId(Long crgId, Long tipo); 

	List<Representantes> getAllCrg(int tipo);
	
	public Rutas getByIdAndEstatus(Long idCrg, char estatus, Long tipo);
	
	List<Rutas> findById(Long idRutas);
	
	void updateIdCrg(Long idRuta, Long idCrg);
	
	
	// consulta de rutas
	List<Rutas> getByFederal(Long idFederal);
	
	List<Rutas> getByFedAndZonaCrg(Long idFederal, Long zonaCRG);
	
	List<Rutas> getByFedAndZonaCrgAndRuta(Long idFederal, Long zonaCRG, Long ruta);
	
	List<Rutas> getByFedAndZonaCrgAndRutaAndCasilla(Long idFederal, Long zonaCRG, Long ruta, Long casilla);
	
	
}
