package mx.morena.negocio.servicios;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import mx.morena.negocio.dto.ReporteAsignacionDistritalDTO;
import mx.morena.negocio.dto.ReporteCrgDTO;
import mx.morena.negocio.dto.ReporteAsignacionEstatalDTO;
import mx.morena.negocio.exception.RepresentanteException;

import mx.morena.negocio.dto.ReporteRCDTO;
import mx.morena.negocio.dto.ReporteRgDTO;
public interface IReportesAsignacionService {
	
	List<ReporteRCDTO> getReporteRc(Long perfil, Long idUsuario) throws RepresentanteException;

	List<ReporteAsignacionDistritalDTO> getRepAsignacionDistrital(long idUsuario) throws RepresentanteException ;
	
	public void getReporteRcDownload(HttpServletResponse response, Long perfil, Long idUsuario) throws RepresentanteException, IOException;

	void getReporteDistritalDownload(HttpServletResponse response, long perfil) throws RepresentanteException, IOException;
	
	List<ReporteCrgDTO> getReporteCrgDv(Long idUsuario, Long perfil) throws RepresentanteException;

	void getReporteCrgDownload(HttpServletResponse response, long perfil, long idUsuario) throws RepresentanteException, IOException;
	
	List<ReporteAsignacionEstatalDTO> getReporteAsignacionEstatal(long idUsuario) throws RepresentanteException, IOException;

	void getReporteEstatalDownload(HttpServletResponse response, long perfil) throws RepresentanteException, IOException;

	
	List<ReporteRgDTO> getReporteRg(Long perfil, Long idUsuario) throws RepresentanteException;
	
	void getReporteRgDownload(HttpServletResponse response, Long perfil, Long idUsuario) throws RepresentanteException, IOException;
}
