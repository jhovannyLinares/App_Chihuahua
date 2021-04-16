package mx.morena.presentacion.controlador;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.InstalacionCasillasDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.IInstalacionCasillaService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class InstalacionCasillaController extends MasterController {
	
	@Autowired
	private IInstalacionCasillaService IService;
	
	@PostMapping("/Casillas")
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

}
