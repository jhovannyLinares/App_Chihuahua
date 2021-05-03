package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.EnvioActasDTO;
import mx.morena.negocio.dto.ResultadoOkDTO;
import mx.morena.negocio.dto.ResultadoVotacionDTO;
import mx.morena.negocio.dto.VotacionesDTO;
import mx.morena.negocio.exception.CotException;

public interface ICasillasService {

	public Long save( EnvioActasDTO acta , long perfil, long idUsuario) throws CotException;

	public ResultadoOkDTO saveResultados(ResultadoVotacionDTO actas, long perfil, long usuario) throws CotException;

	public List<VotacionesDTO> getActas(Long idCasilla) throws CotException;
	
}
