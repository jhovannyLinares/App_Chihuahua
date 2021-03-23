package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.CotDTO;
import mx.morena.negocio.dto.CotResponseDTO;
import mx.morena.negocio.dto.SeccionDTO;
import mx.morena.negocio.exception.CotException;

public interface ICotService {

	public Long save(CotDTO cot, long perfil, long idUsuario) throws CotException;

	public String asignarSecciones(List<Long> idSecciones, Long idCot, long perfil) throws CotException;
	
	public String suspender(Long idCot, long perfil, long idUsuario) throws CotException;

	public String activar(Long idCot, long perfil) throws CotException;
	
	public List<CotResponseDTO> getCots(long perfil, Long idDistrito, Long idMunicipio) throws CotException;
	
	public List<SeccionDTO> seccionesSinAsignar(long perfil, Long idMunicipio) throws CotException;
	
	public String update(CotDTO cot, long perfil, Long id) throws CotException;
}
