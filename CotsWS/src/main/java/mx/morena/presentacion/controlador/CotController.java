package mx.morena.presentacion.controlador;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mx.morena.negocio.dto.AsignarSeccionesDTO;
import mx.morena.negocio.dto.CotDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.ICotService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "/")
public class CotController extends MasterController{
	
	@Autowired
	private ICotService cotService;
	
	@PostMapping("/cots")
	public String guardarCots(HttpServletRequest request, @RequestBody CotDTO cot) {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);
		
		try {
			return cotService.save(cot, perfil, usuario);
		} catch (CotException e) {
			throw new ResponseStatusException(HttpStatus.resolve(e.getCodeError()), e.getLocalizedMessage());
		}
	}
	
	@PostMapping("/cots/secciones")
	public String asignarSeccionesCots(HttpServletRequest request, @RequestBody AsignarSeccionesDTO secciones) {
		long perfil = getPerfil(request);
		
		try {
			return cotService.asignarSecciones(secciones.getIdSecciones(), secciones.getIdCot(), perfil);
		} catch (CotException e) {
			throw new ResponseStatusException(HttpStatus.resolve(e.getCodeError()), e.getLocalizedMessage());
		}
	}
	
}
