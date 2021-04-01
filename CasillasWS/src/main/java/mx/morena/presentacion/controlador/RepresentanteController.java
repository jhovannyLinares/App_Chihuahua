package mx.morena.presentacion.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.AsignacionRepresentantesDTO;
import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.dto.RepresentantesClaveDTO;
import mx.morena.negocio.dto.TipoRepDTO;
import mx.morena.negocio.exception.RepresentanteException;
import mx.morena.negocio.servicios.IRepresentanteService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "representantes")
@CrossOrigin
public class RepresentanteController extends MasterController {
	
	@Autowired
	private IRepresentanteService representanteService;
	
	@PostMapping
	@CrossOrigin
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarRepresentanteFederal(HttpServletResponse response,HttpServletRequest request, @RequestBody RepresentanteDTO representanteDTO) throws IOException {
		
		try {
			long perfil = getPerfil(request);
			long usuario = getUsuario(request);
			
			return representanteService.saveRepresentante(representanteDTO, perfil, usuario);
		} catch (RepresentanteException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/tipos")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	@CrossOrigin
	public List<TipoRepDTO> getTipoRep(HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		long perfil = getPerfil(request);
			
			return representanteService.getAllTipo();
			
	}
	
	@GetMapping("/claveElectoral")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	@CrossOrigin
	public List<RepresentantesClaveDTO> getAllRepresentantes(HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value = "claveElector", required = false) String claveElector,
			@RequestParam(value = "sinClaveElector", required = true) boolean sinClaveElector) throws IOException {
		
		try {
			return representanteService.getAllRepresentantes(claveElector, sinClaveElector);
		}catch (RepresentanteException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
		
	}
	@PostMapping("/asignacion")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	@CrossOrigin
	public Long asignaRepresentante(HttpServletResponse response,HttpServletRequest request, @RequestBody AsignacionRepresentantesDTO dto) throws IOException {
		
		try {
			long usuario = getUsuario(request);
			long perfil = getPerfil(request);

			return representanteService.asignaRepresentante(usuario, perfil, dto);

		} catch (RepresentanteException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
}
