package mx.morena.negocio.servicio;

import java.util.List;

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
	
	List<ReporteDistritalDTO> getReporteDistrital(Long perfil) throws ConvencidosException;
	
	List<ReporteLocalDTO> getReporteLocal(Long perfil) throws ConvencidosException;
	
	List<ReporteMunicipalDTO> getReporteMunicipal()throws ConvencidosException;

}
