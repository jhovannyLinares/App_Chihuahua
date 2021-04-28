package mx.morena.negocio.servicios.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ReporteAsistenciaEstatalDTO;
import mx.morena.negocio.dto.ReporteAsistenciaFederalDTO;
import mx.morena.negocio.dto.ReporteVotacionDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.IReporteCasillaService;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IInstalacionCasillasRepository;
import mx.morena.persistencia.repository.IReporteCasillasRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ReporteCasillaImpl extends MasterService implements IReporteCasillaService {
	
	@Autowired
	private IReporteCasillasRepository reporteCasillaRepository ;
		
	@Autowired
	private IDistritoFederalRepository distritoFederalRepository;
	
	@Autowired
	private IInstalacionCasillasRepository instalacionCasillaRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	private String once = "11:00:00";
	
	private String quince = "15:00:00";
	
	private String dieciocho = "18:00:00";
	
	private String SI = "si";
	
//	private String NO = "no";

	@Override
	public List<ReporteVotacionDTO> getReporteVotacion(Long usuario, Long perfil, Long idReporte)
			throws CotException, IOException {

		if (perfil == PERFIL_RG) {
			if (idReporte >= 1 && idReporte <= 5) {

			List<ReporteVotacionDTO> lstDto = new ArrayList<>();
			List<DistritoFederal> lstSeccion = null;
			ReporteVotacionDTO dto = null;
			ReporteVotacionDTO total = new ReporteVotacionDTO();
			

			lstSeccion = distritoFederalRepository.findAll();
			System.out.println("**** " + lstSeccion.size());
			
			total.setIdFederal(null);
			total.setListaNominal(0L);
			total.setVotacion11hrs(0L);
			total.setPorcentajeVotacion11hrs(0.0);
			total.setVotacion15hrs(0L);
			total.setPorcentajeVotacion15hrs(0.0);
			total.setVotacion18hrs(0L);
			total.setPorcentajeVotacion18hrs(0.0);

			for (DistritoFederal se : lstSeccion) {
				dto = new ReporteVotacionDTO();
				
				Long votos11 = reporteCasillaRepository.getCountByDistritoAndTipoVotacion(se.getId(), idReporte, once);
				Long votos15 = reporteCasillaRepository.getCountByDistritoAndTipoVotacion(se.getId(), idReporte, quince);
				Long votos18 = reporteCasillaRepository.getCountByDistritoAndTipoVotacion(se.getId(), idReporte, dieciocho);

				dto.setIdFederal(se.getId());
				dto.setListaNominal(20L);

				dto.setVotacion11hrs(votos11);
				double porcentaje11 = dosDecimales((dto.getVotacion11hrs() * 100.0)/dto.getListaNominal()).doubleValue();
				dto.setPorcentajeVotacion11hrs(porcentaje11);

				dto.setVotacion15hrs(votos15);
				double porcentaje15 = dosDecimales((dto.getVotacion15hrs() * 100.0)/dto.getListaNominal()).doubleValue();
				dto.setPorcentajeVotacion15hrs(porcentaje15);

				dto.setVotacion18hrs(votos18);
				double porcentaje18 = dosDecimales((dto.getVotacion18hrs() * 100.0)/dto.getListaNominal()).doubleValue();
				dto.setPorcentajeVotacion18hrs(porcentaje18);
				
				lstDto.add(dto);
				
				total.setListaNominal(total.getListaNominal() + dto.getListaNominal());
				total.setVotacion11hrs(total.getVotacion11hrs() + dto.getVotacion11hrs());
				total.setVotacion15hrs(total.getVotacion15hrs() + dto.getVotacion15hrs());
				total.setVotacion18hrs(total.getVotacion18hrs() + dto.getVotacion18hrs());

			}
			
			total.setPorcentajeVotacion11hrs(dosDecimales((total.getVotacion11hrs() * 100.0)/total.getListaNominal()).doubleValue());
			total.setPorcentajeVotacion15hrs(dosDecimales((total.getVotacion15hrs() * 100.0)/total.getListaNominal()).doubleValue());
			total.setPorcentajeVotacion18hrs(dosDecimales((total.getVotacion18hrs() * 100.0)/total.getListaNominal()).doubleValue());
			
			lstDto.add(total);
			
			return lstDto;
			
			}else {
				throw new CotException("No existe el id del reporte a consultar", 400);
			}
		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

	}
	
	public BigDecimal dosDecimales(double numero) {

		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;

	}

	@Override
	public void getReporteVotacionDownload(HttpServletResponse response, Long usuario, Long perfil, Long idReporte)
			throws CotException, IOException {

		if (perfil == PERFIL_RG) {
			if (idReporte >= 1 && idReporte <= 5) {

				setNameFile(response, CSV_REPORTE_VOTACION);

				List<ReporteVotacionDTO> reporteDTOs = getReporteVotacion(usuario, perfil, idReporte);

				String[] header = { "idFederal", "ListaNominal", "votacion11hrs", "porcentajeVotacion11hrs",
						"votacion15hrs", "porcentajeVotacion15hrs", "votacion18hrs", "porcentajeVotacion18hrs" };

				setWriterFile(response, reporteDTOs, header);
			} else {
				throw new CotException("No existe el id del reporte a consultar", 400);
			}
		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

	}

	@Override
	public List<ReporteAsistenciaEstatalDTO> getReporteAsistenciaEstatal(Long usuario, Long perfil)
			throws CotException, IOException {
		if (perfil == PERFIL_ESTATAL) {

			List<DistritoFederal> lstSeccion = null;
			List<ReporteAsistenciaEstatalDTO> lstDto = new ArrayList<ReporteAsistenciaEstatalDTO>();
			ReporteAsistenciaEstatalDTO dto = null;
			ReporteAsistenciaEstatalDTO total = new ReporteAsistenciaEstatalDTO();
			total.setIdFederal(null);
			total.setRgMeta(0L);
			total.setRcMeta(0L);
			total.setRgAsistencia(0L);
			total.setRcAsistencia(0L);
			total.setRgPorcentaje(0.0);
			total.setRcPorcentaje(0.0);

			lstSeccion = distritoFederalRepository.findAll();

			for (DistritoFederal df : lstSeccion) {
				dto = new ReporteAsistenciaEstatalDTO();

				Long rgMeta;
				Long rcMeta;
				Long rgAsistencia = instalacionCasillaRepository.getCountRgByDfAndAsistencia(df.getId(), SI);
				Long rcAsistencia = instalacionCasillaRepository.getCountRcByDfAndAsistencia(df.getId(), SI);

				dto.setIdFederal(df.getId());
				dto.setRgMeta(100L);
				dto.setRcMeta(100L);
				dto.setRgAsistencia(rgAsistencia);
				dto.setRcAsistencia(rcAsistencia);
				dto.setRgPorcentaje(dosDecimales((dto.getRgAsistencia() * 100.0) / dto.getRgMeta()).doubleValue());
				dto.setRcPorcentaje(dosDecimales((dto.getRcAsistencia() * 100.0) / dto.getRcMeta()).doubleValue());

				lstDto.add(dto);

				total.setRgMeta(total.getRgMeta() + dto.getRgMeta());
				total.setRcMeta(total.getRcMeta() + dto.getRcMeta());
				total.setRgAsistencia(total.getRgAsistencia() + dto.getRgAsistencia());
				total.setRcAsistencia(total.getRcAsistencia() + dto.getRcAsistencia());
			}

			lstDto.add(total);
			return lstDto;

		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

	}

	@Override
	public void getReporteAsistenciaEstatalDownload(HttpServletResponse response, Long usuario, Long perfil)
			throws CotException, IOException {

		if (perfil == PERFIL_ESTATAL) {

			setNameFile(response, CSV_ASISTENCIA_ESTATAL);

			List<ReporteAsistenciaEstatalDTO> reporteDTOs = getReporteAsistenciaEstatal(usuario, perfil);

			String[] header = { "idFederal", "rgMeta", "rcMeta", "rgAsistencia", "rcAsistencia", "rgPorcentaje",
					"rcPorcentaje" };

			setWriterFile(response, reporteDTOs, header);
		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	@Override
	public List<ReporteAsistenciaFederalDTO> getReporteAsistenciaDistrital(Long usuario, Long perfil, Long idFederal)
			throws CotException, IOException {

		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL) {

			List<ReporteAsistenciaFederalDTO> lstDto = new ArrayList<ReporteAsistenciaFederalDTO>();
			ReporteAsistenciaFederalDTO dto = new ReporteAsistenciaFederalDTO();

			Long distrito = 0L;

			if (perfil == PERFIL_FEDERAL) {
				Usuario usr = usuarioRepository.findById(usuario);
				distrito = usr.getFederal();
			}
			if (perfil == PERFIL_ESTATAL) {
				if (idFederal != null && idFederal != 0) {
					distrito = idFederal;
				} else {
					throw new CotException("Debe de ingresar un distrito federal.", 400);
				}
			}

			Long rgMeta = 0L;
			Long rcMeta = 0L;
			Long rgAsistencia = instalacionCasillaRepository.getCountRgByDfAndAsistencia(distrito, SI);
			Long rcAsistencia = instalacionCasillaRepository.getCountRcByDfAndAsistencia(distrito, SI);

			dto.setIdFederal(distrito);
			dto.setRgMeta(50L);
			dto.setRcMeta(50L);
			dto.setRgAsistencia(rgAsistencia);
			dto.setRcAsistencia(rcAsistencia);
			dto.setRgPorcentaje(dosDecimales((dto.getRgAsistencia() * 100.0) / dto.getRgMeta()).doubleValue());
			dto.setRcPorcentaje(dosDecimales((dto.getRcAsistencia() * 100.0) / dto.getRcMeta()).doubleValue());

			lstDto.add(dto);

			return lstDto;

		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

	}

	@Override
	public void getReporteAsistenciaDistritalDownload(HttpServletResponse response, Long usuario, Long perfil,
			Long idFederal) throws CotException, IOException {

					setNameFile(response, CSV_ASISTENCIA_DISTRITAL);

					List<ReporteAsistenciaFederalDTO> reporteDTOs = getReporteAsistenciaDistrital(usuario, perfil,
							idFederal);

					String[] header = { "idFederal", "rgMeta", "rcMeta", "rgAsistencia", "rcAsistencia", "rgPorcentaje",
							"rcPorcentaje" };

					setWriterFile(response, reporteDTOs, header);

				
	}
}
