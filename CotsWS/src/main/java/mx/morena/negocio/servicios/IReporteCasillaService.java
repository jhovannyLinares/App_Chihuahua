package mx.morena.negocio.servicios;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import mx.morena.negocio.dto.ReporteAsistenciaEstatalDTO;
import mx.morena.negocio.dto.ReporteAsistenciaFederalDTO;
import mx.morena.negocio.dto.ReporteAsistenciaLocalDTO;
import mx.morena.negocio.dto.ReporteVotacionDTO;
import mx.morena.negocio.exception.CotException;

public interface IReporteCasillaService {

	List<ReporteVotacionDTO> getReporteVotacion(Long usuario, Long perfil, Long idReporte) throws CotException, IOException;

	void getReporteVotacionDownload(HttpServletResponse response, Long usuario, Long perfil, Long idReporte) throws CotException, IOException;

	List<ReporteAsistenciaEstatalDTO> getReporteAsistenciaEstatal(Long usuario, Long perfil, Long idFederal) throws CotException, IOException;

	void getReporteAsistenciaEstatalDownload(HttpServletResponse response, Long usuario, Long perfil, Long idFederal) throws CotException, IOException;

	List<ReporteAsistenciaFederalDTO> getReporteAsistenciaDistrital(Long usuario, Long perfil) throws CotException, IOException;

	void getReporteAsistenciaDistritalDownload(HttpServletResponse response, Long usuario, Long perfil) throws CotException, IOException;

	List<ReporteAsistenciaLocalDTO> getReporteAsistenciaLocal(Long usuario, Long perfil, Long idFederal, Long idLocal) throws CotException, IOException;


}
