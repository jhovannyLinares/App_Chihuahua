package mx.morena.negocio.servicios;

import java.io.IOException;
import java.util.List;

import mx.morena.negocio.dto.ActasVotacionDTO;
import mx.morena.negocio.dto.AfluenciaVotosDTO;
import mx.morena.negocio.dto.CasillasDTO;
import mx.morena.negocio.dto.CierreCasillaDTO;
import mx.morena.negocio.dto.DatosRcDTO;
import mx.morena.negocio.dto.EstadoVotacionDTO;
import mx.morena.negocio.dto.IncidenciasCasillasDTO;
import mx.morena.negocio.dto.InstalacionCasillasDTO;
import mx.morena.negocio.dto.ReporteCasillaDTO;
import mx.morena.negocio.dto.ResultadoOkDTO;
import mx.morena.negocio.dto.VotacionesDTO;
import mx.morena.negocio.exception.CotException;

public interface IInstalacionCasillaService {

	Long saveInstalacionCasilla(InstalacionCasillasDTO dto, long perfil, long usuario) throws CotException, IOException;

	Long saveIncidenciasCasilla(IncidenciasCasillasDTO dto, long perfil, long usuario) throws CotException, IOException;
	
	List<CasillasDTO> getCasillasAsignadas(Long idUsuario) throws CotException;

	String horaCierre(long usuario, CierreCasillaDTO dto, long perfil) throws CotException, IOException;

	List<ReporteCasillaDTO> getInformeVotacion(Long idCasilla, long idUsuario) throws CotException;

	List<VotacionesDTO> getVotaciones(Long idCasilla) throws CotException;

	InstalacionCasillasDTO getInstalacionCasilla(long perfil, long usuario, Long idCasilla) throws CotException;

	DatosRcDTO getDatosRc(long perfil, long usuario) throws CotException;
	
	String saveEstadoVotacion(EstadoVotacionDTO dto, long perfil, long usuario)throws CotException, IOException;
	
	ResultadoOkDTO saveEstadoP(List<EstadoVotacionDTO> dto, Long perfil, Long usuario)throws CotException, IOException;
	
	ResultadoOkDTO saveAfluencia(List<AfluenciaVotosDTO> dto, Long perfil, Long usuario)throws CotException, IOException;
	
	ResultadoOkDTO saveActas(List<ActasVotacionDTO> dto, Long perfil, Long usuario)throws CotException, IOException;

	String saveActasVotos(ActasVotacionDTO dto, long perfil, long usuario) throws CotException, IOException;

	String saveAfluenciaVotos(AfluenciaVotosDTO dto, long perfil, long usuario) throws CotException, IOException;
	
}
