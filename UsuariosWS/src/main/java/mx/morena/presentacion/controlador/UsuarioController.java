package mx.morena.presentacion.controlador;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public UsuarioDTO login(HttpServletResponse response,@RequestBody UserLogin user) throws IOException {
		try {
			return usuarioService.login(user.getUsuario(), user.getPassword());
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

}
