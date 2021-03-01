package mx.morena.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.morena.security.dto.Modulo;
import mx.morena.security.dto.Usuario;
import mx.morena.security.servicio.Token;

@RestController
public class UserController {

	@PostMapping("user")
	public Usuario login(@RequestParam("user") String username, @RequestParam("password") String pwd) {

		String token = Token.create(username);
		Usuario user = new Usuario();

		user.setUsuario(username);
		
		List<Modulo> modulos = new ArrayList<>();
		
		Modulo modulo = new Modulo();
		
		modulo.setDescripcion("Modulo prueba");
		modulo.setURI("/ModuloPrueba");
		modulos.add(modulo);
		
		user.setModulos(modulos);
		user.setToken(token);
		user.setPerfil("Prueba");
		
		return user;

	} 

}