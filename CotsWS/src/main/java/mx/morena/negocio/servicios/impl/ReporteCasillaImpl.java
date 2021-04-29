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
import mx.morena.negocio.dto.ReporteAsistenciaLocalDTO;
import mx.morena.negocio.dto.ReporteVotacionDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.IReporteCasillaService;
import mx.morena.persistencia.entidad.Casilla;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.ICasillaRepository;
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
	
	@Autowired
	private ICasillaRepository casillasRepository;
	
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
	public List<ReporteAsistenciaEstatalDTO> getReporteAsistenciaEstatal(Long usuario, Long perfil, Long idFederal)
			throws CotException, IOException {
		if (perfil == PERFIL_ESTATAL) {

			List<DistritoFederal> lstSeccion = null;
			List<ReporteAsistenciaEstatalDTO> lstDto = new ArrayList<ReporteAsistenciaEstatalDTO>();
			List<ReporteAsistenciaEstatalDTO> lstDtoDf = null;
			ReporteAsistenciaEstatalDTO dto = null;
			ReporteAsistenciaEstatalDTO total = new ReporteAsistenciaEstatalDTO();
			total.setIdFederal(null);
			total.setRgMeta(0L);
			total.setRcMeta(0L);
			total.setRgAsistencia(0L);
			total.setRcAsistencia(0L);
			total.setRgPorcentaje(0.0);
			total.setRcPorcentaje(0.0);

			if (idFederal == null) {
				lstSeccion = distritoFederalRepository.findAll();
				for (DistritoFederal df : lstSeccion) {

					dto = getAsistenciaEstatal(df.getId());
					lstDto.add(dto);
					
					total.setRgMeta(total.getRgMeta() + dto.getRgMeta());
					total.setRcMeta(total.getRcMeta() + dto.getRcMeta());
					total.setRgAsistencia(total.getRgAsistencia() + dto.getRgAsistencia());
					total.setRcAsistencia(total.getRcAsistencia() + dto.getRcAsistencia());

				}
				
				total.setRgPorcentaje(dosDecimales((total.getRgAsistencia() * 100.0) / total.getRgMeta()).doubleValue());
				total.setRcPorcentaje(dosDecimales((total.getRcAsistencia() * 100.0) / total.getRcMeta()).doubleValue());
				
				lstDto.add(total);

				return lstDto;

			} else {

				dto = getAsistenciaEstatal(idFederal);
				lstDto.add(dto);
			}

			return lstDto;

		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	@Override
	public void getReporteAsistenciaEstatalDownload(HttpServletResponse response, Long usuario, Long perfil, Long idFederal)
			throws CotException, IOException {

			setNameFile(response, CSV_ASISTENCIA_ESTATAL);

			List<ReporteAsistenciaEstatalDTO> reporteDTOs = getReporteAsistenciaEstatal(usuario, perfil, idFederal);

			String[] header = { "idFederal", "rgMeta", "rcMeta", "rgAsistencia", "rgPorcentaje", "rcAsistencia", 
					"rcPorcentaje" };

			setWriterFile(response, reporteDTOs, header);
	}
	
	public ReporteAsistenciaEstatalDTO getAsistenciaEstatal(Long federal) {
		List<ReporteAsistenciaEstatalDTO> lstDto = new ArrayList<>();
		ReporteAsistenciaEstatalDTO dto = new ReporteAsistenciaEstatalDTO();

		dto = new ReporteAsistenciaEstatalDTO();

		Long rgMeta;
		Long rcMeta;
		Long rgAsistencia = instalacionCasillaRepository.getCountRgByDfAndAsistencia(federal, SI);
		Long rcAsistencia = instalacionCasillaRepository.getCountRcByDfAndAsistencia(federal, SI);

		dto.setIdFederal(federal);
		dto.setRgMeta(100L);
		dto.setRcMeta(100L);
		dto.setRgAsistencia(rgAsistencia);
		dto.setRcAsistencia(rcAsistencia);
		dto.setRgPorcentaje(dosDecimales((dto.getRgAsistencia() * 100.0) / dto.getRgMeta()).doubleValue());
		dto.setRcPorcentaje(dosDecimales((dto.getRcAsistencia() * 100.0) / dto.getRcMeta()).doubleValue());

		lstDto.add(dto);

		return dto;
	}

	@Override
	public List<ReporteAsistenciaFederalDTO> getReporteAsistenciaDistrital(Long usuario, Long perfil)
			throws CotException, IOException {

		if (perfil == PERFIL_FEDERAL) {

			List<ReporteAsistenciaFederalDTO> lstDto = new ArrayList<ReporteAsistenciaFederalDTO>();
			ReporteAsistenciaFederalDTO dto = new ReporteAsistenciaFederalDTO();

			Long distrito = 0L;

			Usuario usr = usuarioRepository.findById(usuario);
			distrito = usr.getFederal();

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
	public void getReporteAsistenciaDistritalDownload(HttpServletResponse response, Long usuario, Long perfil) throws CotException, IOException {

		setNameFile(response, CSV_ASISTENCIA_DISTRITAL);

		List<ReporteAsistenciaFederalDTO> reporteDTOs = getReporteAsistenciaDistrital(usuario, perfil);

		String[] header = { "idFederal", "rgMeta", "rcMeta", "rgAsistencia", "rcAsistencia", "rgPorcentaje",
				"rcPorcentaje" };

		setWriterFile(response, reporteDTOs, header);

	}

	@Override
	public List<ReporteAsistenciaLocalDTO> getReporteAsistenciaLocal(Long usuario, Long perfil, Long idFederal,
			Long idLocal) throws CotException, IOException {

		boolean estFed = perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL;

		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_LOCAL) {

			List<ReporteAsistenciaLocalDTO> lstDto = new ArrayList<>();
			List<ReporteAsistenciaLocalDTO> lstDtoForLocales = null;
			List<Casilla> lstCasilla = null;
			ReporteAsistenciaLocalDTO total = new ReporteAsistenciaLocalDTO();

			Long local = 0L;
			Long federal = 0L;
			Long tipo = 0L;

			total.setIdLocal(null);
			total.setRgMeta(0L);
			total.setRcMeta(0L);
			total.setRgAsistencia(0L);
			total.setRcAsistencia(0L);
			total.setRgPorcentaje(0.0);
			total.setRcPorcentaje(0.0);
			Long rgMeta = 0L;
			Long rcMeta = 0L;
			Usuario usr = usuarioRepository.findById(usuario);

			if (perfil == PERFIL_LOCAL) {
				local = usr.getLocalidad();
				tipo = 1L;
				lstDto = getAsistenciaLocales(local, federal, tipo);
			}

			if (perfil == PERFIL_FEDERAL) {
				tipo = 2L;
				if (idLocal != null) {
					federal = usr.getFederal();
					local = idLocal;
					lstDto = getAsistenciaLocales(local, federal, tipo);
				} else {
					federal = usr.getFederal();
					lstCasilla = casillasRepository.getLocalesByFederal(usr.getFederal());
					System.out.println("********* " + lstCasilla.size());
					
					for (Casilla casilla : lstCasilla) {
						lstDtoForLocales = getAsistenciaLocales(casilla.getLocal(), federal, tipo);
						lstDto.addAll(lstDtoForLocales);
					}
					
					System.out.println("Lista for " + lstDto.size());
					return lstDto;
				}
			}
			
			if (perfil == PERFIL_ESTATAL) {
//				tipo = 3L;
				if (idFederal == null && idLocal == null) {
					tipo = 1L;
					lstCasilla = casillasRepository.getAllDistritosLocales();
					System.out.println("**** " + lstCasilla.size());
					for (Casilla casilla : lstCasilla) {
						lstDtoForLocales = getAsistenciaLocales(casilla.getLocal(), federal, tipo);
						lstDto.addAll(lstDtoForLocales);
					}
					return lstDto;
				}
			}

//			if (perfil == PERFIL_ESTATAL) {
//				if (idFederal != null && idLocal != null) {
//					federal = idFederal;
//					local = idLocal;
//					tipo = 3L;
//				} else {
//					throw new CotException("Debe de ingresar la informacion correspondiente.", 400);
//				}
//			}

//			lstDto = getAsistenciaLocales(local, federal, tipo, dto);
			System.out.println("Lista return " + lstDto.size());
			return lstDto;
			
		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	public List<ReporteAsistenciaLocalDTO> getAsistenciaLocales(Long local, Long federal, Long tipo) {
		List<ReporteAsistenciaLocalDTO> lstDto = new ArrayList<>();
		
		ReporteAsistenciaLocalDTO dto = new ReporteAsistenciaLocalDTO();
		

		Long rgAsistencia = instalacionCasillaRepository.getCountRgByLocalAndAsistencia(local, SI, federal, tipo);
		Long rcAsistencia = instalacionCasillaRepository.getCountRcByLocalAndAsistencia(local, SI, federal, tipo);

		dto.setIdLocal(local);
		dto.setRgMeta(50L);
		dto.setRcMeta(50L);
		dto.setRgAsistencia(rgAsistencia);
		dto.setRcAsistencia(rcAsistencia);
		dto.setRgPorcentaje(dosDecimales((dto.getRgAsistencia() * 100.0) / dto.getRgMeta()).doubleValue());
		dto.setRcPorcentaje(dosDecimales((dto.getRcAsistencia() * 100.0) / dto.getRcMeta()).doubleValue());

		lstDto.add(dto);

		return lstDto;
	}
}
