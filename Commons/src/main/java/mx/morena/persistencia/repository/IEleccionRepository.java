package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Eleccion;

public interface IEleccionRepository {
	
	List<Eleccion> findAll();

}
