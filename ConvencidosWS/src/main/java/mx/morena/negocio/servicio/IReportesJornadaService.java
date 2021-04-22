package mx.morena.negocio.servicio;

import java.util.List;

import mx.morena.negocio.dto.ReporteCapacitacionEstatalDTO;
import mx.morena.negocio.exception.JornadaException;

public interface IReportesJornadaService {
	
	List<ReporteCapacitacionEstatalDTO> getReporteCapEstatal(Long idUsuario) throws JornadaException;
	
}
