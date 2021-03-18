package mx.morena.presentacion.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.ConvencidosDTO;
import mx.morena.negocio.dto.ConvencidosResponseDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IConvencidosService;
import mx.morena.security.controller.MasterController;

@RestController
public class ConvencidosController extends MasterController {

	@Autowired
	private IConvencidosService convencidosService;

	/**
	 * servicio de busqueda por de convencidos por clave elector, distrito federal, municipio
	 * 
	 * @param claveElector
	 * @return
	 * @throws IOException 
	 */
	@GetMapping("/convencidos")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<ConvencidosResponseDTO> getConvencidos(HttpServletResponse response,
			@RequestParam(value = "idFederal", required = false) Long distritoFederalId,
			@RequestParam(value = "idMunicipio", required = false) Long idMunicipio,
			@RequestParam(value = "idSeccion", required = false) Long idSeccion,
			@RequestParam(value = "claveElector", required = false) String claveElector) throws IOException {
		try {
			List<ConvencidosResponseDTO> convencidos = convencidosService.getConvencidos(distritoFederalId, idMunicipio,
					idSeccion, claveElector);
			return convencidos;
		} catch (ConvencidosException e) {
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	/**
	 * Servicio de guardado de convencidos
	 * 
	 * @param request
	 * @param dto
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/convencidos")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long saveConvencidos(HttpServletResponse response,HttpServletRequest request, @RequestBody ConvencidosDTO dto) throws IOException {
		try {
			long usuario = getUsuario(request);

			return convencidosService.save(usuario, dto);

		} catch (ConvencidosException e) {
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}

	/**
	 * Servicio de validacion por clave elector
	 * @param claveElector
	 * @return
	 * @throws IOException 
	 */
	@GetMapping("claveElector/{claveElector}/convencidos")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private boolean getClaveElector(HttpServletResponse response,@PathVariable("claveElector") String claveElector) throws IOException {
		try {
			return convencidosService.findByClaveElector(claveElector);
		} catch (ConvencidosException e) {
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return false;
		} catch (Exception e ) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return false;
		}
	}

}
