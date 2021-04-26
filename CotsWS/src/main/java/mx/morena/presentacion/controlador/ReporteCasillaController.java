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
import mx.morena.negocio.dto.ReporteVotacionDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.IReporteCasillaService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "/reporte")
@CrossOrigin
public class ReporteCasillaController extends MasterController {
	
	@Autowired
	private IReporteCasillaService reporteCasillaService;
	
	@GetMapping("/votacion")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteVotacionDTO> getReporteVotacion(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idReporte", required = false) Long idReporte) throws IOException {
		try {
			
			Long usuario = getUsuario(request);
			Long perfil = getPerfil(request);
			
			return reporteCasillaService.getReporteVotacion(usuario, perfil, idReporte);
			
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

}
