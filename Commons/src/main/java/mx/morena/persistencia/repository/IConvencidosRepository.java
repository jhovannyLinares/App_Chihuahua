package mx.morena.persistencia.repository;

import java.util.List;
import java.util.Optional;

import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.SeccionElectoral;

public interface IConvencidosRepository {

	List<Convencidos> getByClaveElector(String claveElector);

	List<Convencidos> getByDistritoFederal(DistritoFederal dFederal);

	List<Convencidos> getByMunicipio(Municipio municipio);

	List<Convencidos> getBySeccionesElectoralesIn(List<SeccionElectoral> seccion);

	Convencidos findByClaveElector(String claveElector);

	public Convencidos getByCurp(String curp);

	public Convencidos getByIdAndEstatus(Long idCot, char estatus);

	void save(Convencidos convencidos);
}
