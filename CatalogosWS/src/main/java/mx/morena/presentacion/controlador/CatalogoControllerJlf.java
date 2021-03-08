package mx.morena.presentacion.controlador;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.servicios.ICatalogoService;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.DistritoLocal;
import mx.morena.persistencia.entidad.Localidad;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "catalog")
public class CatalogoControllerJlf extends MasterController {

	@Autowired
	ICatalogoService ICatService;

	@GetMapping("/entidades")
	private List<EntidadDTO> getEntidad() {

		return ICatService.getEntidades();

	}

	@GetMapping("/entidades/{id}/distritosFederales")
	private List<DistritoFederalDTO> getFederalByEntidad(HttpServletRequest request, @PathVariable("id") Long idEntidad) {

		long usuario = getUsuario(request);
		long perfil = getPerfil(request);

		List<DistritoFederalDTO> federal = ICatService.getFederalByEntidad(usuario, perfil, idEntidad);

		return federal;

	}


}