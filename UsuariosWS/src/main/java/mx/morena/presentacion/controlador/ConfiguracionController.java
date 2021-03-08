package mx.morena.presentacion.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mx.morena.negocio.dto.UsuarioRequest;
import mx.morena.negocio.exception.UsuarioException;
import mx.morena.negocio.servicios.IUsuarioService;

@RestController
@RequestMapping("configuracion")
public class ConfiguracionController {

	@Autowired
	private IUsuarioService usuarioService;

	@PutMapping("usuario")
	public Boolean UpdateUsuario(UsuarioRequest usuario) {

		try {
			return usuarioService.updatePwd(usuario);
		} catch (UsuarioException ex) {
			throw new ResponseStatusException(HttpStatus.resolve(ex.getCodeError()), ex.getLocalizedMessage());

		}

	}
}