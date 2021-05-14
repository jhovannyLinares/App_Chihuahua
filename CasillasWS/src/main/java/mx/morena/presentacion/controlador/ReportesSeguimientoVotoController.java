package mx.morena.presentacion.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.ReporteInstalacionCasillaCrgDTO;
import mx.morena.negocio.dto.ReporteInstalacionCasillaDTO;
import mx.morena.negocio.dto.ReporteInstalacionCasillaMuniDTO;
import mx.morena.negocio.dto.ReporteInstalacionCasillaRgDTO;
import mx.morena.negocio.dto.ReporteSeguimintoVotoDTO;
import mx.morena.negocio.exception.SeguimientoVotoException;
import mx.morena.negocio.servicios.IReporteSeguimientoVotoService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "reportes")
@CrossOrigin
public class ReportesSeguimientoVotoController extends MasterController{

	@Autowired
	private IReporteSeguimientoVotoService reportesService;
	
	@GetMapping("/seguimientoVoto")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteSeguimintoVotoDTO>getReporteVoto(HttpServletResponse response, HttpServletRequest request) throws IOException{
		
		long idUsuario = getUsuario(request);
		long perfil = getPerfil(request);
		
		try {
			return reportesService.getSeguimeitoVoto(perfil, idUsuario);
		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}
	
	@GetMapping("/seguimientoVoto/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadSeguimientoVotoCSV(HttpServletResponse response,  HttpServletRequest request) throws IOException {
		
		long perfil = getPerfil(request);
		long usuario = getUsuario(request);
		try {

			reportesService.getReporteSeguimientoVotoDownload(response, perfil, usuario);

		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/instalacionCasillaEstatal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteInstalacionCasillaDTO>getReporteInstCasillaEstatal(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idDistritoFederal", required = false) Long idDistritoFederal,						
			@RequestParam(value = "idDistritoLocal", required = false) Long idDistritoLocal,
			@RequestParam(value = "idMunicipal", required = false) Long idMunicipal) throws SeguimientoVotoException, IOException{
		
		long idUsuario = getUsuario(request);
		
		try {
			return reportesService.getInstalacionCasillaEstatal(idUsuario, idDistritoFederal, idDistritoLocal, idMunicipal);
		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}

	@GetMapping("/instalacionCasillaFederal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteInstalacionCasillaDTO>getReporteInstCasillaFederal(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idDistritoFederal", required = false) Long idDistritoFederal,						
			@RequestParam(value = "idDistritoLocal", required = false) Long idDistritoLocal,
			@RequestParam(value = "idMunicipal", required = false) Long idMunicipal) throws SeguimientoVotoException, IOException{
		
		long idUsuario = getUsuario(request);
		
		try {
			return reportesService.getInstalacionCasillaFederal(idUsuario, idDistritoFederal, idDistritoLocal, idMunicipal);
		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}
	
	@GetMapping("/instalacionCasillaLocal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteInstalacionCasillaDTO>getReporteInstCasillaLocal(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idDistritoFederal", required = false) Long idDistritoFederal,						
			@RequestParam(value = "idDistritoLocal", required = false) Long idDistritoLocal,
			@RequestParam(value = "idMunicipal", required = false) Long idMunicipal) throws SeguimientoVotoException, IOException{
		
		long idUsuario = getUsuario(request);
		
		try {
			return reportesService.getInstalacionCasillaLocal(idUsuario, idDistritoFederal, idDistritoLocal, idMunicipal);
		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}
	
	@GetMapping("/instalacionCasillaMunicipal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteInstalacionCasillaMuniDTO>getReporteInstCasillaMunicipal(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idDistritoFederal", required = false) Long idDistritoFederal,						
			@RequestParam(value = "idDistritoLocal", required = false) Long idDistritoLocal,
			@RequestParam(value = "idMunicipal", required = false) Long idMunicipal) throws SeguimientoVotoException, IOException{
		
		long idUsuario = getUsuario(request);
		
		try {
			return reportesService.getInstalacionCasillaMunicipal(idUsuario, idDistritoFederal, idDistritoLocal, idMunicipal);
		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}
	
	@GetMapping("/instalacionCasillaCrg")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteInstalacionCasillaCrgDTO>getReporteInstCasillaCrg(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idDistritoFederal", required = false) Long idDistritoFederal,						
			@RequestParam(value = "idDistritoLocal", required = false) Long idDistritoLocal,
			@RequestParam(value = "idMunicipal", required = false) Long idMunicipal) throws SeguimientoVotoException, IOException{
		
		long idUsuario = getUsuario(request);
		
		try {
			return reportesService.getInstalacionCasillaCrg(idUsuario, idDistritoFederal, idDistritoLocal, idMunicipal);
		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}

	@GetMapping("/instalacionCasillaRg")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteInstalacionCasillaRgDTO>getReporteInstCasillaRg(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idDistritoFederal", required = false) Long idDistritoFederal,						
			@RequestParam(value = "idDistritoLocal", required = false) Long idDistritoLocal,
			@RequestParam(value = "idMunicipal", required = false) Long idMunicipal
) throws SeguimientoVotoException, IOException{
		
		long idUsuario = getUsuario(request);
		
		try {
			return reportesService.getInstalacionCasillaRg(idUsuario, idDistritoFederal, idDistritoLocal, idMunicipal);
		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}
	
	@GetMapping("/instalacionCasillaEstatal/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadInstalacionCasillaEstatalCSV(HttpServletResponse response,  HttpServletRequest request,
			@RequestParam(value = "idDistritoFederal", required = false) Long idDistritoFederal,						
			@RequestParam(value = "idDistritoLocal", required = false) Long idDistritoLocal,
			@RequestParam(value = "idMunicipal", required = false) Long idMunicipal) throws SeguimientoVotoException, IOException {
		
		long usuario = getUsuario(request);
		try {

			reportesService.getReporteInstalacionCasillaEstatalDownload(response, usuario, idDistritoFederal, idDistritoLocal, idMunicipal);

		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/instalacionCasillaFederal/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadInstalacionCasillaFederalCSV(HttpServletResponse response,  HttpServletRequest request,
			@RequestParam(value = "idDistritoFederal", required = false) Long idDistritoFederal,						
			@RequestParam(value = "idDistritoLocal", required = false) Long idDistritoLocal,
			@RequestParam(value = "idMunicipal", required = false) Long idMunicipal) throws SeguimientoVotoException, IOException {
		
		long usuario = getUsuario(request);
		try {

			reportesService.getReporteInstalacionCasillaFederalDownload(response, usuario, idDistritoFederal, idDistritoLocal, idMunicipal);

		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/instalacionCasillaLocal/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadInstalacionCasillaLocalCSV(HttpServletResponse response,  HttpServletRequest request,
			@RequestParam(value = "idDistritoFederal", required = false) Long idDistritoFederal,						
			@RequestParam(value = "idDistritoLocal", required = false) Long idDistritoLocal,
			@RequestParam(value = "idMunicipal", required = false) Long idMunicipal) throws SeguimientoVotoException, IOException {
		
		long usuario = getUsuario(request);
		try {

			reportesService.getReporteInstalacionCasillaLocalDownload(response, usuario, idDistritoFederal, idDistritoLocal, idMunicipal);

		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/instalacionCasillaMunicipal/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadInstalacionCasillaMunicipalCSV(HttpServletResponse response,  HttpServletRequest request,
			@RequestParam(value = "idDistritoFederal", required = false) Long idDistritoFederal,						
			@RequestParam(value = "idDistritoLocal", required = false) Long idDistritoLocal,
			@RequestParam(value = "idMunicipal", required = false) Long idMunicipal) throws SeguimientoVotoException, IOException {
		
		long usuario = getUsuario(request);
		try {

			reportesService.getReporteInstalacionCasillaMunicipalDownload(response, usuario, idDistritoFederal, idDistritoLocal, idMunicipal);

		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/instalacionCasillaCrg/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadInstalacionCasillaCrgCSV(HttpServletResponse response,  HttpServletRequest request,
			@RequestParam(value = "idDistritoFederal", required = false) Long idDistritoFederal,						
			@RequestParam(value = "idDistritoLocal", required = false) Long idDistritoLocal,
			@RequestParam(value = "idMunicipal", required = false) Long idMunicipal) throws SeguimientoVotoException, IOException {
		
		long usuario = getUsuario(request);
		try {

			reportesService.getReporteInstalacionCasillaCrgDownload(response, usuario, idDistritoFederal, idDistritoLocal, idMunicipal);

		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping("/instalacionCasillaRg/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadInstalacionCasillaRgCSV(HttpServletResponse response,  HttpServletRequest request,
			@RequestParam(value = "idDistritoFederal", required = false) Long idDistritoFederal,						
			@RequestParam(value = "idDistritoLocal", required = false) Long idDistritoLocal,
			@RequestParam(value = "idMunicipal", required = false) Long idMunicipal) throws SeguimientoVotoException, IOException {
		
		long usuario = getUsuario(request);
		try {

			reportesService.getReporteInstalacionCasillaRgDownload(response, usuario, idDistritoFederal, idDistritoLocal, idMunicipal);

		} catch (SeguimientoVotoException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
