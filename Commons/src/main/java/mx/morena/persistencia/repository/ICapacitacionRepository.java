package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Capacitacion;

public interface ICapacitacionRepository {
	
	List<Capacitacion> getRepresentanteByClave( String claveElector);

}
