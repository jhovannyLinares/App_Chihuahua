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
import mx.morena.negocio.exception.RepresentanteException;
import mx.morena.negocio.servicios.IReportesAsignacionService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "reportes")
@CrossOrigin
public class ReportesAsignacionController extends MasterController{

	@Autowired
	private IReportesAsignacionService reportesService;
	
	@GetMapping("/asignacionDistrital")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteAsignacionDistritalDTO>getReporteFederal(HttpServletResponse response, HttpServletRequest request) throws IOException{
		
		long perfil = getPerfil(request);
		
		try {
			return reportesService.getRepAsignacion(perfil);
		} catch (RepresentanteException e) {
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
