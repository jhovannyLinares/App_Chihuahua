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
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.dto.LocalidadDTO;
import mx.morena.negocio.dto.MunicipioDTO;
import mx.morena.negocio.servicios.ICatalogoService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "catalogos")
public class CatalogoController extends MasterController {

	@Autowired
	ICatalogoService ICatService;

//	@GetMapping()
//	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
//	private CatalogoDTOOffline getCatalogos(HttpServletRequest request) {
//
//		return ICatService.getCatalogos(getUsuario(request), getPerfil(request));
//
//	}

	@GetMapping("/entidades")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<EntidadDTO> getEntidad() {

		return ICatService.getEntidades();

	}

//	
//	
//	
//
	@GetMapping("/entidades/{id}/distritosFederales")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<DistritoFederalDTO> getFederalByEntidad(HttpServletRequest request,
			@PathVariable("id") String idEntidad) {

		long usuario = getUsuario(request);
		long perfil = getPerfil(request);

		List<DistritoFederalDTO> federal = ICatService.getFederalByEntidad(usuario, perfil, idEntidad);

		return federal;

	}

//
	@GetMapping("/distritosFederales/{id}/municipios")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<MunicipioDTO> getLocalByFederal(HttpServletRequest request, @PathVariable("id") String idFederal) {

		long usuario = getUsuario(request);
		long idPerfil = getPerfil(request);

		List<MunicipioDTO> local = ICatService.getMunicipioByFederal(usuario, idPerfil, idFederal);

		return local;

	}

	@GetMapping("/municipios/{id}/localidades")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<LocalidadDTO> getLocalidadByMunicipio(HttpServletRequest request,
			@PathVariable("id") String idMunicipio) {

		long usuario = getUsuario(request);
		long idPerfil = getPerfil(request);

		List<LocalidadDTO> localidad = ICatService.getLocalidadByMunicipio(usuario, idPerfil, idMunicipio);

		return localidad;
	}
//
//	@GetMapping("/localidades/{id}/seccionesElectorales")
//	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
//	private List<SeccionDTO> getseccionByLocalidad(HttpServletRequest request, @PathVariable("id") String idLocalidad) {
//
//		long usuario = getUsuario(request);
//		long idPerfil = getPerfil(request);
//
//		List<SeccionDTO> seccion = ICatService.getSeccionByLocalidad(usuario, idPerfil, idLocalidad);
//
//		return seccion;
//	}
}
