package mx.morena.presentacion.controlador;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.AsignarSeccionesDTO;
import mx.morena.negocio.dto.CotDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.ICotService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "/")
public class CotController extends MasterController {

	@Autowired
	private ICotService cotService;

	@PostMapping("/cots")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarCots(HttpServletRequest request, @RequestBody CotDTO cot) {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return cotService.save(cot, perfil, usuario);
		} catch (CotException e) {
			throw new ResponseStatusException(HttpStatus.resolve(e.getCodeError()), e.getLocalizedMessage());
		}
	}

	@PostMapping("/cots/secciones")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String asignarSeccionesCots(HttpServletRequest request, @RequestBody AsignarSeccionesDTO secciones) {
		long perfil = getPerfil(request);

		try {
			return cotService.asignarSecciones(secciones.getIdSecciones(), secciones.getIdCot(), perfil);
		} catch (CotException e) {
			throw new ResponseStatusException(HttpStatus.resolve(e.getCodeError()), e.getLocalizedMessage());
		}
	}
	
	@PutMapping("/cots/{id}")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String suspender(HttpServletRequest request, @PathVariable("id") Long idCot) {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return cotService.suspender(idCot, perfil, usuario);
		} catch (CotException e) {
			throw new ResponseStatusException(HttpStatus.resolve(e.getCodeError()), e.getLocalizedMessage());
		}
	}

}
