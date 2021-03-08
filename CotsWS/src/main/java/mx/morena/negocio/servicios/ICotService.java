package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.CotDTO;
import mx.morena.negocio.exception.CotException;

public interface ICotService {
	public String save(CotDTO cot, int perfil) throws CotException;
	public String asignarSecciones(List<Long> idSecciones, Long idCot, int perfil) throws CotException;
}
