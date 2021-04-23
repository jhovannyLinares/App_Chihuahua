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
									@RequestParam(value = "idDisitritoFederal", required = false) Long idDisitritoFederal) throws IOException {
		
		try {
			Long idUsuario = getUsuario(request);
			return reportesJornadaService.getReporteCapEstatal(idUsuario, idDisitritoFederal);
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
								@RequestParam(value = "idDisitritoFederal", required = false) Long idDisitritoFederal) throws IOException {
		try {
			Long idUsuario = getUsuario(request);
			reportesJornadaService.getReporteCapEstatalDownload(response, idUsuario, idDisitritoFederal);
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
			Long idUsuario = getUsuario(request);
			return reportesJornadaService.getReporteRg(idEntidad, idFederal);
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
}
