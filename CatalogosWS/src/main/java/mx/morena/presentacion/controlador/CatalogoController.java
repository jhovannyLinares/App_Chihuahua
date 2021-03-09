package mx.morena.presentacion.controlador;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.DistritoLocalDTO;
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.dto.LocalidadDTO;
import mx.morena.negocio.dto.MunicipioDTO;
import mx.morena.negocio.dto.SeccionDTO;
import mx.morena.negocio.servicios.ICatalogoService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "catalogos")

public class CatalogoController extends MasterController {

	@Autowired
	ICatalogoService ICatService;

	@GetMapping("/entidades")
	private List<EntidadDTO> getEntidad() {
		System.out.println("hola");

		return ICatService.getEntidades();

	}

	@GetMapping("/entidades/{id}/distritosFederales")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<DistritoFederalDTO> getFederalByEntidad(HttpServletRequest request,
			@PathVariable("id") Long idEntidad) {

		long usuario = getUsuario(request);
		long perfil = getPerfil(request);

		List<DistritoFederalDTO> federal = ICatService.getFederalByEntidad(usuario, perfil, idEntidad);

		return federal;

	}

	@GetMapping("/distritosFederales/{id}/distritosLocales")
	private List<DistritoLocalDTO> getLocalByFederal(HttpServletRequest request, @PathVariable("id") Long idFederal) {

		long usuario = getUsuario(request);
		long idPerfil = getPerfil(request);

		List<DistritoLocalDTO> local = ICatService.getLocalByFederal(usuario, idPerfil, idFederal);

		return local;

	}

	@GetMapping("/distritosLocales/{id}/municipios")
	private List<MunicipioDTO> getMunicipioByLocal(HttpServletRequest request, @PathVariable("id") Long idLocal) {

		long usuario = getUsuario(request);
		long idPerfil = getPerfil(request);

		List<MunicipioDTO> municipio = ICatService.getMunicipioByLocal(usuario, idPerfil, idLocal);

		return municipio;

	}

	@GetMapping("/municipios/{id}/localidades")
	private List<LocalidadDTO> getLocalidadByMunicipio(HttpServletRequest request,
			@PathVariable("id") Long idMunicipio) {

		long usuario = getUsuario(request);
		long idPerfil = getPerfil(request);

		List<LocalidadDTO> localidad = ICatService.getLocalidadByMunicipio(usuario, idPerfil, idMunicipio);

		return localidad;
	}

	@GetMapping("/localidades/{id}/seccionesElectorales")
	private List<SeccionDTO> getseccionByLocalidad(HttpServletRequest request, @PathVariable("id") Long idLocalidad) {

		long usuario = getUsuario(request);
		long idPerfil = getPerfil(request);

		List<SeccionDTO> seccion = ICatService.getSeccionByLocalidad(usuario, idPerfil, idLocalidad);

		return seccion;
	}
}
