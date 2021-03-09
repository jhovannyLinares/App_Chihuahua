package mx.morena.negocio.servicio;

import java.util.List;

import mx.morena.negocio.dto.ConvencidosDTO;
import mx.morena.negocio.exception.ConvencidosException;

public interface IConvencidosService {

	List<ConvencidosDTO> getConvencidos(Long idFederal, Long idMunicipio, Long idSeccion, String claveElector)
			throws ConvencidosException;

	String save(ConvencidosDTO dto) throws ConvencidosException;

	//ConvencidosDTO getByClaveElector(String claveElector);

}
