package mx.morena.persistencia.repository;

import mx.morena.persistencia.entidad.Representantes;


public interface IRepresentanteRepository {

	Representantes findByClaveElector(String claveElector);
	
	void save (Representantes representantes);
	
	Long getIdMax();

}
