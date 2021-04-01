package mx.morena.presentacion.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.ReporteConvencidosDTO;
import mx.morena.negocio.dto.ReporteDistritalDTO;
import mx.morena.negocio.dto.ReporteMunicipalDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IConvencidosService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "reportes")
@CrossOrigin
public class ReportesController extends MasterController {
	
	@Autowired
	private IConvencidosService convencidosService;
	
	@GetMapping("/convencidosDistrital")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteDistritalDTO>getReporteFederal(HttpServletResponse response, HttpServletRequest request) throws IOException{
		
		long usuario = getUsuario(request);
		
		try {
			return convencidosService.getReporteDistrital(usuario);
		} catch (ConvencidosException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}
	
	@GetMapping("/convencidosLocal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private java.util.List<ReporteConvencidosDTO>getReporteLocal(HttpServletResponse response) throws IOException{
		
		try {
			return convencidosService.getReporteDistrital();
		} catch (ConvencidosException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}
	
	@GetMapping("/convencidosMunicipal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private java.util.List<ReporteMunicipalDTO>getReporteMunicipal(HttpServletResponse response) throws IOException{
		
		try {
			return convencidosService.getReporteMunicipal();
		} catch (ConvencidosException e) {
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
