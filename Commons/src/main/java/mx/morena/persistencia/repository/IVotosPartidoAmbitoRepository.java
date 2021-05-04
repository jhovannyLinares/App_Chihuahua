package mx.morena.persistencia.repository;

public interface IVotosPartidoAmbitoRepository {
	
	Long getVotosByDistritoAndMunicipioAndPartido(Long idEntidad, Long df, Long idMunicipio, Long idEleccion, Long idPartido);
}
