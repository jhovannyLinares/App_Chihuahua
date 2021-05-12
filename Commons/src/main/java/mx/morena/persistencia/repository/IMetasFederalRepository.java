package mx.morena.persistencia.repository;

import mx.morena.persistencia.entidad.Metas;

public interface IMetasFederalRepository {
	
	Metas getMetasByFederal(Long idFederal);

}
