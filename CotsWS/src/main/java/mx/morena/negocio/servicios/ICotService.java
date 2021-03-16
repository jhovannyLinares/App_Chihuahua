package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.CotDTO;
import mx.morena.negocio.exception.CotException;

public interface ICotService {

	public Long save(CotDTO cot, long perfil, long idUsuario) throws CotException;

	public String asignarSecciones(List<String> idSecciones, Long idCot, long perfil) throws CotException;
	
	public String suspender(Long idCot, long perfil, long idUsuario) throws CotException;

}
