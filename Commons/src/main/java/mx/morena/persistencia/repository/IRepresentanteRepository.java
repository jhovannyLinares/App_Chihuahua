package mx.morena.persistencia.repository;

import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Representantes;


public interface IRepresentanteRepository {

	Representantes findByClaveElector(String claveElector);
	
	void save (Representantes representantes);

}
