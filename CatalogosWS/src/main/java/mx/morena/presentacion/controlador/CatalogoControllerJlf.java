package mx.morena.presentacion.controlador;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.servicios.ICatalogoService;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.DistritoLocal;
import mx.morena.persistencia.entidad.Localidad;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.SeccionElectoral;

@RestController
@RequestMapping(value = "catalogos")
public class CatalogoControllerJlf {

	@Autowired
	ICatalogoService ICatService;

	@GetMapping("/entidades")
	private List<EntidadDTO> getEntidad() {

		return ICatService.getEntidades();

	}

	@GetMapping("/entidades/{id}/distritosFederales")
	private List<DistritoFederal> getFederalByEntidad(HttpServletResponse response, @PathVariable("id") Long id) { //

		List<DistritoFederal> federal = ICatService.getFederalByEntidad(response, id);

		return federal;

	}

	@GetMapping("/distritosFederales/{id}/distritosLocales")
	private List<DistritoLocal> getLocalByFederal(HttpServletResponse response, @PathVariable("id") Long id) {

		List<DistritoLocal> local = ICatService.getLocalByFederal(response, id);

		return local;

	}

	@GetMapping("/distritosLocales/{id}/municipios")
	private List<Municipio> getMunicipioByLocal(HttpServletResponse response, @PathVariable("id") Long id) {

		List<Municipio> municipio = ICatService.getMunicipioByLocal(response, id);

		return municipio;

	}

	@GetMapping("/municipios/{id}/localidades")
	private List<Localidad> getLocalidadByMunicipio(HttpServletResponse response, @PathVariable("id") Long id) {

		List<Localidad> localidad = ICatService.getLocalidadByMunicipio(response, id);

		return localidad;
	}

	@GetMapping("/localidades/{id}/seccionesElectorales")
	private List<SeccionElectoral> getseccionByLocalidad(HttpServletResponse response, @PathVariable("id") Long id) {

		List<SeccionElectoral> seccion = ICatService.getSeccionByLocalidad(response, id);

		return seccion;
	}
}
