package mx.morena.negocio.servicios;

import java.io.IOException;
import java.util.List;

import mx.morena.negocio.dto.ActasVotacionDTO;
import mx.morena.negocio.dto.AfluenciaVotosDTO;
import mx.morena.negocio.dto.CasillasDTO;
import mx.morena.negocio.dto.CierreCasillaDTO;
import mx.morena.negocio.dto.CierreVotacionDTO;
import mx.morena.negocio.dto.CierreVotacionResponseDTO;
import mx.morena.negocio.dto.DatosRcDTO;
import mx.morena.negocio.dto.EstadoVotacionDTO;
import mx.morena.negocio.dto.IncidenciasCasillasDTO;
import mx.morena.negocio.dto.IncidenciasCasillasRespDTO;
import mx.morena.negocio.dto.InstalacionCasillasDTO;
import mx.morena.negocio.dto.InstalacionCasillasResponseDTO;
import mx.morena.negocio.dto.ReporteCasillaDTO;
import mx.morena.negocio.dto.ResultadoOkDTO;
import mx.morena.negocio.dto.UbicacionCasillaDTO;
import mx.morena.negocio.dto.VotacionesDTO;
import mx.morena.negocio.exception.CotException;

public interface IInstalacionCasillaService {

	Long saveInstalacionCasilla(InstalacionCasillasDTO dto, long perfil, long usuario) throws CotException, IOException;

	Long saveIncidenciasCasilla(IncidenciasCasillasDTO dto, long perfil, long usuario) throws CotException, IOException;
	
	List<CasillasDTO> getCasillasAsignadas(Long idUsuario) throws CotException;

	String horaCierre(long usuario, CierreCasillaDTO dto, long perfil) throws CotException, IOException;
	
	String horaCierreVotacion(long usuario, CierreVotacionDTO dto, long perfil) throws CotException;

	List<ReporteCasillaDTO> getInformeVotacion(Long idCasilla, long idUsuario) throws CotException;

	List<VotacionesDTO> getVotaciones(Long idCasilla) throws CotException;

	InstalacionCasillasResponseDTO getInstalacionCasilla(long perfil, long usuario, Long idCasilla) throws CotException;

	DatosRcDTO getDatosRc(long perfil, long usuario) throws CotException;

	UbicacionCasillaDTO getDatosCasilla(long perfil, long usuario, Long idCasilla) throws CotException;
	
	String saveEstadoVotacion(EstadoVotacionDTO dto, long perfil, long usuario)throws CotException, IOException;
	
	ResultadoOkDTO saveEstadoP(List<EstadoVotacionDTO> dto, Long perfil, Long usuario)throws CotException, IOException;
	
	ResultadoOkDTO saveAfluencia(List<AfluenciaVotosDTO> dto, Long perfil, Long usuario)throws CotException, IOException;
	
	ResultadoOkDTO saveActas(List<ActasVotacionDTO> dto, Long perfil, Long usuario)throws CotException, IOException;

	String saveActasVotos(ActasVotacionDTO dto, long perfil, long usuario) throws CotException, IOException;

	String saveAfluenciaVotos(AfluenciaVotosDTO dto, long perfil, long usuario) throws CotException, IOException;
	
	List<IncidenciasCasillasRespDTO> getRegistrosVotaciones(Long idUsuario, Long perfil, Long idCasilla, Integer tipoReporte) throws CotException;
	
	boolean existeReporte(Long perfil, Long idCasilla, Integer tipoReporte) throws CotException;
	
	List<CierreVotacionResponseDTO> getCierreVotacion(Long idUsuario, Long perfil, Long idCasilla) throws CotException;
	
	boolean isCerradaVotacion(Long perfil, Long idCasilla) throws CotException;
}
