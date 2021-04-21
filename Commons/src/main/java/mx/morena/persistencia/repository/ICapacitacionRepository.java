package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Capacitacion;
import mx.morena.persistencia.entidad.RegistroCapacitacion;

public interface ICapacitacionRepository {
	
	List<Capacitacion> getRepresentanteByClave( String claveElector);

	long saveCapacitacion(RegistroCapacitacion rc);

}
