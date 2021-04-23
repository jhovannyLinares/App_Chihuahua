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
import mx.morena.negocio.dto.ReporteCapacitacionCrgDTO;
import mx.morena.negocio.dto.ReporteCapacitacionDistritalDTO;
import mx.morena.negocio.dto.ReporteCapacitacionEstatalDTO;
import mx.morena.negocio.dto.ReporteCapacitacionRgDTO;
import mx.morena.negocio.exception.JornadaException;
import mx.morena.negocio.servicio.IReportesJornadaService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "reportesCapacitacion")
@CrossOrigin
public class ReportesJornadaController extends MasterController {
	
	@Autowired
	private IReportesJornadaService reportesJornadaService;
	
	@GetMapping("/estatal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<ReporteCapacitacionEstatalDTO> getReporteCapacitacionEst(HttpServletResponse response, HttpServletRequest request,
									@RequestParam(value = "idEntidad", required = true) Long idEntidad,						
									@RequestParam(value = "idDisitritoFederal", required = false) Long idDisitritoFederal,
									@RequestParam(value = "idCrg", required = false) Long idCrg,
									@RequestParam(value = "idRg", required = false) Long idRg) throws IOException {
		
		try {
			Long idUsuario = getUsuario(request);
			return reportesJornadaService.getReporteCapEstatal(idUsuario, idEntidad, idDisitritoFederal);
		} catch (JornadaException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
		
	}
	
	@GetMapping("/estatal/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadReporteEstatalCSV(HttpServletResponse response, HttpServletRequest request,
								@RequestParam(value = "idEntidad", required = true) Long idEntidad,
								@RequestParam(value = "idDisitritoFederal", required = false) Long idDisitritoFederal,
								@RequestParam(value = "idCrg", required = false) Long idCrg,
								@RequestParam(value = "idRg", required = false) Long idRg) throws IOException {
		try {
			Long idUsuario = getUsuario(request);
			reportesJornadaService.getReporteCapEstatalDownload(response, idUsuario, idEntidad, idDisitritoFederal);
		} catch (JornadaException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/rg")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<ReporteCapacitacionRgDTO> getReporteCapacitacionRG(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idEntidad", required = false) Long idEntidad,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "idCrg", required = false) Long idCrg,
			@RequestParam(value = "idRg", required = false) Long idRg) throws IOException {
		
		try {
			Long usuario = getUsuario(request);
			return reportesJornadaService.getReporteRg(usuario, idEntidad, idFederal, idCrg, idRg);
		}catch (JornadaException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/rg/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadCSVRg(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idEntidad", required = false) Long idEntidad,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "idCrg", required = false) Long idCrg,
			@RequestParam(value = "idRg", required = false) Long idRg) throws IOException {
		
		try {

			long usuario = getUsuario(request);
			reportesJornadaService.getReporteRgDownload(response, usuario, idEntidad, idFederal, idCrg, idRg);

		} catch (JornadaException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/distrital")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<ReporteCapacitacionDistritalDTO> getReporteCapacitacionDist(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idEntidad", required = false) Long idEntidad,
			@RequestParam(value = "idFederal", required = false) Long idFederal) throws IOException {
		
		Long usuario = getUsuario(request);
		
		try {
			return reportesJornadaService.getReporteCapDistrital(usuario, idEntidad, idFederal);
		} catch (JornadaException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	@GetMapping("/distrital/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadReporteCapacitacionCSV(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idEntidad", required = false) Long idEntidad,
			@RequestParam(value = "idFederal", required = false) Long idFede) throws IOException {
		
		try {
			
			Long idUsuario = getUsuario(request);

			reportesJornadaService.getReporteDistritalDownload(response,idUsuario, idEntidad, idFede);

		} catch (JornadaException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/* Reporte de Capacitacion CRG*/
	
	@GetMapping("/crg")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<ReporteCapacitacionCrgDTO> getReporteCapacitacionCrg(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idEntidad", required = false) Long idEntidad,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "idCrg", required = false) Long idCrg,
			@RequestParam(value = "idRg", required = false) Long idRg) throws IOException{
		
		try {
			Long idUsuario = getUsuario(request);
			return reportesJornadaService.getReporteCapCrg(idUsuario, idEntidad, idFederal, idCrg, idRg);
		}catch (JornadaException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
		
	}
	
	/* ICJ- Servicio encargado de la descarga del excel */
	
	@GetMapping("/crg/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadCSV(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idEntidad", required = false) Long idEntidad,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "idCrg", required = false) Long idCrg,
			@RequestParam(value = "idRg", required = false) Long idRg) throws IOException {
		
		try {
			long usuario = getUsuario(request);
			reportesJornadaService.getReporteDownload(response, usuario, idEntidad, idFederal, idCrg, idRg );

		} catch (JornadaException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
