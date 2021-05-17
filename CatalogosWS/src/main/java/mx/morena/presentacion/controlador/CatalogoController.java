package mx.morena.presentacion.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.CasillaDTO;
import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.EleccionDTO;
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.dto.IncidenciaDTO;
import mx.morena.negocio.dto.MotivosTerminoVotacionDTO;
import mx.morena.negocio.dto.MunicipioDTO;
import mx.morena.negocio.dto.RepresentacionPartidosDTO;
import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.dto.SeccionDTO;
import mx.morena.negocio.dto.SeccionUserDTO;
import mx.morena.negocio.dto.TipoActasDTO;
import mx.morena.negocio.dto.offline.CatalogoDTOOffline;
import mx.morena.negocio.exception.CatalogoException;
import mx.morena.negocio.servicios.ICatalogoService;
import mx.morena.negocio.servicios.impl.CargoDTO;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "catalogos")
@CrossOrigin
public class CatalogoController extends MasterController {

	@Autowired
	ICatalogoService ICatService;

	@GetMapping()
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private CatalogoDTOOffline getCatalogos(HttpServletRequest request) throws CatalogoException {

		return ICatService.getCatalogos(getUsuario(request), getPerfil(request));

	}

	@GetMapping("/representantes")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<RepresentanteDTO> getRepresentantes(HttpServletRequest request) {

		return ICatService.getRepresentantes(getPerfil(request));

	}

	@GetMapping("/{tipoRepresentante}/cargos")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<CargoDTO> getCargos(HttpServletResponse response, HttpServletRequest request,
			@PathVariable("tipoRepresentante") Long tipoRepresentante) throws IOException {
		try {
			return ICatService.getCargos(tipoRepresentante);
		} catch (CatalogoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}

	@GetMapping("/casillas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<CasillaDTO> getCasillas(HttpServletRequest request,
			@RequestParam(value = "distritoFederalId", required = false) Long distritoFederalId,
			@RequestParam(value = "municipioId", required = false) Long municipioId,
			@RequestParam(value = "seccionId", required = false) Long seccionId,
			@RequestParam(value = "isLibres", required = false) Boolean isLibres) {

		return ICatService.getCasillas(getUsuario(request), getPerfil(request), distritoFederalId, municipioId,
				seccionId, isLibres);

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
	
	@GetMapping("/entidades/{id}/distritosLocales")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<DistritoFederalDTO> getLocalByEntidad(HttpServletRequest request,
			@PathVariable("id") Long idEntidad) {

		long usuario = getUsuario(request);
		long perfil = getPerfil(request);

		List<DistritoFederalDTO> federal = ICatService.getLocalByEntidad(usuario, perfil, idEntidad);

		return federal;

	}

	@GetMapping("/distritosFederales/{id}/municipios")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<MunicipioDTO> getLocalByFederal(HttpServletRequest request, @PathVariable("id") Long idFederal) throws CatalogoException {

		long usuario = getUsuario(request);
		long idPerfil = getPerfil(request);

		List<MunicipioDTO> local = ICatService.getMunicipioByFederal(usuario, idPerfil, idFederal);
		return local;
	}

	@GetMapping("/municipios/{id}/secciones")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<SeccionDTO> getLocalidadByMunicipio(HttpServletRequest request, @PathVariable("id") Long idMunicipio) {

		long usuario = getUsuario(request);
		long idPerfil = getPerfil(request);

		List<SeccionDTO> secciones = ICatService.getSeccionesByMunicipio(usuario, idPerfil, idMunicipio);

		return secciones;
	}

//	@GetMapping("/distritosFederales/{idFederal}/zonas")
//	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
//	private List<ZonaDTO> getZonas(HttpServletRequest request, @PathVariable("idFederal") Long idFederal) throws CatalogoException {
//
//		long usuario = getUsuario(request);
//		long idPerfil = getPerfil(request);
//		
//		
//		List<ZonaDTO> dto;
//
//		dto = ICatService.getZonas(usuario, idPerfil, idFederal);
//
//		return dto;
//	}

	@GetMapping("/incidencias")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<IncidenciaDTO> getIncidencias(HttpServletResponse response, HttpServletRequest request) throws IOException{
		
		try {
			return ICatService.getIncidencias();
		} catch (CatalogoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/eleccion")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<EleccionDTO> getEleccion(HttpServletResponse response, HttpServletRequest request) throws IOException{
		
		try {
			return ICatService.getEleccion();
		} catch (CatalogoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	/* ICJ - Generacion del catalogo para mostrar las secciones de acuerdo al usuario brigadista*/
	
	@GetMapping("/seccion")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<SeccionUserDTO> getSeccion(HttpServletResponse response, HttpServletRequest request) throws IOException{
		
		long usuario = getUsuario(request);
		
		try {
			return ICatService.getSeccionByBrigadista(usuario);
		} catch (CatalogoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/tipoActas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<TipoActasDTO> getTipoActas(HttpServletResponse response, HttpServletRequest request) throws IOException{
		
		long idPerfil = getPerfil(request);
		
		try {
			return ICatService.getTipoActas(idPerfil);
		} catch (CatalogoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/representantePartidos")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<RepresentacionPartidosDTO> getRepresentacionPartidos(HttpServletResponse response, HttpServletRequest request) throws IOException{
		
		try {
			return ICatService.getRepresentacionPartidos();
		} catch (CatalogoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/motivos")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<MotivosTerminoVotacionDTO> getMotivosTermino(HttpServletResponse response, HttpServletRequest request) throws IOException{
		
		try {
			return ICatService.getMotivos();
		} catch (CatalogoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
}
