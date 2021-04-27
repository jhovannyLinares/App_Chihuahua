package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.AsignacionCasillas;
import mx.morena.persistencia.entidad.Casilla;

public interface ICasillaRepository {

	public List<Casilla> getCasillas(Long entidad);

	public List<Casilla> getCasillas(Long entidad, Long federal);

	public List<Casilla> getCasillas(Long entidad, Long federal, Long municipio);

	public List<Casilla> getCasillasFederal(Long distritoFederalId);

	public List<Casilla> getCasillasMunicipio(Long municipioId);

	public List<Casilla> getCasillasSeccion(Long seccionId);

	public Long countByDistritoAndTipologia(Long distritoId, String tipologia);
	
	public Long countByLocalAndTipologia(Long localId, String tipologia);
	
	public Long countByMunicipioAndTipologia(Long idMunicipio, String tipologia);
	
	public List<AsignacionCasillas> getCasillasAsignadasById(Long idCasilla);
	
	public List<AsignacionCasillas> getCasillasAsignadasByRuta(Long entidad, Long idDistritoF, Long idRuta);

	public List<Casilla> getCasillasById(Long idCasilla);

}
