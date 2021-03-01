package mx.morena.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaService {

	@GetMapping("/hola")
	public String hola(HttpServletRequest request) {

		System.out.println(request.getSession().getAttribute("Perfil"));
		
		return "hola ";

	}

}
