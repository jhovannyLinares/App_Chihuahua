package mx.morena.presentacion.controlador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.RutasDTO;

@RestController
@RequestMapping(value = "/")
public class RutasController {

	

	@PostMapping("/rutas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long saveRutas(HttpServletResponse response, HttpServletRequest request, @RequestBody RutasDTO dto) {
		return null;
		
	}
	
}
