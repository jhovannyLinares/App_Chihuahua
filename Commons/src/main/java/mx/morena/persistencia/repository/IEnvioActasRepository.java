package mx.morena.persistencia.repository;

import mx.morena.persistencia.entidad.EnvioActas;

public interface IEnvioActasRepository {

	void save(EnvioActas Actas);
	
	Long getIdMax();
}
