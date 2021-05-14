package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Eleccion;
import mx.morena.persistencia.entidad.TipoActas;

public interface IEleccionRepository {
	
	List<Eleccion> findAll();
	
	List<TipoActas> findAllActas();

}
