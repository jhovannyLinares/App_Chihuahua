package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Localidad;

public interface ILocalidadRepository {

	Localidad getById(Long localidad);

	List<Localidad> getByMunicipio(Long municipio);

}
