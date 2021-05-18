package mx.morena.presentacion.controlador;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
import mx.morena.negocio.dto.ReporteCasillaDTO;
import mx.morena.negocio.dto.ResultadoOkDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.IInstalacionCasillaService;
import mx.morena.security.controller.MasterController;


@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class InstalacionCasillaController extends MasterController {
	
	@Autowired
	private IInstalacionCasillaService IService;
	
	/**
	 * @param response
	 * @param request
	 * @param dto
	 * @return
	 * @throws IOException
	 * 
	 * Guardado de informacion de la instalacion de la casilla
	 */
	@PostMapping("/instalacionCasillas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long saveIstalacionCasilla(HttpServletResponse response, HttpServletRequest request, @RequestBody InstalacionCasillasDTO dto) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return IService.saveInstalacionCasilla(dto, perfil, usuario);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	@PostMapping("/reporteCasillas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long saveIncidenciaCasilla(HttpServletResponse response, HttpServletRequest request, @RequestBody IncidenciasCasillasDTO dto) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return IService.saveIncidenciasCasilla(dto, perfil, usuario);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/casillasAsignadas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<CasillasDTO> getCasillas(HttpServletResponse response, HttpServletRequest request) throws IOException {
		try {
			long idUsuario = getUsuario(request);
			return IService.getCasillasAsignadas(idUsuario);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("casillas/{idCasilla}/informeVotacion")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteCasillaDTO> getInformeVotacion(HttpServletResponse response, HttpServletRequest request,@PathVariable("idCasilla") Long idCasilla) throws IOException {
		try {
			long idUsuario = getUsuario(request);
			return IService.getInformeVotacion(idCasilla, idUsuario);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@PutMapping("/horaCierre")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String cierreCasilla(HttpServletResponse response, HttpServletRequest request, @RequestBody CierreCasillaDTO dto) throws IOException {
		long usuario = getUsuario(request);
		long perfil = getPerfil(request);

		try {
			return IService.horaCierre(usuario, dto, perfil);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	/**
	 * @param response
	 * @param request
	 * @param dto
	 * @return
	 * @throws IOException
	 * 
	 * Guardado de informacion del cierre de votación
	 */
	@PostMapping("/cierreVotacion")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String cierreVotacion(HttpServletResponse response, HttpServletRequest request, @RequestBody CierreVotacionDTO dto) throws IOException {
		long usuario = getUsuario(request);
		long perfil = getPerfil(request);

		try {
			return IService.horaCierreVotacion(usuario, dto, perfil);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	/**
	 * @param response
	 * @param request
	 * @param idCasilla
	 * @return
	 * @throws IOException
	 * 
	 * Consulta de informacion de instalacion de la casilla
	 */
	@GetMapping("/instalacionCasillas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public InstalacionCasillasDTO getIstalacionCasilla(HttpServletResponse response, HttpServletRequest request, 
			@RequestParam(value = "idCasilla", required = true) Long idCasilla) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);
 
		try {
			return IService.getInstalacionCasilla( perfil, usuario, idCasilla);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@PostMapping("/casillas/estadoVotacion")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResultadoOkDTO saveEstadoP(HttpServletResponse response, HttpServletRequest request, @RequestBody List<EstadoVotacionDTO> dto) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return IService.saveEstadoP(dto, perfil, usuario);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	/**
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 * 
	 * Consulta de informacion del RC logueado
	 */
	@GetMapping("/DatosRc")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public DatosRcDTO getDatosRc(HttpServletResponse response, HttpServletRequest request ) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);
 
		try {
			return IService.getDatosRc(perfil, usuario);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	
	@PostMapping("/afluenciaVotos")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResultadoOkDTO saveAfluencia(HttpServletResponse response, HttpServletRequest request, @RequestBody List<AfluenciaVotosDTO> dto) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return IService.saveAfluencia(dto, perfil, usuario);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@PostMapping("/actasVotacion")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResultadoOkDTO saveActas(HttpServletResponse response, HttpServletRequest request, @RequestBody List<ActasVotacionDTO> dto) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return IService.saveActas(dto, perfil, usuario);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	/**
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 * 
	 * Consulta de informacion registrada por el Rc
	 */
	@GetMapping("/registrosRc")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<IncidenciasCasillasRespDTO> getRegistrosVotacionRc(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idCasilla", required = true) Long idCasilla,
			@RequestParam(value = "tipoReporte", required = false) Integer tipoReporte) throws IOException {
		
		long usuario = getUsuario(request);
		long perfil = getPerfil(request);
 
		try {
			return IService.getRegistrosVotaciones(usuario, perfil, idCasilla, tipoReporte);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	/**
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 * 
	 * Consulta si un tipo de reporte existe en una casilla
	 */
	@GetMapping("/tipoReporte")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public boolean getTipoReporte(HttpServletResponse response, HttpServletRequest request,
				@RequestParam(value = "idCasilla", required = true) Long idCasilla,
				@RequestParam(value = "tipoReporte", required = true) Integer tipoReporte) throws IOException {
		long perfil = getPerfil(request);
 
		try {
			return IService.existeReporte(perfil, idCasilla, tipoReporte);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return false;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return false;
		}
	}
	
	/**
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 * 
	 * Consulta los datos registrados sobre el cierre de votacion
	 */
	@GetMapping("/cierreVotaciones")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<CierreVotacionResponseDTO> getCierreCasilla(HttpServletResponse response, HttpServletRequest request,
				@RequestParam(value = "idCasilla", required = true) Long idCasilla) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);
		
		try {
			return IService.getCierreVotacion(usuario, perfil, idCasilla);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	/**
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 * 
	 * Consulta si esta cerrada la votacion en una casilla
	 */
	@GetMapping("/votacionesCerradas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public boolean getVotacionesCerradas(HttpServletResponse response, HttpServletRequest request,
				@RequestParam(value = "idCasilla", required = true) Long idCasilla) throws IOException {
		long perfil = getPerfil(request);
 
		try {
			return IService.isCerradaVotacion(perfil, idCasilla);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return false;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return false;
		}
	}
}
