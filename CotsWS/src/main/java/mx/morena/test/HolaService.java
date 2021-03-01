package mx.morena.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaService {
	
	@GetMapping("/hola")
	public String hola() {

		return "hola";
	}
	
}






