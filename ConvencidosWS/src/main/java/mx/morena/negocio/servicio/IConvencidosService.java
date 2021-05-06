package mx.morena.negocio.servicio;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import mx.morena.negocio.dto.ConvencidosDTO;
import mx.morena.negocio.dto.ConvencidosResponseDTO;
import mx.morena.negocio.dto.ReporteLocalDTO;
import mx.morena.negocio.dto.ReporteDistritalDTO;
import mx.morena.negocio.dto.ReporteMunicipalDTO;
import mx.morena.negocio.exception.ConvencidosException;

public interface IConvencidosService {

	List<ConvencidosResponseDTO> getConvencidos(Long distritoFederalId, Long idMunicipio, Long idSeccion, String claveElector)
			throws ConvencidosException;

	Long save(long usuario, ConvencidosDTO dto) throws ConvencidosException;

	boolean findByClaveElector(String claveElector) throws ConvencidosException;
	
	List<ReporteDistritalDTO> getReporteDistrital(Long perfil, Long idFederal) throws ConvencidosException;
	
	List<ReporteLocalDTO> getReporteLocal(Long perfil) throws ConvencidosException;
	
	List<ReporteMunicipalDTO> getReporteMunicipal(long perfil)throws ConvencidosException;

	void getReporteDownload(HttpServletResponse response, long perfil, Long idFederal) throws ConvencidosException, IOException;
	
	public void getReporteLocalDownload(HttpServletResponse response, Long perfil) throws ConvencidosException, IOException;
	
	public void getReporteConvMunicipalDownload(HttpServletResponse response, Long perfil) throws ConvencidosException, IOException;
}
