package mx.morena.presentacion.controlador;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mx.morena.negocio.dto.UsuarioRequest;
import mx.morena.negocio.exception.UsuarioException;
import mx.morena.negocio.servicios.IUsuarioService;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.security.dto.UsuarioDTO;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	@PostMapping("login")
	public UsuarioDTO login(HttpServletResponse response, @RequestParam("user") String username,
			@RequestParam("password") String pwd) {
		try {
			return usuarioService.login(username, pwd);
		} catch (UsuarioException ex) {
			throw new ResponseStatusException(HttpStatus.resolve(ex.getCodeError()), ex.getLocalizedMessage());

		}
//		 try {
//		        return actorService.getActor(id);
//		    } catch (ActorNotFoundException ex) {
//		        throw new ResponseStatusException(
//		          HttpStatus.NOT_FOUND, "Actor Not Found", ex);
//		    }

	}

	@PostMapping("registro")
	public void Registro(UsuarioRequest usuario) {

	}

	@PutMapping
	public void UpdateUsuario(Usuario usuario) {

	}

}