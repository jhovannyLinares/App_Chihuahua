package mx.morena.negocio.servicio.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ReporteCapacitacionEstatalDTO;
import mx.morena.negocio.dto.ReporteCapacitacionRgDTO;
import mx.morena.negocio.exception.JornadaException;
import mx.morena.negocio.servicio.IReportesJornadaService;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.ICapacitacionRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ReportesJornadaServiceImpl extends MasterService implements IReportesJornadaService {
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private IDistritoFederalRepository distritoRepository;
	
	@Autowired
	private ICapacitacionRepository capacitacionRepository;
	
	@Override
	public List<ReporteCapacitacionEstatalDTO> getReporteCapEstatal(Long idUsuario, Long idDistritoFederal) throws JornadaException {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
		Long idEstado = usuario.getEntidad();
		
		if (perfil == PERFIL_ESTATAL) {
			List<ReporteCapacitacionEstatalDTO> reporteDto = new ArrayList<ReporteCapacitacionEstatalDTO>();
			ReporteCapacitacionEstatalDTO dto = null;
			ReporteCapacitacionEstatalDTO totales = new ReporteCapacitacionEstatalDTO();

			totales.setNumero(null);
			totales.setDistritoFederal("Total:");
			totales.setMetaRG(0L);
			totales.setAvanceCapacitacionRG(0L);
			totales.setPorcentajeCapacitacionRG(0.0);
			totales.setAvanceEntregaNombramientoRG(0L);
			totales.setPorcentajeAvanceEntregaRG(0.0);
			totales.setMetaRC(0L);
			totales.setAvanceCapacitacionRC(0L);
			totales.setPorcentajeCapacitacionRC(0.0);
			totales.setAvanceEntregaNombramientoRC(0L);
			totales.setPorcentajeAvanceEntregaRC(0.0);
			
			if (idDistritoFederal != null && idDistritoFederal > 0) {
				DistritoFederal distrito = distritoRepository.findById(idDistritoFederal);
				dto = new ReporteCapacitacionEstatalDTO();
				dto = calculosReporteCapEstatal(idEstado, idDistritoFederal, distrito.getCabeceraFederal());

				reporteDto.add(dto);
				
				totales.setMetaRG(totales.getMetaRG() + dto.getMetaRG());
				totales.setAvanceCapacitacionRG(totales.getAvanceCapacitacionRG() + dto.getAvanceCapacitacionRG());
				totales.setAvanceEntregaNombramientoRG(totales.getAvanceEntregaNombramientoRG() + dto.getAvanceEntregaNombramientoRG());

				totales.setMetaRC(totales.getMetaRC() + dto.getMetaRC());
				totales.setAvanceCapacitacionRC(totales.getAvanceCapacitacionRC() + dto.getAvanceCapacitacionRC());
				totales.setAvanceEntregaNombramientoRC(totales.getAvanceEntregaNombramientoRC() + dto.getAvanceEntregaNombramientoRC());
							
			} else if (idDistritoFederal == null) {
				List<DistritoFederal> distritos = distritoRepository.findByEntidad(idEstado);
				
				for (DistritoFederal df : distritos) {
					dto = new ReporteCapacitacionEstatalDTO();
					
					dto = calculosReporteCapEstatal(idEstado, df.getId(), df.getCabeceraFederal());
					
					reporteDto.add(dto);
					
					totales.setMetaRG(totales.getMetaRG() + dto.getMetaRG());
					totales.setAvanceCapacitacionRG(totales.getAvanceCapacitacionRG() + dto.getAvanceCapacitacionRG());
					totales.setAvanceEntregaNombramientoRG(totales.getAvanceEntregaNombramientoRG() + dto.getAvanceEntregaNombramientoRG());

					totales.setMetaRC(totales.getMetaRC() + dto.getMetaRC());
					totales.setAvanceCapacitacionRC(totales.getAvanceCapacitacionRC() + dto.getAvanceCapacitacionRC());
					totales.setAvanceEntregaNombramientoRC(totales.getAvanceEntregaNombramientoRC() + dto.getAvanceEntregaNombramientoRC());
				}
				
				totales.setPorcentajeCapacitacionRG(
						dosDecimales((totales.getAvanceCapacitacionRG() * 100.0) / totales.getMetaRG()).doubleValue());
				totales.setPorcentajeAvanceEntregaRG(
						dosDecimales((totales.getAvanceEntregaNombramientoRG()* 100.0) / totales.getMetaRG()).doubleValue());
				totales.setPorcentajeCapacitacionRC(
						dosDecimales((totales.getAvanceCapacitacionRC() * 100.0) / totales.getMetaRC()).doubleValue());
				totales.setPorcentajeAvanceEntregaRC(
						dosDecimales((totales.getAvanceEntregaNombramientoRC() * 100.0) / totales.getMetaRC()).doubleValue());

				reporteDto.add(totales);
				
			} else {
				throw new JornadaException("Ingrese un distrito valido", 400);
			}
			
			return reporteDto;
			
		} else {
			throw new JornadaException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}
	
	public BigDecimal dosDecimales(double numero) {

		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;

	}
	
	public ReporteCapacitacionEstatalDTO calculosReporteCapEstatal(Long idEstado, Long idDf, String df) {
		ReporteCapacitacionEstatalDTO dto = new ReporteCapacitacionEstatalDTO();
		
		Long capacitacionRg = capacitacionRepository.getCapacitacionByDfAndRepresentante(idEstado, idDf, PERFIL_RG, SI_TOMO_CAPACITACION);
		Long nombramientoRg = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, idDf, PERFIL_RG, SI_TOMO_CAPACITACION, true);
		Long capacitacionRc = capacitacionRepository.getCapacitacionByDfAndRepresentante(idEstado, idDf, PERFIL_RC, SI_TOMO_CAPACITACION);
		Long nombramientoRc = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, idDf, PERFIL_RC, SI_TOMO_CAPACITACION, true);

		dto.setNumero(idDf);
		dto.setDistritoFederal(df);
		dto.setMetaRG(30L);
		dto.setAvanceCapacitacionRG(capacitacionRg);
		double porcentajeCapRG = (dto.getAvanceCapacitacionRG() * 100.0) / dto.getMetaRG();
		dto.setPorcentajeCapacitacionRG(dosDecimales(porcentajeCapRG).doubleValue());
		dto.setAvanceEntregaNombramientoRG(nombramientoRg);
		double porcentajeEntregaRG = (dto.getAvanceEntregaNombramientoRG() * 100.0) / dto.getMetaRG();
		dto.setPorcentajeAvanceEntregaRG(dosDecimales(porcentajeEntregaRG).doubleValue());
		dto.setMetaRC(40L);
		dto.setAvanceCapacitacionRC(capacitacionRc);
		double porcentajeCapRC = (dto.getAvanceCapacitacionRC() * 100.0) / dto.getMetaRC();
		dto.setPorcentajeCapacitacionRC(dosDecimales(porcentajeCapRC).doubleValue());
		dto.setAvanceEntregaNombramientoRC(nombramientoRc);
		double porcentajeEntregaRC = (dto.getAvanceEntregaNombramientoRC() * 100.0) / dto.getMetaRC();
		dto.setPorcentajeAvanceEntregaRC(dosDecimales(porcentajeEntregaRC).doubleValue());
		
		return dto;
	}

	@Override
	public void getReporteCapEstatalDownload(HttpServletResponse response, Long idUsuario, Long idDistritoFederal) throws JornadaException, IOException {
		setNameFile(response, CSV_CAPACITACION_NOMB_ESTATAL);

		List<ReporteCapacitacionEstatalDTO> reporteDTOs = getReporteCapEstatal(idUsuario, idDistritoFederal);

		String[] header = { "numero", "distritoFederal", "metaRG", "avanceCapacitacionRG", "porcentajeCapacitacionRG", "avanceEntregaNombramientoRG",
				"porcentajeAvanceEntregaRG", "metaRC", "avanceCapacitacionRC", "porcentajeCapacitacionRC", "avanceEntregaNombramientoRC", "porcentajeAvanceEntregaRC" };

		setWriterFile(response, reporteDTOs, header);
	}

	@Override
	public List<ReporteCapacitacionRgDTO> getReporteRg(Long idEntidad, Long idFederal) throws JornadaException {

		ReporteCapacitacionRgDTO dto = new ReporteCapacitacionRgDTO();
		List<ReporteCapacitacionRgDTO> lstDto = new ArrayList<ReporteCapacitacionRgDTO>();

		dto.setMetaRC(45L);
		long avanceCapacitacion = capacitacionRepository.getCountCapacitacionRC(idEntidad, idFederal, PERFIL_RC);
		dto.setAvanceCapacitacionRC(avanceCapacitacion);

		double porcentajeCapacitacion = (avanceCapacitacion * 100.0) / dto.getMetaRC();
		dto.setPorcentajeCapacitacionRC(dosDecimales(porcentajeCapacitacion).doubleValue());

		long avanceNombramiento = capacitacionRepository.getCountRcNombramiento(idEntidad, idFederal, PERFIL_RC, true);
		dto.setAvanceEntregaNombramientoRC(avanceNombramiento);

		if (avanceCapacitacion != 0) {
			double porcentajeNombramiento = (avanceNombramiento * 100.0) / avanceCapacitacion;
			dto.setPorcentajeAvanceEntregaRC(dosDecimales(porcentajeNombramiento).doubleValue());
		} else {
			dto.setPorcentajeAvanceEntregaRC(0.0);
		}

		lstDto.add(dto);

		return lstDto;
	}

}
