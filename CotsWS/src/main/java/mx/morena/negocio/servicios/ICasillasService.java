package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.EnvioActasDTO;
import mx.morena.negocio.dto.PartidosXAmbitoDTO;
import mx.morena.negocio.dto.ResultadoOkDTO;
import mx.morena.negocio.dto.ResultadoVotacionDTO;
import mx.morena.negocio.dto.UbicacionCasillaDTO;
import mx.morena.negocio.dto.VotacionesDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.impl.PreguntasCasillaDTO;

public interface ICasillasService {

	public Long save( EnvioActasDTO acta , long perfil, long idUsuario) throws CotException;

	public ResultadoOkDTO saveResultados(ResultadoVotacionDTO actas, long perfil, long usuario) throws CotException;

	public List<VotacionesDTO> getActas(Long idCasilla) throws CotException;
	
	public List<EnvioActasDTO> getActasByTipo(Long idTipoActa, Long perfil) throws CotException;

	public List<PartidosXAmbitoDTO> getPartidos(Long idCasilla) throws CotException;

	public PreguntasCasillaDTO getFormulario(Long idCasilla, Long ambito) throws CotException;

	public String updateDatosCasilla(long perfil, Long idCasilla, UbicacionCasillaDTO dto) throws CotException;	
	
}
