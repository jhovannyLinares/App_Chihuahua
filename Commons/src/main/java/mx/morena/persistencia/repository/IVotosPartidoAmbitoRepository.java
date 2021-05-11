package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Partido;
import mx.morena.persistencia.entidad.Votacion;

public interface IVotosPartidoAmbitoRepository {
	
	Long getVotosByDistritoAndMunicipioAndPartido(Long idEntidad, Long df, Long idMunicipio, Long idEleccion);
	
	Long getVotosByEleccionAndPartido(Long idFederal, Long idEleccion, Long idPartido);
	
	List<Votacion> getByAmbitoAndPartido(Long idEstado, Long idFederal, Long idMunicipio, Long idEleccion, Long idPartido);

	Long getVotosPartidoByDistrito(Long idMunicipio, Long idDistrito, Long idEleccion, Long idPartido);

	Long getNulosByMunicipio(Long idMunicipio, Long idFederal, Long idEleccion, String partido);

	Long getCoalicionByIdCoalicion(Long idMunicipio, Long idDistrito, Long idEleccion, Long idCoalicion);
	
	Long getCoalicionByCoalicion(Long idDistrito, Long idEleccion, Long idCoalicion);
	
	Long getCoalicion(Long idMunicipio, Long idEleccion, Long idCoalicion);
	
	List<Partido> getPartidosGobernador();
	
	List<Partido>getPartidosFederal();
	
	List<Partido>getPartidosLocal(Long idFederal);
	
	List<Partido>getPartidosMunicipal(Long idUbicacion);
	
	List<Partido>getPartidosSindico(Long idUbicacion);
	
	Long getVotosByPartido(Long idEleccion, Long idPartido);
}
