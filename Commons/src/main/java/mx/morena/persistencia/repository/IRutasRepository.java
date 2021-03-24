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
		
	List<Rutas> getRutas(Long idFederal, Long zonaCRG, Long ruta, Long casilla);

	List<Rutas> getZonasByDistrito(Long idDistrito);

	List<Rutas> getRutasByZonas(Long zonaCrg);

	List<Rutas> getCasillaByRuta(Long ruta);
	
}
