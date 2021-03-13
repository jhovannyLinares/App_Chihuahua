package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Localidad;

public interface ILocalidadRepository {

	Localidad getById(String localidad);

	List<Localidad> getByMunicipio(String municipio);

}
