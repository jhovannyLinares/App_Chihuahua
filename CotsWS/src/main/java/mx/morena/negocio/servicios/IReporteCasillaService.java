package mx.morena.negocio.servicios;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import mx.morena.negocio.dto.ReporteVotacionDTO;
import mx.morena.negocio.exception.CotException;

public interface IReporteCasillaService {

	List<ReporteVotacionDTO> getReporteVotacion(Long usuario, Long perfil, Long idReporte) throws CotException, IOException;

	void getReporteVotacionDownload(HttpServletResponse response, Long usuario, long perfil, Long idReporte) throws CotException, IOException;

}
