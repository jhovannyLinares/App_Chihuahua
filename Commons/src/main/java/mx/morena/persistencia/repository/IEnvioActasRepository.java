package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.EnvioActas;

public interface IEnvioActasRepository {

	void save(EnvioActas Actas);
	
	Long getIdMax();
	
	Long countByTipoVotacionAndDistrito(Long df, Long tipo);

	List<EnvioActas> getCasilla(Long idCasilla);
	
	Long validaDuplicidadActa(Long tipo, Long casila, Long tipoActa);
	
	List<EnvioActas> getActaByTipo(Long idTipoActa);
	
	//se extra el base64 por medio del id
	List<EnvioActas> getBase64Byid(Long idTipoActa);
	
	
	
}
