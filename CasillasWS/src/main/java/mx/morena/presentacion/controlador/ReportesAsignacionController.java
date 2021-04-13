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
import mx.morena.negocio.dto.ReporteCrgDTO;
import mx.morena.negocio.exception.RepresentanteException;
import mx.morena.negocio.servicios.IReportesAsignacionService;
import mx.morena.negocio.dto.ReporteRCDTO;
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
			return reportesService.getRepAsignacionDistrital(perfil);
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

	@GetMapping("/asignacionDistrital/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadCSV(HttpServletResponse response, HttpServletRequest request) throws IOException {
		
		try {

			long perfil = getPerfil(request);
			reportesService.getReporteDistritalDownload(response, perfil);

		} catch (RepresentanteException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	
	@GetMapping("/asignacionRc")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteRCDTO>getReporteRc(HttpServletResponse response, HttpServletRequest request) throws IOException{
		
		long perfil = getPerfil(request);
		
		try {
			return reportesService.getReporteRc(perfil);
		} catch (RepresentanteException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e ) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}
	
	@GetMapping("/asignacionRc/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadLocalCSV(HttpServletResponse response,  HttpServletRequest request) throws IOException {
		
		long perfil = getPerfil(request);
		try {

			reportesService.getReporteRcDownload(response, perfil);

		} catch (RepresentanteException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/asignacionCrg")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteCrgDTO> getReporteAsignacionCrg(HttpServletResponse response, HttpServletRequest request) throws IOException {
		
		try {
			long perfil = getPerfil(request);
			
			return reportesService.getReporteCrgDv(perfil);
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
	
	@GetMapping("/asignacionCrg/download")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadAsignacionCrgCSV(HttpServletResponse response,  HttpServletRequest request) throws IOException {
		
		try {
			long perfil = getPerfil(request);
			reportesService.getReporteCrgDownload(response, perfil);
		} catch (RepresentanteException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}


