package mx.morena.presentacion.controlador;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mx.morena.negocio.dto.ConvencidosDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IConvencidosService;

@RestController
//@RequestMapping(value = "/convencidos")
public class ConvencidosController {

	@Autowired
	private IConvencidosService convencidosService;

	
	/**
	 * servicio de busqueda por de convencidos por clave elector
	 * @param claveElector
	 * @return
	 */
	@GetMapping("/convencidos")
	public List<ConvencidosDTO> getConvencidos(@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "idMunicipio", required = false) Long idMunicipio,
			@RequestParam(value = "idSeccion", required = false) Long idSeccion,
			@RequestParam(value = "claveElector", required = false) String claveElector) {
		try {
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXxx    convencidos controller");
			List<ConvencidosDTO> convencidos = convencidosService.getConvencidos(idFederal, idMunicipio, idSeccion, claveElector);
			return convencidos;
		} catch (ConvencidosException e) {
			throw new ResponseStatusException(HttpStatus.resolve(e.getCodeError()), e.getLocalizedMessage());
		}
	}
	
	
	@PostMapping("/formConvencidos")
	public String saveConvencidos(@ModelAttribute ConvencidosDTO dto) {
		try {
		System.out.println("XXXXXXXXXXXXXXXXXXX   save convencidos controller");
		
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String fecha = "10/25/2020";
		
		ConvencidosDTO cdto = new ConvencidosDTO();
		cdto.setId(1L);
		cdto.setaMaterno("perez");
		cdto.setaPaterno("duran");
		cdto.setCalle("degollado");
		cdto.setClaveElector("pevt234432");
		cdto.setColonia("san juan");
		cdto.setCorreo("sdewa@sdsd.com");
		cdto.setCp("234432");
		//cdto.setFechaRegistro(df(fecha));
		cdto.setIdEstado(8L);
		cdto.setIdFederal(4L);
		cdto.setIdMunicipio(3L);
		cdto.setIdSeccion(2L);
		cdto.setNombre("esequiel");
		cdto.setTelCasa("43553435355");
		cdto.setTelCelular("534655354");
		cdto.setNumExterior("26");
		cdto.setNumInterior("10");
		
		return convencidosService.save(cdto);
		
		} catch (ConvencidosException e){
			throw new ResponseStatusException(HttpStatus.resolve(e.getCodeError()), e.getLocalizedMessage());
		}
		
	}


//	@GetMapping("claveElector/{claveElector}/convencidos")
//	private ConvencidosDTO getClaveElector(@PathVariable("claveElector") String claveElector) {
//		return convencidosService.getByClaveElector(claveElector);
//	}

}
