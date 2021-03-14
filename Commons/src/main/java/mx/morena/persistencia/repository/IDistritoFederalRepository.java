package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.DistritoFederal;

public interface IDistritoFederalRepository {

	DistritoFederal findById(String federal);

	List<DistritoFederal> findAll();

	List<DistritoFederal> findByEntidad(String idEntidad);

}
