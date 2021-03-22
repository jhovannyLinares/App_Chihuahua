package mx.morena.persistencia.repository;

import java.util.Date;
import java.util.List;

import mx.morena.persistencia.entidad.Convencidos;

public interface IConvencidosRepository {

	List<Convencidos> getByDistritoFederal(Long idDistrito, Long tipo);

	List<Convencidos> findByClaveElector(String claveElector);

	public Convencidos getByCurp(String curp, Long tipo);

	public Convencidos getByIdAndEstatus(Long idCot, char estatus, Long tipo);

	void save(Convencidos convencidos);

	void updateStatusCot(Long id, char estatus, Date fechaBaja, Long tipo, String tipoFecha);

	Long getIdMax();
	
	List<Convencidos> getAllCots(Long tipo);

    List<Convencidos> getByDfAndMpio(Long idDistrito, Long idMunicipio, Long tipo);
    
    List<Convencidos> getByDfAndMpioAndSeccion(Long distritoFederalId, Long idMunicipio, Long idSeccion,
			Long convencido);

	List<Convencidos> getByDfAndMpioAndSeccionAndCveE(Long distritoFederalId, Long idMunicipio, Long idSeccion,
			String claveElector, Long convencido);

}
