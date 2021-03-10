package mx.morena.presentacion.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mx.morena.negocio.dto.UserLogin;
import mx.morena.negocio.exception.UsuarioException;
import mx.morena.negocio.servicios.IUsuarioService;
import mx.morena.security.dto.UsuarioDTO;

@RestController
@RequestMapping("usuario")
@CrossOrigin
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	@CrossOrigin
	@PostMapping("/login")
	public UsuarioDTO login(@RequestBody UserLogin user) {
		try {
			return usuarioService.login(user.getUsuario(), user.getPassword());
		} catch (UsuarioException ex) {
			throw new ResponseStatusException(HttpStatus.resolve(ex.getCodeError()), ex.getLocalizedMessage());

		}

	}

}
