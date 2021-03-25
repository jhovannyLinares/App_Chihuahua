package mx.morena.presentacion.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.AsignarSeccionesDTO;
import mx.morena.negocio.dto.CotDTO;
import mx.morena.negocio.dto.CotResponseDTO;
import mx.morena.negocio.dto.SeccionDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.ICotService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class CotController extends MasterController {

	@Autowired
	private ICotService cotService;

	@PostMapping("/cots")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarCots(HttpServletResponse response, HttpServletRequest request, @RequestBody CotDTO cot) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return cotService.save(cot, perfil, usuario);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	@PostMapping("/cots/secciones")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String asignarSeccionesCots(HttpServletResponse response, HttpServletRequest request, @RequestBody AsignarSeccionesDTO secciones) throws IOException {
		long perfil = getPerfil(request);

		try {
			return cotService.asignarSecciones(secciones.getIdSecciones(), secciones.getIdCot(), perfil);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@PutMapping("/cots/{id}")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String activar(HttpServletResponse response, HttpServletRequest request, @PathVariable("id") Long idCot) throws IOException {
		long perfil = getPerfil(request);

		try {
			return cotService.activar(idCot, perfil);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@DeleteMapping("/cots/{id}")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String suspender(HttpServletResponse response, HttpServletRequest request, @PathVariable("id") Long idCot) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return cotService.suspender(idCot, perfil, usuario);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/cots")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<CotResponseDTO> getCots(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idFederal") Long idDistritoFederal,
			@RequestParam(value = "idMunicipio", required = false) Long idMunicipio) throws IOException {
		long perfil = getPerfil(request);
		
		try {
			return cotService.getCots(perfil, idDistritoFederal, idMunicipio);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/cots/secciones/{idMunicipio}")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<SeccionDTO> getSeccionesLibres(HttpServletResponse response, HttpServletRequest request,
			@PathVariable("idMunicipio") Long idMunicipio) throws IOException {
		long perfil = getPerfil(request);
		
		try {
			return cotService.seccionesSinAsignar(perfil, idMunicipio);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@PatchMapping("/cots/{id}")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String update(HttpServletResponse response, HttpServletRequest request, @RequestBody CotDTO cot, @PathVariable("id") Long id) throws IOException {
		long perfil = getPerfil(request);
		
		try {
			return cotService.update(cot, perfil, id);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/cots/{curp}")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private boolean getClaveElector(HttpServletResponse response, @PathVariable("curp") String curp) throws IOException {
		try {
			return cotService.getByCurp(curp);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return false;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return false;
		}
	}

}
