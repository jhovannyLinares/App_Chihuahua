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
import mx.morena.negocio.dto.ReporteAsistenciaCrgDTO;
import mx.morena.negocio.dto.ReporteAsistenciaEstatalDTO;
import mx.morena.negocio.dto.ReporteAsistenciaFederalDTO;
import mx.morena.negocio.dto.ReporteAsistenciaLocalDTO;
import mx.morena.negocio.dto.ReporteAsistenciaMunicipalDTO;
import mx.morena.negocio.dto.ReporteAsistenciaRgDTO;
import mx.morena.negocio.dto.ReporteResultadosDTO;
import mx.morena.negocio.dto.ReporteVotacionDTO;
import mx.morena.negocio.dto.ReporteVotacionMunicipalDTO;
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

	@GetMapping("/votacion/download")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadCSV(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idReporte", required = false) Long idReporte) throws IOException {

		try {
			long usuario = getUsuario(request);
			long perfil = getPerfil(request);
			reporteCasillaService.getReporteVotacionDownload(response, usuario, perfil, idReporte);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping("/asistenciaEstatal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<ReporteAsistenciaEstatalDTO> reporteAsistenciaEstatal(HttpServletResponse response,
			HttpServletRequest request, @RequestParam(value = "idFederal", required = false) Long idFederal)
			throws IOException {

		try {
			long usuario = getUsuario(request);
			long perfil = getPerfil(request);

			return reporteCasillaService.getReporteAsistenciaEstatal(usuario, perfil, idFederal);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return null;
	}

	@GetMapping("/asistenciaEstatal/download")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadReporteAsistenciaEstatal(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idFederal", required = false) Long idFederal) throws IOException {

		try {
			long usuario = getUsuario(request);
			long perfil = getPerfil(request);

			reporteCasillaService.getReporteAsistenciaEstatalDownload(response, usuario, perfil, idFederal);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping("/asistenciaFederal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<ReporteAsistenciaFederalDTO> reporteAsistenciaFederal(HttpServletResponse response,
			HttpServletRequest request) throws IOException {
//			@RequestParam(value = "idFederal", required = false) Long idFederal) throws IOException {

		try {
			long usuario = getUsuario(request);
			long perfil = getPerfil(request);

			return reporteCasillaService.getReporteAsistenciaDistrital(usuario, perfil);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return null;
	}

	@GetMapping("/asistenciaFederal/download")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadReporteAsistenciaDistrital(HttpServletResponse response, HttpServletRequest request)
			throws IOException {
//			@RequestParam(value = "idFederal", required = false) Long idFederal) throws IOException { 

		try {
			long usuario = getUsuario(request);
			long perfil = getPerfil(request);

			reporteCasillaService.getReporteAsistenciaDistritalDownload(response, usuario, perfil);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/asistenciaLocal")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<ReporteAsistenciaLocalDTO> reporteAsistenciaLocal(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "idLocal", required = false) Long idLocal) throws IOException {
		
		try {
			long usuario = getUsuario(request);
			long perfil = getPerfil(request);
			
			return reporteCasillaService.getReporteAsistenciaLocal(usuario, perfil, idFederal, idLocal);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return null;
	}
	
	@GetMapping("/asistenciaLocal/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadReporteAsistenciaLocal(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "idLocal", required = false) Long idLocal) throws IOException {
		
		try {
			long usuario = getUsuario(request);
			long perfil = getPerfil(request);
			
			reporteCasillaService.getReporteAsistenciaLocalDownload(response, usuario, perfil, idFederal, idLocal);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/asistenciaMunicipal")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<ReporteAsistenciaMunicipalDTO> reporteAsistenciaMunicipal(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "idLocal", required = false) Long idLocal,
			@RequestParam(value = "idMunicipio", required = false) Long idMunicipio) throws IOException {
		
		try {
			long usuario = getUsuario(request);
			long perfil = getPerfil(request);
//			Long idLocal = 0L;
			
			return reporteCasillaService.getReporteAsistenciaMunicipal(usuario, perfil, idFederal, idLocal, idMunicipio);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return null;
	}
	
	@GetMapping("/asistenciaMunicipal/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadReporteAsistenciaMunicipal(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "idLocal", required = false) Long idLocal,
			@RequestParam(value = "idMunicipio", required = false) Long idMunicipio) throws IOException {
		try {
			long usuario = getUsuario(request);
			long perfil = getPerfil(request);
			
			reporteCasillaService.getReporteAsistenciaMunicipalDownload(response, usuario, perfil, idFederal, idLocal, idMunicipio);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	
	@GetMapping("/resultados")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteResultadosDTO> getReporteResultados(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idAmbito", required = false) Long idReporte,
			@RequestParam(value = "idUbicacion", required = false) Long idUbicacion) throws IOException {
		try {

			Long usuario = getUsuario(request);
			Long perfil = getPerfil(request);

			return reporteCasillaService.getReporteResultados(usuario, perfil, idReporte, idUbicacion);

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

	@GetMapping("/resultados/download")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadResultados(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idReporte", required = false) Long idReporte,
			@RequestParam(value = "idUbicacion", required = false) Long idUbicacion) throws IOException {

		try {
			Long usuario = getUsuario(request);
			long perfil = getPerfil(request);
			reporteCasillaService.getReporteResultadosDownload(response, usuario, perfil, idReporte, idUbicacion);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/municipal")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteVotacionMunicipalDTO> getReporteMunicipal(HttpServletResponse response, HttpServletRequest request,
										@RequestParam(value = "idEleccion", required = true) Long idEleccion) throws IOException {
		
		try {
			Long usuario = getUsuario(request);
			return reporteCasillaService.getReporteMunicipal(usuario, idEleccion);
			
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
	
	@GetMapping("/municipal/download")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadReporteMunicipal(HttpServletResponse response, HttpServletRequest request,
										@RequestParam(value = "idEleccion", required = true) Long idEleccion) throws IOException {
		
		try {
			Long usuario = getUsuario(request);
			Long perfil = getPerfil(request);
			reporteCasillaService.getReporteMunicipioDownload(response, usuario, perfil, idEleccion);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping("/asistenciaCrg")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<ReporteAsistenciaCrgDTO> reporteAsistenciaCrg(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "idLocal", required = false) Long idLocal,
			@RequestParam(value = "idMunicipio", required = false) Long idMunicipio) throws IOException {
		
		try {
			Long usuario = getUsuario(request);
			Long perfil = getPerfil(request);
//			Long idLocal = 0L;
			
			return reporteCasillaService.getReporteAsistenciaCrg(usuario, perfil, idFederal, idLocal, idMunicipio);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return null;
	}
	
	@GetMapping("/asistenciaCrg/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadReporteAsistenciaCrg(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "idLocal", required = false) Long idLocal,
			@RequestParam(value = "idMunicipio", required = false) Long idMunicipio) throws IOException {
		try {
			long usuario = getUsuario(request);
			long perfil = getPerfil(request);
			
			reporteCasillaService.getReporteAsistenciaCrgDownload(response, usuario, perfil, idFederal, idLocal, idMunicipio);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/asistenciaRg")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public List<ReporteAsistenciaRgDTO> reporteAsistenciaRg(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "idLocal", required = false) Long idLocal,
			@RequestParam(value = "idMunicipio", required = false) Long idMunicipio,
			@RequestParam(value = "idRutaRg", required = false) String idRutaRg) throws IOException {
		
		try {
			long usuario = getUsuario(request);
			long perfil = getPerfil(request);
//			Long idLocal = 0L;
			
			return reporteCasillaService.getReporteAsistenciaRg(usuario, perfil, idFederal, idLocal, idMunicipio, idRutaRg);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return null;
	}
	
	@GetMapping("/asistenciaRg/download")	
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadReporteAsistenciaRg(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "idFederal", required = false) Long idFederal,
			@RequestParam(value = "idLocal", required = false) Long idLocal,
			@RequestParam(value = "idMunicipio", required = false) Long idMunicipio,
			@RequestParam(value = "idRutaRg", required = false) String idRutaRg) throws IOException {
		try {
			long usuario = getUsuario(request);
			long perfil = getPerfil(request);
			
			reporteCasillaService.getReporteAsistenciaRgDownload(response, usuario, perfil, idFederal, idLocal, idMunicipio, idRutaRg);

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
