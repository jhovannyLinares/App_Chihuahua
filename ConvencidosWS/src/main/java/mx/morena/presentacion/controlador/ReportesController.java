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
import mx.morena.negocio.dto.ReporteLocalDTO;
import mx.morena.negocio.dto.ReporteDistritalDTO;
import mx.morena.negocio.dto.ReporteMunicipalDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IConvencidosService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "/reporte")
@CrossOrigin
public class ReportesController extends MasterController {
	
	@Autowired
	private IConvencidosService convencidosService;
	
	@GetMapping("/convencidosDistrital")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteDistritalDTO>getReporteFederal(HttpServletResponse response, HttpServletRequest request) throws IOException{
		
		long perfil = getPerfil(request);
		
		try {
			return convencidosService.getReporteDistrital(perfil);
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
	private List<ReporteLocalDTO>getReporteLocal(HttpServletResponse response, HttpServletRequest request) throws IOException{
		
		long perfil = getPerfil(request);
		
		try {
			return convencidosService.getReporteLocal(perfil);
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
	private List<ReporteMunicipalDTO> getReporteMunicipal(HttpServletResponse response, HttpServletRequest request) throws IOException{
		
		try {
			long perfil = getPerfil(request);
			
			return convencidosService.getReporteMunicipal(perfil);
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
	
	@GetMapping("/convencidosDistrital/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadCSV(HttpServletResponse response, HttpServletRequest request) throws IOException {
		
		try {

			long perfil = getPerfil(request);
			convencidosService.getReporteDownload(response, perfil);

		} catch (ConvencidosException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/convencidosLocal/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadLocalCSV(HttpServletResponse response,  HttpServletRequest request) throws IOException {
		
		long perfil = getPerfil(request);
		try {

			convencidosService.getReporteLocalDownload(response, perfil);

		} catch (ConvencidosException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
}
