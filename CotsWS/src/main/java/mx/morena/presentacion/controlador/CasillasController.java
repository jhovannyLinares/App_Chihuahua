package mx.morena.presentacion.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.EnvioActasDTO;
import mx.morena.negocio.dto.PartidosXAmbitoDTO;
import mx.morena.negocio.dto.ResultadoOkDTO;
import mx.morena.negocio.dto.ResultadoVotacionDTO;
import mx.morena.negocio.dto.UbicacionCasillaDTO;
import mx.morena.negocio.dto.VotacionesDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.ICasillasService;
import mx.morena.negocio.servicios.impl.PreguntasCasillaDTO;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "/casillas")
@CrossOrigin
public class CasillasController extends MasterController {

	@Autowired
	private ICasillasService casillaService;

	@Autowired
	private ServletContext servletContext;

	@PostMapping("/envioActas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public Long guardarActas(HttpServletResponse response, HttpServletRequest request, @RequestBody EnvioActasDTO actas)
			throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return casillaService.save(actas, perfil, usuario);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	@GetMapping("/{idCasilla}/envioActas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<VotacionesDTO> getActas(HttpServletResponse response, HttpServletRequest request,
			@PathVariable("idCasilla") Long idCasilla) throws IOException {

//		long perfil = getPerfil(request);
//		long usuario = getUsuario(request);

		try {
			return casillaService.getActas(idCasilla);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	@GetMapping("/{idCasilla}/partidos")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<PartidosXAmbitoDTO> getPartidos(HttpServletResponse response, HttpServletRequest request,
			@PathVariable("idCasilla") Long idCasilla) throws IOException {

//		long perfil = getPerfil(request);
//		long usuario = getUsuario(request);

		try {
			return casillaService.getPartidos(idCasilla);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	@PostMapping("/resultados")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResultadoOkDTO saveResultados(HttpServletResponse response, HttpServletRequest request,
			@RequestBody ResultadoVotacionDTO actas) throws IOException {
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);

		try {
			return casillaService.saveResultados(actas, perfil, usuario);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	@GetMapping("/{idCasilla}/formulario")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public PreguntasCasillaDTO getFormulario(HttpServletResponse response, HttpServletRequest request,
			@PathVariable("idCasilla") Long idCasilla, @RequestParam(value = "ambito", required = true) Long ambito)
			throws IOException {

//		long perfil = getPerfil(request);
//		long usuario = getUsuario(request);

		try {
			return casillaService.getFormulario(idCasilla, ambito);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	/* ICJ - Servicio para consultar actas por tipo de acta */

	@GetMapping("/consultaActas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<EnvioActasDTO> getActasByTipo(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idTipoActa", required = true) Long idTipoActa) throws IOException {

		long perfil = getPerfil(request);

		try {
			return casillaService.getActasByTipo(idTipoActa, perfil);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	/**
	 * @param response
	 * @param request
	 * @param idCasilla
	 * @param dto
	 * @return
	 * @throws IOException
	 * 
	 *                     Actualizacion de ubicacion de la casilla
	 */
	@PutMapping("/{idCasilla}")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public String updateDatosCasilla(HttpServletResponse response, HttpServletRequest request,
			@PathVariable("idCasilla") Long idCasilla, @RequestBody UbicacionCasillaDTO dto) throws IOException {
		long perfil = getPerfil(request);
//		long usuario = getUsuario(request);
//		long idCasilla = 0L;

		try {
			return casillaService.updateDatosCasilla(perfil, idCasilla, dto);
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	/* ICJ - Servicio encargado de hacer la descarga de las actas (pdf o jpeg) */

	@GetMapping("/consultaActas/download")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private ResponseEntity<InputStreamResource> getReporte(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idActa", required = true) long idActa,
			@RequestParam(value = "TipoMime", required = true) String typeMime) throws IOException {
		long perfil = getPerfil(request);
		String extension = "";

		try {

			// Valida la extension de acuerdo al tipo de mime
			if (typeMime.equalsIgnoreCase("image/jpeg")) {
				extension = "jpg";
			} else if (typeMime.equalsIgnoreCase("application/pdf")) {
				extension = "pdf";
			} else {
				extension = null;
			}

			// Comienza la descarga del archivo
			String fileName = casillaService.getActaFile(perfil, response, idActa, extension);
			org.springframework.http.MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext,
					fileName);
			System.out.println("fileName: " + fileName);
			System.out.println("mediaType: " + mediaType);

			File file = new File(fileName);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

			return ResponseEntity.ok()
					// Content-Disposition
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
					// Content-Type
					.contentType(mediaType)
					// Contet-Length
					.contentLength(file.length()) //
					.body(resource);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return null;

	}
}
