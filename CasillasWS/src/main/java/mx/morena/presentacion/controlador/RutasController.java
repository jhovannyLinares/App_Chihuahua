package mx.morena.presentacion.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.AsignarRutasDTO;
import mx.morena.negocio.dto.CatalogoCrgDTO;
import mx.morena.negocio.exception.RutasException;
import mx.morena.negocio.servicios.IRutasService;
import mx.morena.security.controller.MasterController;
import mx.morena.negocio.dto.RutasResponseDTO;

@RestController
@RequestMapping(value = "/")
public class RutasController extends MasterController {

	@Autowired
	private IRutasService rutasService;

	@GetMapping("/rutas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<RutasResponseDTO> getRutas(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "", required = false) Long zonaCRG,
			@RequestParam(value = "", required = false) Long ruta,
			@RequestParam(value = "", required = false) Long casilla) throws IOException {

		try {
			long perfil = getPerfil(request);
			return rutasService.getRutas(idFederal, zonaCRG, ruta, casilla, perfil);
		} catch (RutasException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@PostMapping("/crg/rutas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String asignarSeccionesCots(HttpServletResponse response, HttpServletRequest request, @RequestBody AsignarRutasDTO rutas) throws IOException {
		long perfil = getPerfil(request);

		try {
			return rutasService.asignarRutas(rutas.getIdRuta(), rutas.getIsCrg(), perfil);
		} catch (RutasException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("catalogo/crg")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<CatalogoCrgDTO> getCatalogoCrg(HttpServletResponse response, HttpServletRequest request) throws IOException {

		long perfil = getPerfil(request);
		
		try {
			return rutasService.getCatalogo(perfil);
		}catch (RutasException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}
}
