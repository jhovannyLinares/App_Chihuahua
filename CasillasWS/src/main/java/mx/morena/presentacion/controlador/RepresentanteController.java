package mx.morena.presentacion.controlador;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public Long guardarRepresentanteFederal(HttpServletResponse response,HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) throws IOException {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepFederal(representanteDTO, perfil, usuario);
		} catch (RepresentanteException e) {
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@PostMapping("/local")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarRepresentanteLocal(HttpServletResponse response,HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) throws IOException {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepLocal(representanteDTO, perfil, usuario);
		} catch (RepresentanteException e) {
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
		
	}
	
	@PostMapping("/municipal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarRepresentanteMunicipal(HttpServletResponse response,HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) throws IOException {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepMunicipal(representanteDTO, perfil, usuario);
		} catch (RepresentanteException e) {
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@PostMapping("/crg")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarRepresentanteCRG(HttpServletResponse response,HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) throws IOException {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepCRG(representanteDTO, perfil, usuario);
		} catch (RepresentanteException e) {
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
		
	}
	
	@PostMapping("/rg")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarRepresentanteRG(HttpServletResponse response,HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) throws IOException {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepRG(representanteDTO, perfil, usuario);
		} catch (RepresentanteException e) {
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@PostMapping("/rc")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarRepresentanteRC(HttpServletResponse response,HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) throws IOException {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepRC(representanteDTO, perfil, usuario);
		} catch (RepresentanteException e) {
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
		
	}
	
}
