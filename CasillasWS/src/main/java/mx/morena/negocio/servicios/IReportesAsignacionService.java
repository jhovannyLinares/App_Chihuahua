package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.ReporteAsignacionDistritalDTO;
import mx.morena.negocio.exception.RepresentanteException;

public interface IReportesAsignacionService {

	List<ReporteAsignacionDistritalDTO> getRepAsignacion(long perfil) throws RepresentanteException ;

}
