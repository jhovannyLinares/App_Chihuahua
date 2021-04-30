package mx.morena.negocio.servicios;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import mx.morena.negocio.dto.ReporteInstalacionCasillaCrgDTO;
import mx.morena.negocio.dto.ReporteInstalacionCasillaDTO;
import mx.morena.negocio.dto.ReporteInstalacionCasillaMuniDTO;
import mx.morena.negocio.dto.ReporteInstalacionCasillaRgDTO;
import mx.morena.negocio.dto.ReporteSeguimintoVotoDTO;
import mx.morena.negocio.dto.SeguimientoVotoDTO;
import mx.morena.negocio.exception.SeguimientoVotoException;

public interface IReporteSeguimientoVotoService {

	List<ReporteSeguimintoVotoDTO> getSeguimeitoVoto(Long Perfil, Long Usuario) throws SeguimientoVotoException, IOException;
	
	public List<SeguimientoVotoDTO> getCasillaBySeccion(long idPerfil, Long idSeccion) throws SeguimientoVotoException;
	
	public void getReporteSeguimientoVotoDownload(HttpServletResponse response, Long perfil, Long usuario) throws SeguimientoVotoException, IOException;
	
	String marcarConvencido(Long idUsuario, Long idConvencido, boolean IsNotificado) throws SeguimientoVotoException;
	
	List<ReporteInstalacionCasillaDTO> getInstalacionCasillaEstatal(Long idUsuario, Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)throws SeguimientoVotoException, IOException;
	
	List<ReporteInstalacionCasillaDTO> getInstalacionCasillaFederal(Long idUsuario, Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)throws SeguimientoVotoException, IOException;
	
	List<ReporteInstalacionCasillaDTO> getInstalacionCasillaLocal(Long idUsuario, Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)throws SeguimientoVotoException, IOException;
	
	List<ReporteInstalacionCasillaMuniDTO> getInstalacionCasillaMunicipal(Long idUsuario, Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)throws SeguimientoVotoException, IOException;
	
	List<ReporteInstalacionCasillaCrgDTO> getInstalacionCasillaCrg(Long idUsuario, Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)throws SeguimientoVotoException, IOException;
	
	List<ReporteInstalacionCasillaRgDTO> getInstalacionCasillaRg(Long idUsuario, Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)throws SeguimientoVotoException, IOException;
	
	public void getReporteInstalacionCasillaEstatalDownload(HttpServletResponse response,Long idUsuario, Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)throws SeguimientoVotoException, IOException;
	
	public void getReporteInstalacionCasillaFederalDownload(HttpServletResponse response,Long idUsuario, Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)throws SeguimientoVotoException, IOException;
	
	public void getReporteInstalacionCasillaLocalDownload(HttpServletResponse response,Long idUsuario, Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)throws SeguimientoVotoException, IOException;
	
	public void getReporteInstalacionCasillaMunicipalDownload(HttpServletResponse response,Long idUsuario, Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)throws SeguimientoVotoException, IOException;
	
	public void getReporteInstalacionCasillaCrgDownload(HttpServletResponse response,Long idUsuario, Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)throws SeguimientoVotoException, IOException;
	
	public void getReporteInstalacionCasillaRgDownload(HttpServletResponse response,Long idUsuario, Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)throws SeguimientoVotoException, IOException;
}
