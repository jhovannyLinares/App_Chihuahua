package mx.morena.negocio.servicio;

import java.util.List;

import mx.morena.negocio.dto.ConvencidosDTO;
import mx.morena.negocio.exception.ConvencidosException;

public interface IConvencidosService {

	List<ConvencidosDTO> getConvencidos(String distritoFederalId, String idMunicipio, String idSeccion, String claveElector)
			throws ConvencidosException;

	Long save(long usuario, ConvencidosDTO dto) throws ConvencidosException;

	boolean findByClaveElector(String claveElector) throws ConvencidosException;

}
