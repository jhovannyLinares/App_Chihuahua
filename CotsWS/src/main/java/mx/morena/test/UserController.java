package mx.morena.test;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.morena.security.dto.User;
import mx.morena.security.servicio.Token;

@RestController
public class UserController {

	@PostMapping("user")
	public User login(@RequestParam("user") String username, @RequestParam("password") String pwd) {

		String token = Token.create(username);
		User user = new User();

		user.setUser(username);
		user.setToken(token);
		return user;

	}

}