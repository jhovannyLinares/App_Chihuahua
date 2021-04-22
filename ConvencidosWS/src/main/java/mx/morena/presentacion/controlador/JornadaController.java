package mx.morena.presentacion.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.CapacitacionDTO;
import mx.morena.negocio.dto.RegistroCapacitacionDTO;
import mx.morena.negocio.exception.JornadaException;
import mx.morena.negocio.servicio.IJornadaService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "seguimientoJornada")
@CrossOrigin
public class JornadaController extends MasterController{
	
	@Autowired
	private IJornadaService jornadaService;

	@GetMapping("/capacitacion")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<CapacitacionDTO> getBusqueda(HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value = "claveElector", required = false) String claveElector,
			@RequestParam(value = "rc", required = true) boolean rc,
			@RequestParam(value = "rg", required = true) boolean rg) throws IOException {
		
		try {
			return jornadaService.getRepresentantes(claveElector, rc, rg);
		}catch (JornadaException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
		
	}
	
	@PostMapping("/capacitacion")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String saveCapacitacion(HttpServletResponse response, HttpServletRequest request, @RequestBody RegistroCapacitacionDTO dto) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return jornadaService.saveCapacitacion(dto, perfil, usuario);
		} catch (JornadaException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@PutMapping("/nombramiento")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String entregaNombramiento(HttpServletResponse response, HttpServletRequest request, @RequestBody CapacitacionDTO dto) throws IOException {
		long usuario = getUsuario(request);
		long perfil = getPerfil(request);

		try {
			return jornadaService.updateNombramiento(usuario, dto, perfil);
		} catch (JornadaException e) {
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
