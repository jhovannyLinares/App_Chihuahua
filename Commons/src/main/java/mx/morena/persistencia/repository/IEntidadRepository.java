package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Entidad;

public interface IEntidadRepository {

	List<Entidad> findAll();

	Entidad findById(Long idEntidad);

}
