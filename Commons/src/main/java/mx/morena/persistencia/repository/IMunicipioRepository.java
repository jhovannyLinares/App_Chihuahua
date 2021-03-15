package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Municipio;

public interface IMunicipioRepository {

	Municipio findById(Long municipio);

	List<Municipio> findAll();

	List<Municipio> getByFederal(Long idFederal);

}
