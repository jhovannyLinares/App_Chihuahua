package mx.morena.persistencia.repository;

import java.util.Date;
import java.util.List;

import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.SeccionElectoral;

public interface IConvencidosRepository {

	List<Convencidos> getByDistritoFederal(Long idDistrito,Long tipo);

	List<Convencidos> getByMunicipio(Long municipio,Long tipo);

	List<Convencidos> getBySeccionesElectoralesIn(List<SeccionElectoral> seccion,Long tipo);

	List<Convencidos> findByClaveElector(String claveElector,Long tipo);

	public Convencidos getByCurp(String curp,Long tipo);

	public Convencidos getByIdAndEstatus(Long idCot, char estatus,Long tipo);

	void save(Convencidos convencidos);
	
	void updateStatusCot(Long id, char estatus, Date fechaBaja,Long tipo);
}
