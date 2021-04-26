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
import mx.morena.negocio.dto.ReporteAsignacionDistritalDTO;
import mx.morena.negocio.dto.ReporteSeguimintoVotoDTO;
import mx.morena.negocio.exception.RepresentanteException;
import mx.morena.negocio.exception.SeguimientoVotoException;
import mx.morena.negocio.servicios.IReporteSeguimientoVotoService;
import mx.morena.negocio.servicios.IReportesAsignacionService;
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
		long perfil = getUsuario(request);
		
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
}
