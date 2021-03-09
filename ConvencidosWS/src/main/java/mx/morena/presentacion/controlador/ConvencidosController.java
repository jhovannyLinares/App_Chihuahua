package mx.morena.presentacion.controlador;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mx.morena.negocio.dto.ConvencidosDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IConvencidosService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.Entidad;
import mx.morena.security.controller.MasterController;

@RestController
//@RequestMapping(value = "/convencidos")
public class ConvencidosController extends MasterController{

	@Autowired
	private IConvencidosService convencidosService;

	
	/**
	 * servicio de busqueda por de convencidos por clave elector
	 * @param claveElector
	 * @return
	 */
	@GetMapping("/convencidos")
	public List<ConvencidosDTO> getConvencidos(@RequestParam(value = "distrito_federal_id", required = false) Long distritoFederalId,
			@RequestParam(value = "idMunicipio", required = false) Long idMunicipio,
			@RequestParam(value = "idSeccion", required = false) Long idSeccion,
			@RequestParam(value = "claveElector", required = false) String claveElector) {
		try {
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXxx    convencidos controller");
			List<ConvencidosDTO> convencidos = convencidosService.getConvencidos(distritoFederalId, idMunicipio, idSeccion, claveElector);
			return convencidos;
		} catch (ConvencidosException e) {
			throw new ResponseStatusException(HttpStatus.resolve(e.getCodeError()), e.getLocalizedMessage());
		}
	}
		
	/**
	 * Servicio de guardado de convencidos
	 * @param request
	 * @param dto
	 * @return
	 */
	@PostMapping("/convencidos")
	public Long saveConvencidos(HttpServletRequest request, @RequestBody ConvencidosDTO dto) {
		try {
		System.out.println("XXXXXXXXXXXXXXXXXXX   save convencidos controller");
		
		long usuario = getUsuario(request);

		return convencidosService.save(usuario,dto);
		
		} catch (ConvencidosException e){
			throw new ResponseStatusException(HttpStatus.resolve(e.getCodeError()), e.getLocalizedMessage());
		}
		
	}






}
