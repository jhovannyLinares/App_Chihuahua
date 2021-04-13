package mx.morena.negocio.servicios;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import mx.morena.negocio.dto.ReporteAsignacionDistritalDTO;
import mx.morena.negocio.exception.RepresentanteException;

public interface IReportesAsignacionService {

	List<ReporteAsignacionDistritalDTO> getRepAsignacionDistrital(long perfil) throws RepresentanteException ;

	void getReporteDistritalDownload(HttpServletResponse response, long perfil) throws RepresentanteException, IOException;

}
