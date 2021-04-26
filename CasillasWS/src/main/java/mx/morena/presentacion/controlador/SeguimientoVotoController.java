package mx.morena.presentacion.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.SeguimientoVotoDTO;
import mx.morena.negocio.exception.SeguimientoVotoException;
import mx.morena.negocio.servicios.IReporteSeguimientoVotoService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class SeguimientoVotoController extends MasterController {
	
	@Autowired
	private IReporteSeguimientoVotoService seguimientoVotoService;
	
	@GetMapping("/seguimientoVoto")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<SeguimientoVotoDTO> getCasillaBySeccion(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idSeccion", required = false) Long idSeccion) throws IOException {

		try {
			long perfil = getPerfil(request);
			return seguimientoVotoService.getCasillaBySeccion( perfil, idSeccion );
		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
}
