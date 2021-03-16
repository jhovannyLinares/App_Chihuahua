package mx.morena.presentacion.controlador;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	 */
	@GetMapping("/convencidos")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<ConvencidosResponseDTO> getConvencidos(
			@RequestParam(value = "idFederal", required = false) Long distritoFederalId,
			@RequestParam(value = "idMunicipio", required = false) Long idMunicipio,
			@RequestParam(value = "idSeccion", required = false) Long idSeccion,
			@RequestParam(value = "claveElector", required = false) String claveElector) {
		try {
			List<ConvencidosResponseDTO> convencidos = convencidosService.getConvencidos(distritoFederalId, idMunicipio,
					idSeccion, claveElector);
			return convencidos;
		} catch (ConvencidosException e) {
			throw new ResponseStatusException(HttpStatus.resolve(e.getCodeError()), e.getLocalizedMessage());
		}
	}

	/**
	 * Servicio de guardado de convencidos
	 * 
	 * @param request
	 * @param dto
	 * @return
	 */
	@PostMapping("/convencidos")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long saveConvencidos(HttpServletRequest request, @RequestBody ConvencidosDTO dto) {
		try {
			long usuario = getUsuario(request);

			return convencidosService.save(usuario, dto);

		} catch (ConvencidosException e) {
			
			throw new ResponseStatusException(HttpStatus.resolve(e.getCodeError()), e.getLocalizedMessage());
		}

	}

	/**
	 * Servicio de validacion por clave elector
	 * @param claveElector
	 * @return
	 */
	@GetMapping("claveElector/{claveElector}/convencidos")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private boolean getClaveElector(@PathVariable("claveElector") String claveElector) {
		try {
			return convencidosService.findByClaveElector(claveElector);
		} catch (ConvencidosException e) {
			throw new ResponseStatusException(HttpStatus.resolve(e.getCodeError()), e.getLocalizedMessage());
		}
	}

}
