package mx.morena.presentacion.controlador;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.EnvioActasDTO;
import mx.morena.negocio.dto.ResultadoOkDTO;
import mx.morena.negocio.dto.ResultadoVotacionDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.ICasillasService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "/casillas")
@CrossOrigin
public class CasillasController extends MasterController{

	@Autowired
	private ICasillasService casillaService;
	
	@PostMapping("/envioActas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarActas(HttpServletResponse response, HttpServletRequest request, @RequestBody EnvioActasDTO actas) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return casillaService.save(actas, perfil, usuario);
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
	
	@PostMapping("/resultados")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResultadoOkDTO saveResultados(HttpServletResponse response, HttpServletRequest request, @RequestBody ResultadoVotacionDTO actas) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return casillaService.saveResultados(actas, perfil, usuario);
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

	
}
