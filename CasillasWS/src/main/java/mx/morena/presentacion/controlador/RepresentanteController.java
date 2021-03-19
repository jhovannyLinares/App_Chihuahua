package mx.morena.presentacion.controlador;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.exception.RepresentanteException;
import mx.morena.negocio.servicios.IRepresentanteService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "representantes")
public class RepresentanteController extends MasterController {
	
	@Autowired
	private IRepresentanteService representanteService;
	
	@PostMapping
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarRepresentanteFederal(HttpServletResponse response,HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) throws IOException {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepresentante(representanteDTO, perfil, usuario);
		} catch (RepresentanteException e) {
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
