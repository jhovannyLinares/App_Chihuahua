package mx.morena.presentacion.controlador;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.CasillaDTO;
import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.dto.LocalidadDTO;
import mx.morena.negocio.dto.MunicipioDTO;
import mx.morena.negocio.dto.offline.CatalogoDTOOffline;
import mx.morena.negocio.servicios.ICatalogoService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "catalogos")
public class CatalogoController extends MasterController {

	@Autowired
	ICatalogoService ICatService;

	@GetMapping()
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private CatalogoDTOOffline getCatalogos(HttpServletRequest request) {

		return ICatService.getCatalogos(getUsuario(request), getPerfil(request));

	}
	
	@GetMapping("/representantes")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private Map<Byte,String> getCasillas(HttpServletRequest request){

		return ICatService.getRepresentantes( getPerfil(request));

	}
	
	@GetMapping("/casillas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<CasillaDTO> getCasillas(HttpServletRequest request,
			@RequestParam(value = "distritoFederalId", required = false) Long distritoFederalId,
			@RequestParam(value = "municipioId", required = false) Long municipioId,
			@RequestParam(value = "seccionId", required = false) Long seccionId) {

		return ICatService.getCasillas(getUsuario(request), getPerfil(request),distritoFederalId,municipioId,seccionId);

	}

	@GetMapping("/entidades")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<EntidadDTO> getEntidad() {

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

	@GetMapping("/distritosFederales/{id}/municipios")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<MunicipioDTO> getLocalByFederal(HttpServletRequest request, @PathVariable("id") Long idFederal) {

		long usuario = getUsuario(request);
		long idPerfil = getPerfil(request);

		List<MunicipioDTO> local = ICatService.getMunicipioByFederal(usuario, idPerfil, idFederal);

		return local;

	}

	@GetMapping("/municipios/{id}/secciones")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<LocalidadDTO> getLocalidadByMunicipio(HttpServletRequest request,
			@PathVariable("id") Long idMunicipio) {

		long usuario = getUsuario(request);
		long idPerfil = getPerfil(request);

		List<LocalidadDTO> localidad = ICatService.getLocalidadByMunicipio(usuario, idPerfil, idMunicipio);

		return localidad;
	}

}
