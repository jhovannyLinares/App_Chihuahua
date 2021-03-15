package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.SeccionElectoral;

public interface IConvencidosRepository {

	List<Convencidos> getByDistritoFederal(Long idDistrito);

	List<Convencidos> getByMunicipio(Long municipio);

	List<Convencidos> getBySeccionesElectoralesIn(List<SeccionElectoral> seccion);

	List<Convencidos> findByClaveElector(String claveElector);

	public Convencidos getByCurp(String curp);

	public Convencidos getByIdAndEstatus(Long idCot, char estatus);

	void save(Convencidos convencidos);
}
