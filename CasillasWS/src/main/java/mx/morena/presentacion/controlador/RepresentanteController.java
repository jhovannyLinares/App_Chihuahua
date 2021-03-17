package mx.morena.presentacion.controlador;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.exception.RepresentanteException;
import mx.morena.negocio.servicios.IRepresentanteService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "representantes")
public class RepresentanteController extends MasterController {
	
	@Autowired
	private IRepresentanteService representanteService;
	
	@PostMapping("/federal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarRepresentanteFederal(HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepFederal(representanteDTO, perfil, usuario);
		} catch (RepresentanteException ex) {
			throw new ResponseStatusException(HttpStatus.resolve(ex.getCodeError()), ex.getLocalizedMessage());
		}
		
	}
	
	@PostMapping("/local")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarRepresentanteLocal(HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepLocal(representanteDTO, perfil, usuario);
		} catch (RepresentanteException ex) {
			throw new ResponseStatusException(HttpStatus.resolve(ex.getCodeError()), ex.getLocalizedMessage());
		}
		
	}
	
	@PostMapping("/municipal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarRepresentanteMunicipal(HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepMunicipal(representanteDTO, perfil, usuario);
		} catch (RepresentanteException ex) {
			throw new ResponseStatusException(HttpStatus.resolve(ex.getCodeError()), ex.getLocalizedMessage());
		}
		
	}
	
	@PostMapping("/crg")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarRepresentanteCRG(HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepCRG(representanteDTO, perfil, usuario);
		} catch (RepresentanteException ex) {
			throw new ResponseStatusException(HttpStatus.resolve(ex.getCodeError()), ex.getLocalizedMessage());
		}
		
	}
	
	@PostMapping("/rg")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarRepresentanteRG(HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepRG(representanteDTO, perfil, usuario);
		} catch (RepresentanteException ex) {
			throw new ResponseStatusException(HttpStatus.resolve(ex.getCodeError()), ex.getLocalizedMessage());
		}
		
	}
	
	@PostMapping("/rc")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarRepresentanteRC(HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepRC(representanteDTO, perfil, usuario);
		} catch (RepresentanteException ex) {
			throw new ResponseStatusException(HttpStatus.resolve(ex.getCodeError()), ex.getLocalizedMessage());
		}
		
	}
	
}
