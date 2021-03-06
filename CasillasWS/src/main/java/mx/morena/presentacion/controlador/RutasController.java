package mx.morena.presentacion.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.AsignarCasillasDTO;
import mx.morena.negocio.dto.AsignarRutasDTO;
import mx.morena.negocio.dto.CasillasCatalogoDto;
import mx.morena.negocio.dto.CatalogoCrgDTO;
import mx.morena.negocio.dto.DesasignarCasillasDTO;
import mx.morena.negocio.dto.RutaCatalogoDto;
import mx.morena.negocio.dto.RutaResponseDTO;
import mx.morena.negocio.dto.SeccionDTO;
import mx.morena.negocio.exception.RutasException;
import mx.morena.negocio.servicios.IRutasService;
import mx.morena.security.controller.MasterController;
import mx.morena.negocio.dto.ZonaCrgDTO;

@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class RutasController extends MasterController {

	@Autowired
	private IRutasService rutasService;

	@GetMapping("/rutas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<RutaResponseDTO> getRutas(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "", required = false) Long zonaCRG,
			@RequestParam(value = "", required = false) Long ruta,
			@RequestParam(value = "", required = false) Long casilla) throws IOException {

		try {
			long perfil = getPerfil(request);
			return rutasService.getRutas(idFederal, zonaCRG, ruta, casilla, perfil);
		} catch (RutasException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@PostMapping("crg/rutas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String asignarRutasCrg(HttpServletResponse response, HttpServletRequest request, @RequestBody AsignarRutasDTO rutas) throws IOException {
		long perfil = getPerfil(request);

		try {
			return rutasService.asignarRutas(rutas.getIdRuta(), rutas.getIdCrg(), perfil);
		} catch (RutasException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("catalogo/crg")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<CatalogoCrgDTO> getCatalogoCrg(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "tipoRepresentante") Long tipoRepresentante) throws IOException {

		long perfil = getPerfil(request);
		
		try {
			return rutasService.getCatalogo(tipoRepresentante,perfil);
		}catch (RutasException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}

	
/////////////////////////////////////////////////////////     catalogos
	
	@GetMapping("/distrito/{id}/zonaCrg")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ZonaCrgDTO> getZonasCrgByDistrito(HttpServletRequest request,
			@PathVariable("id") Long idDistrito) {

		long idPerfil =  getPerfil(request);

		List<ZonaCrgDTO> zonasCrg = rutasService.getZonasByDistrito(idPerfil, idDistrito);

		return zonasCrg;
	}
	
	@GetMapping("/zonaCrg/{id}/ruta")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<RutaCatalogoDto> getRutasByZona(HttpServletRequest request,
			@PathVariable("id") Long zonaCrg) {

		long idPerfil =  getPerfil(request);

		List<RutaCatalogoDto> rutas = rutasService.getRutaByZonaCrg(idPerfil, zonaCrg);

		return rutas;
	}
	
	@GetMapping("/ruta/{idRuta}/seccion")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<SeccionDTO> getSeccionByRutas(HttpServletResponse response,HttpServletRequest request,
			@PathVariable("idRuta") Long idRuta) throws IOException {

		long idPerfil =  getPerfil(request);
		try {
			List<SeccionDTO> seccionDTOs = rutasService.getSeccionByRutas(idPerfil, idRuta);
			return seccionDTOs;
		} catch (RutasException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

		
	}
	
	@GetMapping("/rutaCrg/{id}/casilla")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<CasillasCatalogoDto> getCasillaByRuta(HttpServletRequest request,
			@PathVariable("id") Long ruta) {
		
		long idPerfil = getPerfil(request);
		
		List<CasillasCatalogoDto> casillas = rutasService.getCasillaByRuta(idPerfil, ruta);
		return casillas;
	}
	
	@PostMapping("/rutas/casillas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private String asignarCasillas(HttpServletResponse response, HttpServletRequest request,
				@RequestBody AsignarCasillasDTO casillas) throws IOException {
		
		try {
			long perfil = getPerfil(request);
			return rutasService.asignarCasillas(perfil, casillas);
		} catch (RutasException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@DeleteMapping("/rutas/casillas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private String desasignarCasillas(HttpServletResponse response, HttpServletRequest request,
				@RequestBody DesasignarCasillasDTO casillas) throws IOException {
		
		try {
			long perfil = getPerfil(request);
			return rutasService.desasignarCasillas(perfil, casillas);
		} catch (RutasException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
}
