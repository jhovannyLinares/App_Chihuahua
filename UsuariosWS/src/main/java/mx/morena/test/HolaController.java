package mx.morena.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.morena.security.controller.MasterController;
import mx.morena.security.dto.Usuario;

@RestController
public class HolaController extends MasterController {

	@GetMapping("/hola")
	public String hola(HttpServletRequest request) {

		String perfil = getPerfil(request);

		return "hola " + perfil;

	}

//	@PatchMapping("/usuario")
//	public String updateDireccion(Usuario usuario) {
//		updateDireccion(usuario);
//		
//		usuarioBd = Repository.getusario();
//		
//		if (usuario.getNombre != usuarioBd.getNombre) {}
//		
//		if (usuario.getDireccion != usuarioBd.getdireccion) {}
//		
//	}
//	
//	
//	@PutMapping("/usuario")
//	public String updateUsuario(HttpServletResponse response, Usuario usuario) {
//		try {
//		updateUsuario(usuario);
//		}catch(ExceptionNotfoundUsuario ex) {
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST,"No se pudo actualizar el usuario");
//		}
//	}	

}
