package mx.morena.negocio.servicio.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ReporteResultadosActasDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IReporteResultadosService;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IEnvioActasRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ReporteResultadosServiceImpl extends MasterService implements IReporteResultadosService {
	
	@Autowired
	private ICasillaRepository casillasRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private IDistritoFederalRepository distritoFederalRepository;
	
	@Autowired
	private IEnvioActasRepository envioActasRepository;
	
	@Override
	public List<ReporteResultadosActasDTO> getReporteResultados(Long idUsuario) throws ConvencidosException {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
		Long idEntidad = usuario.getEntidad();
		Long idDf = usuario.getFederal();
		
		List<ReporteResultadosActasDTO> resultados = new ArrayList<ReporteResultadosActasDTO>();
		ReporteResultadosActasDTO dto = null;
		
		ReporteResultadosActasDTO totales = new ReporteResultadosActasDTO();
		totales.setIdFederal("Totales:");
		totales.setCasillas(0L);
		totales.setActasGobernador(0L);
		totales.setPorcentajeActasGobernador(0.0);
		totales.setActasPresidenteMunicipal(0L);
		totales.setActasSindico(0L);
		totales.setActasDiputadoLocal(0L);
		totales.setActasDiputadoFederal(0L);
		
		if (perfil == PERFIL_ESTATAL) {
			List<DistritoFederal> lstDistritos = new ArrayList<DistritoFederal>();
			lstDistritos = distritoFederalRepository.findByEntidad(idEntidad);

			for (DistritoFederal df : lstDistritos) {
				dto = new ReporteResultadosActasDTO();
				dto = calcularReporteActas(idEntidad, df.getId(), df.getCabeceraFederal());
				
				resultados.add(dto);
				
				totales.setCasillas(totales.getCasillas()+dto.getCasillas());
				totales.setActasGobernador(totales.getActasGobernador()+dto.getActasGobernador());
				totales.setActasPresidenteMunicipal(totales.getActasPresidenteMunicipal()+dto.getActasPresidenteMunicipal());
				totales.setActasSindico(totales.getActasSindico()+dto.getActasSindico());
				totales.setActasDiputadoLocal(totales.getActasDiputadoLocal()+dto.getActasDiputadoLocal());
				totales.setActasDiputadoFederal(totales.getActasDiputadoFederal()+dto.getActasDiputadoFederal());
			}
			
			totales.setPorcentajeActasGobernador(dosDecimales((totales.getActasGobernador()*100.00)/totales.getCasillas()).doubleValue());
			totales.setPorcentajeActasDiputadoFederal(dosDecimales((totales.getActasDiputadoFederal()*100.00)/totales.getCasillas()).doubleValue());
			totales.setPorcentajeActasDiputadoLocal(dosDecimales((totales.getActasDiputadoLocal()*100.00)/totales.getCasillas()).doubleValue());
			totales.setPorcentajeActasSindico(dosDecimales((totales.getActasSindico()*100.00)/totales.getCasillas()).doubleValue());
			totales.setPorcentajeActasMunicipal(dosDecimales((totales.getActasPresidenteMunicipal()*100.00)/totales.getCasillas()).doubleValue());
			
			resultados.add(totales);
			
		} else if (perfil >= PERFIL_FEDERAL && perfil < PERFIL_RC) {
			DistritoFederal distrito = distritoFederalRepository.findById(idDf);
			dto = new ReporteResultadosActasDTO();
			dto = calcularReporteActas(idEntidad, distrito.getId(), distrito.getCabeceraFederal());
			
			resultados.add(dto);
		} else {
			throw new ConvencidosException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
		
		return resultados;
	}
	
	public ReporteResultadosActasDTO calcularReporteActas(Long idEntidad, Long idDistrito, String distrito) {
		
		Long countCasillas = casillasRepository.countByEntidadAndFederal(idEntidad, idDistrito);
		Long actasGob = envioActasRepository.countByTipoVotacionAndDistrito(idDistrito, 1L);
		Long actasPreMun = envioActasRepository.countByTipoVotacionAndDistrito(idDistrito, 2L);
		Long actasSindico = envioActasRepository.countByTipoVotacionAndDistrito(idDistrito, 3L);
		Long actasDipLocal = envioActasRepository.countByTipoVotacionAndDistrito(idDistrito, 4L);
		Long actasDipFed = envioActasRepository.countByTipoVotacionAndDistrito(idDistrito, 5L);
		
		ReporteResultadosActasDTO dto = new ReporteResultadosActasDTO();
		
		dto.setIdFederal(distrito);
		dto.setCasillas(countCasillas);
		dto.setActasGobernador(actasGob);
		double porcentajeActasGob = (actasGob * 100.0) / countCasillas ;
		dto.setPorcentajeActasGobernador(dosDecimales(porcentajeActasGob).doubleValue());
		dto.setActasPresidenteMunicipal(actasPreMun);
		double porcentajeActasMun = (actasPreMun * 100.0) / countCasillas ;
		dto.setPorcentajeActasMunicipal(dosDecimales(porcentajeActasMun).doubleValue());
		dto.setActasSindico(actasSindico);
		double porcentajeActasSindico = (actasSindico * 100.0) / countCasillas ;
		dto.setPorcentajeActasSindico(dosDecimales(porcentajeActasSindico).doubleValue());
		dto.setActasDiputadoLocal(actasDipLocal);
		double porcentajeActasLocal = (actasDipLocal * 100.0) / countCasillas ;
		dto.setPorcentajeActasDiputadoLocal(dosDecimales(porcentajeActasLocal).doubleValue());
		dto.setActasDiputadoFederal(actasDipFed);
		double porcentajeActasFederal = (actasDipFed * 100.0) / countCasillas ;
		dto.setPorcentajeActasDiputadoFederal(dosDecimales(porcentajeActasFederal).doubleValue());
		
		return dto;
	}

	@Override
	public void getReporteResultadosDownload(HttpServletResponse response, Long idUsuario, Long perfil) throws ConvencidosException, IOException {
		
		if (perfil >= PERFIL_ESTATAL && perfil < PERFIL_RC) {
			
			setNameFile(response, CSV_REPORTE_RESULTADOS_ACTAS);
			List<ReporteResultadosActasDTO> dto = getReporteResultados(idUsuario);
					
			String[] header = { "idFederal", "casillas", "actasGobernador", "porcentajeActasGobernador", "actasPresidenteMunicipal", "porcentajeActasMunicipal",
					"actasSindico", "porcentajeActasSindico", "actasDiputadoLocal", "porcentajeActasDiputadoLocal", "actasDiputadoFederal", "porcentajeActasDiputadoFederal" };
			
			String[] header2 = { "DTTO FED", "CASILLAS", "ACTAS GOBERNADOR", "% ACTAS", "ACTAS PRESIDENTE MUNICIPAL", "% ACTAS",
					"ACTAS SINDICO", "% ACTAS", "ACTAS DIPUTADO LOCAL", "% ACTAS", "ACTAS DIPUTADO FEDERAL", "% ACTAS" };

			setWriterFile(response, dto, header, header2);
		} else {
			throw new ConvencidosException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}
	
	public BigDecimal dosDecimales(double numero) {
		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;
	}
	
}
