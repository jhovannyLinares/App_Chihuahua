package mx.morena.negocio.servicio;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import mx.morena.negocio.dto.ReportePMunicipalDTO;
import mx.morena.negocio.dto.ReporteResultadosActasDTO;
import mx.morena.negocio.exception.ConvencidosException;

public interface IReporteResultadosService {
	
	public List<ReporteResultadosActasDTO> getReporteResultados(Long idUsuario) throws ConvencidosException;
	
	public void getReporteResultadosDownload(HttpServletResponse response, Long idUsuario, Long perfil) throws ConvencidosException, IOException;
	
	public List<ReportePMunicipalDTO> getReportePresidenteMunicipal(Long idUsuario, Long idEleccion) throws ConvencidosException;
	
	public void getReportePresidenteMunicipalDownload(HttpServletResponse response, Long idUsuario, Long perfil, Long idEleccion) throws ConvencidosException, IOException;
}
