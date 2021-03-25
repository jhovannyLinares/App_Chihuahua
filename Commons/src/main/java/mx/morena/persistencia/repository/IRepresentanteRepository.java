package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Perfil;
import mx.morena.persistencia.entidad.Representantes;


public interface IRepresentanteRepository {

	Representantes findByClaveElector(String claveElector);
	
	void save (Representantes representantes);
	
	Long getIdMax();
	
	public Representantes getById(Long idCrg);
	
	List<Representantes> getAllCrg(Long tipoRepresentante);
	
	List<Perfil> getAllTipoRep();

}
