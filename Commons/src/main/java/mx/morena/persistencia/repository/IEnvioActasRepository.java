package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.EnvioActas;

public interface IEnvioActasRepository {

	void save(EnvioActas Actas);
	
	Long getIdMax();
	
	Long countByTipoVotacionAndDistrito(Long df, Long tipo);

	List<EnvioActas> getCasilla(Long idCasilla);
}
