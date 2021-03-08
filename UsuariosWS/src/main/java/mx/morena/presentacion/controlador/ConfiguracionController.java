package mx.morena.presentacion.controlador;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	public Boolean UpdateUsuario(HttpServletRequest request, UsuarioRequest usuario) {

		try {

			long idUsuario = getUsuario(request);

			return usuarioService.updatePwd(idUsuario, usuario);
			
		} catch (UsuarioException ex) {
			throw new ResponseStatusException(HttpStatus.resolve(ex.getCodeError()), ex.getLocalizedMessage());

		}

	}
}