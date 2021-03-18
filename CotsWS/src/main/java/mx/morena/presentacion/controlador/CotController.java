package mx.morena.presentacion.controlador;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.AsignarSeccionesDTO;
import mx.morena.negocio.dto.CotDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.ICotService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "/")
public class CotController extends MasterController {

	@Autowired
	private ICotService cotService;

	@PostMapping("/cots")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarCots(HttpServletResponse response,HttpServletRequest request, @RequestBody CotDTO cot) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return cotService.save(cot, perfil, usuario);
		} catch (CotException e) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return null;
		} catch (Exception e ) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	@PostMapping("/cots/secciones")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String asignarSeccionesCots(HttpServletResponse response,HttpServletRequest request, @RequestBody AsignarSeccionesDTO secciones) throws IOException {
		long perfil = getPerfil(request);

		try {
			return cotService.asignarSecciones(secciones.getIdSecciones(), secciones.getIdCot(), perfil);
		} catch (CotException e) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return null;
		} catch (Exception e ) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@PutMapping("/cots/{id}")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String activar(HttpServletResponse response,HttpServletRequest request, @PathVariable("id") Long idCot) throws IOException {
		long perfil = getPerfil(request);

		try {
			return cotService.activar(idCot, perfil);
		} catch (CotException e) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return null;
		} catch (Exception e ) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@DeleteMapping("/cots/{id}")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String suspender(HttpServletResponse response,HttpServletRequest request, @PathVariable("id") Long idCot) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return cotService.suspender(idCot, perfil, usuario);
		} catch (CotException e) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return null;
		} catch (Exception e ) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

}
