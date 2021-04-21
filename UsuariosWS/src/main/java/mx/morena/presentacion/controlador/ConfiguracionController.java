package mx.morena.presentacion.controlador;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.UsuarioRequest;
import mx.morena.negocio.exception.UsuarioException;
import mx.morena.negocio.servicios.IUsuarioService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping("configuracion")
public class ConfiguracionController extends MasterController {

	@Autowired
	private IUsuarioService usuarioService;

	@PutMapping("usuario")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Boolean UpdateUsuario(HttpServletResponse response,HttpServletRequest request,@RequestBody UsuarioRequest usuario) throws IOException {

		try {

			long idUsuario = getUsuario(request);

			return usuarioService.updatePwd(idUsuario, usuario);

		} catch (UsuarioException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}
	
	@GetMapping("/claveElectoral/{claveElector}")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public boolean getRepresentante(HttpServletResponse response,HttpServletRequest request, @PathVariable("claveElector") String claveElector) throws IOException {//@RequestBody UserLogin user
		
		long usuario = getUsuario(request);
		long idPerfil = getPerfil(request);
		
		try {
			return usuarioService.getRepresentante(idPerfil, usuario, claveElector);
		}catch (UsuarioException e) {
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