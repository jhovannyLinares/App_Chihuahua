package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.DistritoFederal;

public interface IDistritoFederalRepository {

	DistritoFederal findById(Long federal);

	List<DistritoFederal> findAll();

	List<DistritoFederal> findByEntidad(Long idEntidad);

}
