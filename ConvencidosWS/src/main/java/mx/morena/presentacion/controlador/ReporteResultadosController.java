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
import mx.morena.negocio.dto.ReportePMunicipalDTO;
import mx.morena.negocio.dto.ReporteResultadosActasDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IReporteResultadosService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "reportesResultados")
@CrossOrigin
public class ReporteResultadosController extends MasterController {
	
	@Autowired
	private IReporteResultadosService reporteResultadosService;
	
	@GetMapping("/actas")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteResultadosActasDTO> getReporteResultados(HttpServletResponse response, HttpServletRequest request) throws IOException {
		
		try {
			Long usuario = getUsuario(request);
			return reporteResultadosService.getReporteResultados(usuario);
			
		} catch (ConvencidosException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}
	
	@GetMapping("/actas/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadReporteResultados(HttpServletResponse response, HttpServletRequest request) throws IOException{ 
		
		try {
			Long usuario = getUsuario(request);
			Long perfil = getPerfil(request);
			reporteResultadosService.getReporteResultadosDownload(response, usuario, perfil);

		} catch (ConvencidosException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/municipal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReportePMunicipalDTO> getReporteResultadosMunicipal(HttpServletResponse response, HttpServletRequest request,
									@RequestParam(value = "idEleccion", required = true) Long idEleccion) throws IOException {
		
		try {
			Long usuario = getUsuario(request);
			return reporteResultadosService.getReportePresidenteMunicipal(usuario, idEleccion);
			
		} catch (ConvencidosException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}

	}
	
	@GetMapping("/municipal/download")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private void getReporteResultadosMunicipalDownload(HttpServletResponse response, HttpServletRequest request,
						@RequestParam(value = "idEleccion", required = true) Long idEleccion) throws IOException {
		
		try {
			Long usuario = getUsuario(request);
			Long perfil = getPerfil(request);
			reporteResultadosService.getReportePresidenteMunicipalDownload(response, usuario, perfil, idEleccion);
			
		} catch (ConvencidosException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

}
