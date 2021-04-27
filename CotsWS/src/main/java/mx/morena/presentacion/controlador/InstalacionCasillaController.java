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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.CasillasDTO;
import mx.morena.negocio.dto.CierreCasillaDTO;
import mx.morena.negocio.dto.IncidenciasCasillasDTO;
import mx.morena.negocio.dto.InstalacionCasillasDTO;
import mx.morena.negocio.dto.ReporteCasillaDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.IInstalacionCasillaService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class InstalacionCasillaController extends MasterController {
	
	@Autowired
	private IInstalacionCasillaService IService;
	
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
	
//	@GetMapping("/casillas/votaciones")
//	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
//	private List<VotacionesDTO> votacionesXCasilla(HttpServletResponse response, HttpServletRequest request, Long idCasilla) throws IOException {
//		try {
//
//			return IService.getVotaciones(idCasilla);
//			
//		} catch (CotException e) {
//			e.printStackTrace();
//			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
//			return null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
//			return null;
//		}
//	}
	
	
}
