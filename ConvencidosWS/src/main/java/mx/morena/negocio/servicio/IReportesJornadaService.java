package mx.morena.negocio.servicio;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import mx.morena.negocio.dto.ReporteCapacitacionDistritalDTO;
import mx.morena.negocio.dto.ReporteCapacitacionEstatalDTO;
import mx.morena.negocio.dto.ReporteCapacitacionRgDTO;
import mx.morena.negocio.exception.JornadaException;

public interface IReportesJornadaService {
	
	List<ReporteCapacitacionEstatalDTO> getReporteCapEstatal(Long idUsuario, Long idEntidad, Long idDistritoFederal) throws JornadaException;
	
	public void getReporteCapEstatalDownload(HttpServletResponse response, Long idUsuario, Long idEntidad, Long idDistritoFederal) throws JornadaException, IOException;

	List<ReporteCapacitacionRgDTO> getReporteRg(Long idEntidad, Long idFederal) throws JornadaException;
	
	List<ReporteCapacitacionDistritalDTO> getReporteCapDistrital(Long idUsuario, Long idEstatal, Long idFederal) throws JornadaException;
	
	public void getReporteDistritalDownload(HttpServletResponse response, Long idUsuario, Long idEstatal, Long idFederal) throws JornadaException, IOException;
	
}
