package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.Rutas;
import mx.morena.persistencia.entidad.Zona;

public interface IRutasRepository {
	
	public List<Rutas> findByCrgId(Long crgId);

	public Representantes getAllCrg(Long tipo);
	
	public List<Rutas> findById(Long idRutas);
	
	void updateIdCrg(Long idRuta, Long idCrg);
		
	List<Rutas> getRutas(Long idFederal, Long zonaCRG, Long ruta, Long casilla);

	List<Zona> getZonasByDistrito(Long idDistrito);

	List<Rutas> getRutasByZonas(Long zona, String idzona);

	List<Rutas> getCasillaByRuta(String rutaRG);

	List<Rutas> getTipoCasilla(String idRutaRg, Long seccionId);
	
	void asignarCasillas(Long idCasilla, Long idRuta, String ruta);
	
	void cambiarEstatusCasilla(Long idCasilla, int asignado);
	
	void desasignarCasillas(Long idCasilla);
	
	Rutas getCasillaByIdAndEstatus(Long idCasilla, int asignado);
	
	Rutas getRutaById(Long idRuta);

	public Zona getZonasByid(Long zonaCrg);

	public Rutas getRutaByid(Long rutaid);

	public List<Rutas> getZonasByWhere(Long idFederal, Long zonaCRG, Long ruta, Long casilla);

	public List<Rutas> getRutasByIdRutaRG(String idRutaRg);
	
}
