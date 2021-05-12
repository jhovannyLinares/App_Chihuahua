package mx.morena.negocio.servicio.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ReporteCapacitacionCrgDTO;
import mx.morena.negocio.dto.ReporteCapacitacionDistritalDTO;
import mx.morena.negocio.dto.ReporteCapacitacionEstatalDTO;
import mx.morena.negocio.dto.ReporteCapacitacionRgDTO;
import mx.morena.negocio.exception.JornadaException;
import mx.morena.negocio.servicio.IReportesJornadaService;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Metas;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.ICapacitacionRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IMetasFederalRepository;
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
	
	@Autowired
	private IMetasFederalRepository metaFedralRepository;
	
	@Override
	public List<ReporteCapacitacionEstatalDTO> getReporteCapEstatal(Long idUsuario, Long idEntidad, Long idDistritoFederal) throws JornadaException {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
		Long idEstado = 0L;
		Long idDistrito = 0L;
		
		if (idEntidad != null) {
			idEstado = idEntidad;
		} else {
			idEstado = usuario.getEntidad();
		}
		
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
			
		} else if (perfil != PERFIL_RC && perfil != PERFIL_BRIGADISTA) {
			List<ReporteCapacitacionEstatalDTO> reporteDto = new ArrayList<ReporteCapacitacionEstatalDTO>();
			ReporteCapacitacionEstatalDTO dto = null;
			
			if (idDistritoFederal != null) {
				idDistrito = idDistritoFederal;
			} else {
				idDistrito = usuario.getFederal();
			}
			
			DistritoFederal distrito = distritoRepository.findById(idDistrito);
			
			dto = new ReporteCapacitacionEstatalDTO();
			dto = calculosReporteCapEstatal(idEstado, idDistrito, distrito.getCabeceraFederal());

			reporteDto.add(dto);
			
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
		Long nombramientoRg = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, idDf, PERFIL_RG, true);
		Long capacitacionRc = capacitacionRepository.getCapacitacionByDfAndRepresentante(idEstado, idDf, PERFIL_RC, SI_TOMO_CAPACITACION);
		Long nombramientoRc = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, idDf, PERFIL_RC, true);
		Long metaRg = 30L;
		Long metaRc = 40L;
		
		dto.setNumero(idDf);
		dto.setDistritoFederal(df);
		dto.setMetaRG(metaRg);
		dto.setAvanceCapacitacionRG(capacitacionRg);
		double porcentajeCapRG = (dto.getAvanceCapacitacionRG() * 100.0) / dto.getMetaRG();
		dto.setPorcentajeCapacitacionRG(dosDecimales(porcentajeCapRG).doubleValue());
		dto.setAvanceEntregaNombramientoRG(nombramientoRg);
		double porcentajeEntregaRG = (dto.getAvanceEntregaNombramientoRG() * 100.0) / dto.getMetaRG();
		dto.setPorcentajeAvanceEntregaRG(dosDecimales(porcentajeEntregaRG).doubleValue());
		dto.setMetaRC(metaRc);
		dto.setAvanceCapacitacionRC(capacitacionRc);
		double porcentajeCapRC = (dto.getAvanceCapacitacionRC() * 100.0) / dto.getMetaRC();
		dto.setPorcentajeCapacitacionRC(dosDecimales(porcentajeCapRC).doubleValue());
		dto.setAvanceEntregaNombramientoRC(nombramientoRc);
		double porcentajeEntregaRC = (dto.getAvanceEntregaNombramientoRC() * 100.0) / dto.getMetaRC();
		dto.setPorcentajeAvanceEntregaRC(dosDecimales(porcentajeEntregaRC).doubleValue());
		
		return dto;
	}

	@Override
	public void getReporteCapEstatalDownload(HttpServletResponse response, Long idUsuario, Long idEntidad, Long idDistritoFederal, Long perfil)
														throws JornadaException, IOException {
		if (perfil != PERFIL_RC && perfil != PERFIL_BRIGADISTA) {
			
			setNameFile(response, CSV_CAPACITACION_NOMB_ESTATAL);
			
			List<ReporteCapacitacionEstatalDTO> reporteDTOs = getReporteCapEstatal(idUsuario, idEntidad, idDistritoFederal);

			String[] header = { "numero", "distritoFederal", "metaRG", "avanceCapacitacionRG", "porcentajeCapacitacionRG", "avanceEntregaNombramientoRG",
					"porcentajeAvanceEntregaRG", "metaRC", "avanceCapacitacionRC", "porcentajeCapacitacionRC", "avanceEntregaNombramientoRC", "porcentajeAvanceEntregaRC" };

			setWriterFile(response, reporteDTOs, header);
		} else {
			throw new JornadaException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
		
	}

	@Override
	public List<ReporteCapacitacionRgDTO> getReporteRg(Long usuario, Long idEntidad, Long idFederal, Long idCrg, Long idRg) throws JornadaException {

		ReporteCapacitacionRgDTO dto = new ReporteCapacitacionRgDTO();
		List<ReporteCapacitacionRgDTO> lstDto = new ArrayList<ReporteCapacitacionRgDTO>();

		Metas metas = metaFedralRepository.getMetasByFederal(idFederal);
		
		dto.setMetaRC(metas.getMetaRc());
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

	@Override
	public void getReporteRgDownload(HttpServletResponse response, long usuario, Long idEntidad, Long idFederal,
			Long idCrg, Long idRg) throws JornadaException, IOException {
		
		// Asignacion de nombre al archivo CSV
				setNameFile(response, CSV_CAPACITACION_RG);

				List<ReporteCapacitacionRgDTO> convDTOs = getReporteRg(usuario, idEntidad, idFederal, idCrg, idRg);

				//Nombre y orden de los encabezados en el excel
				String[] header = { "metaRC", "avanceCapacitacionRC", "porcentajeCapacitacionRC", "avanceEntregaNombramientoRC", "porcentajeAvanceEntregaRC" };

				setWriterFile(response, convDTOs, header);
		
	}




	@Override
	public List<ReporteCapacitacionDistritalDTO> getReporteCapDistrital(Long idUsuario, Long idEstatal, Long idFederal) throws JornadaException {
		
		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
		Long idEstado = usuario.getEntidad();
		Long df = usuario.getFederal();
		
		if (perfil != PERFIL_RC) {
			
			Metas metas = new Metas();
			
			if(idEstatal != null && idFederal != null) {
			
				List<ReporteCapacitacionDistritalDTO> reporteDistDto = new ArrayList<ReporteCapacitacionDistritalDTO>();
				ReporteCapacitacionDistritalDTO dto = null;
				
				
	
					dto = new ReporteCapacitacionDistritalDTO();
					String dist = distritoRepository.findDstritoFederal(idFederal);
					
					Long capacitacionRg = capacitacionRepository.getCapacitacionByDfAndRepresentante(idEstado, idFederal, PERFIL_RG, SI_TOMO_CAPACITACION);
					Long nombramientoRg = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, idFederal, PERFIL_RG, true);
					Long capacitacionRc = capacitacionRepository.getCapacitacionByDfAndRepresentante(idEstado, idFederal, PERFIL_RC, SI_TOMO_CAPACITACION);
					Long nombramientoRc = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, idFederal, PERFIL_RC, true);
					metas = metaFedralRepository.getMetasByFederal(idFederal);
	
					dto.setNumero(idFederal);
					dto.setDistritoFederal(idFederal+"-"+dist);
					dto.setMetaRG(metas.getMetaRg());
					dto.setAvanceCapacitacionRG(capacitacionRg);
					dto.setPorcentajeCapacitacionRG(dosDecimales((capacitacionRg * 100.00)/ metas.getMetaRg()).doubleValue());
					dto.setAvanceEntregaNombramientoRG(nombramientoRg);
					dto.setPorcentajeAvanceEntregaRG(dosDecimales((nombramientoRg * 100.00)/ metas.getMetaRg()).doubleValue());
					dto.setMetaRC(metas.getMetaRc());
					dto.setAvanceCapacitacionRC(capacitacionRc);
					dto.setPorcentajeCapacitacionRC(dosDecimales((capacitacionRc * 100.00)/ metas.getMetaRc()).doubleValue());
					dto.setAvanceEntregaNombramientoRC(nombramientoRc);
					dto.setPorcentajeAvanceEntregaRC(dosDecimales((nombramientoRc * 100.00)/ metas.getMetaRc()).doubleValue());
					reporteDistDto.add(dto);
				
				return reporteDistDto;
			}else {
				if(perfil == PERFIL_ESTATAL) {
					
					List<ReporteCapacitacionDistritalDTO> reporteDto = new ArrayList<ReporteCapacitacionDistritalDTO>();
					ReporteCapacitacionDistritalDTO dto = null;
					ReporteCapacitacionDistritalDTO totales = new ReporteCapacitacionDistritalDTO();

					totales.setNumero(0L);
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
					
					List<DistritoFederal> distritos = distritoRepository.findByEntidad(idEstado);
					
					for (DistritoFederal df1 : distritos) {
						dto = new ReporteCapacitacionDistritalDTO();
						
						Long capacitacionRg = capacitacionRepository.getCapacitacionByDfAndRepresentante(idEstado, df1.getId(), PERFIL_RG, SI_TOMO_CAPACITACION);
						Long nombramientoRg = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, df1.getId(), PERFIL_RG, true);
						Long capacitacionRc = capacitacionRepository.getCapacitacionByDfAndRepresentante(idEstado, df1.getId(), PERFIL_RC, SI_TOMO_CAPACITACION);
						Long nombramientoRc = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, df1.getId(), PERFIL_RC, true);
						metas = metaFedralRepository.getMetasByFederal(df1.getId());
		
						dto.setNumero(df1.getId());
						dto.setDistritoFederal(df1.getCabeceraFederal());
						dto.setMetaRG(metas.getMetaRg());
						dto.setAvanceCapacitacionRG(capacitacionRg);
						dto.setPorcentajeCapacitacionRG(dosDecimales((capacitacionRg * 100.00)/ metas.getMetaRg()).doubleValue());
						dto.setAvanceEntregaNombramientoRG(nombramientoRg);
						dto.setPorcentajeAvanceEntregaRG(dosDecimales((nombramientoRg * 100.00)/ metas.getMetaRg()).doubleValue());
						dto.setMetaRC(metas.getMetaRc());
						dto.setAvanceCapacitacionRC(capacitacionRc);
						dto.setPorcentajeCapacitacionRC(dosDecimales((capacitacionRc * 100.00)/ metas.getMetaRc()).doubleValue());
						dto.setAvanceEntregaNombramientoRC(nombramientoRc);
						dto.setPorcentajeAvanceEntregaRC(dosDecimales((nombramientoRc * 100.00)/ metas.getMetaRc()).doubleValue());
						
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
					
					return reporteDto;
					
				}else {
					List<ReporteCapacitacionDistritalDTO> reporteDistDto = new ArrayList<ReporteCapacitacionDistritalDTO>();
					ReporteCapacitacionDistritalDTO dto = null;
		
						dto = new ReporteCapacitacionDistritalDTO();
						String dist = distritoRepository.findDstritoFederal(df);
						
						Long capacitacionRg = capacitacionRepository.getCapacitacionByDfAndRepresentante(idEstado, df, PERFIL_RG, SI_TOMO_CAPACITACION);
						Long nombramientoRg = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, df, PERFIL_RG, true);
						Long capacitacionRc = capacitacionRepository.getCapacitacionByDfAndRepresentante(idEstado, df, PERFIL_RC, SI_TOMO_CAPACITACION);
						Long nombramientoRc = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, df, PERFIL_RC, true);
						metas = metaFedralRepository.getMetasByFederal(df);
		
						dto.setNumero(df);
						dto.setDistritoFederal(df+"-"+dist);
						dto.setMetaRG(metas.getMetaRg());
						dto.setAvanceCapacitacionRG(capacitacionRg);
						dto.setPorcentajeCapacitacionRG(dosDecimales((capacitacionRg * 100.00)/ metas.getMetaRg()).doubleValue());
						dto.setAvanceEntregaNombramientoRG(nombramientoRg);
						dto.setPorcentajeAvanceEntregaRG(dosDecimales((nombramientoRg * 100.00)/ metas.getMetaRg()).doubleValue());
						dto.setMetaRC(metas.getMetaRc());
						dto.setAvanceCapacitacionRC(capacitacionRc);
						dto.setPorcentajeCapacitacionRC(dosDecimales((capacitacionRc * 100.00)/ metas.getMetaRc()).doubleValue());
						dto.setAvanceEntregaNombramientoRC(nombramientoRc);
						dto.setPorcentajeAvanceEntregaRC(dosDecimales((nombramientoRc * 100.00)/ metas.getMetaRc()).doubleValue());
						reporteDistDto.add(dto);
					
					return reporteDistDto;
				}
			}
		} else {
			throw new JornadaException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	@Override
	public void getReporteDistritalDownload(HttpServletResponse response, Long idUsuario, Long idEstatal, Long idFederal) throws JornadaException, IOException {

		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
		
		if(perfil != PERFIL_RC) {
			// Asignacion de nombre al archivo CSV
			setNameFile(response, CSV_CAP_DISTRITAL);

			List<ReporteCapacitacionDistritalDTO> distritalDTOs = getReporteCapDistrital(idUsuario, idEstatal, idFederal);

			//Nombre y orden de los encabezados en el excel
			String[] header = { "numero", "distritoFederal", "metaRG", "avanceCapacitacionRG", "porcentajeCapacitacionRG", "avanceEntregaNombramientoRG",
					"porcentajeAvanceEntregaRG", "metaRC", "avanceCapacitacionRC", "porcentajeCapacitacionRC", "avanceEntregaNombramientoRC", "porcentajeAvanceEntregaRC"};

			setWriterFile(response, distritalDTOs, header);
			
		} else {
			throw new JornadaException("No cuenta con permisos suficientes para descargar el reporte", 401); 
		}
		
	}
	
	@Override
	public List<ReporteCapacitacionCrgDTO> getReporteCapCrg(Long idUsuario, Long idEntidad, Long idFederal) throws JornadaException {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long idDistrito = usuario.getFederal(); 
		Long perfil = usuario.getPerfil();
		Long idEstado = usuario.getEntidad();
		
		if(perfil != PERFIL_RC) {
		List<ReporteCapacitacionCrgDTO> reporteDto = new ArrayList<ReporteCapacitacionCrgDTO>();
		ReporteCapacitacionCrgDTO dto = null;
		ReporteCapacitacionCrgDTO totales = new ReporteCapacitacionCrgDTO();

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

		
			dto = new ReporteCapacitacionCrgDTO();
			Long capacitacionRg = capacitacionRepository.getCapacitacionByDfAndRepresentante(idEstado, idDistrito,
					PERFIL_CRG, SI_TOMO_CAPACITACION);
			Long nombramientoRg = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, idDistrito,
					PERFIL_CRG, true);
			Long capacitacionRc = capacitacionRepository.getCapacitacionByDfAndRepresentante(idEstado, idDistrito,
					PERFIL_CRG, SI_TOMO_CAPACITACION);
			Long nombramientoRc = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, idDistrito,
					PERFIL_CRG, true);

			dto.setMetaRG(30L);
			dto.setAvanceCapacitacionRG(capacitacionRg);
			double porcentajeCapRG = (dto.getAvanceCapacitacionRG() * 100.0) / dto.getMetaRG();
			dto.setPorcentajeCapacitacionRG(dosDecimales(porcentajeCapRG).doubleValue());
			dto.setAvanceEntregaNombramientoRG(nombramientoRg);
			double porcentajeCapAvace = (dto.getAvanceEntregaNombramientoRG() * 100.0) / dto.getMetaRG();
			dto.setPorcentajeAvanceEntregaRG(dosDecimales(porcentajeCapAvace).doubleValue());
			dto.setMetaRC(40L);
			dto.setAvanceCapacitacionRC(capacitacionRc);
			double porcentajeCapRC = (dto.getAvanceCapacitacionRC() * 100.0) / dto.getMetaRC();
			dto.setPorcentajeCapacitacionRC(dosDecimales(porcentajeCapRC).doubleValue());
			dto.setAvanceEntregaNombramientoRC(nombramientoRc);
			double porcentajeAvanceEntrRC= (dto.getAvanceEntregaNombramientoRC() * 100.0) / dto.getMetaRC();
			dto.setPorcentajeAvanceEntregaRC(dosDecimales(porcentajeAvanceEntrRC).doubleValue());
			reporteDto.add(dto);
		

		return reporteDto;
		
		}else {
			throw new JornadaException("No cuenta con permisos suficientes para descargar el reporte", 401); 
		}

	}

	/* ICJ- Metodo encargado de descagar el reporte en excel */
	@Override
	public void getReporteDownload(HttpServletResponse response, Long idUsuario, Long idEntidad, Long idFederal) throws JornadaException, IOException {

		// Asignacion de nombre al archivo CSV
		setNameFile(response, CSV_CAPACITACION_CRG);

		List<ReporteCapacitacionCrgDTO> capacitacionCrgDTOs = getReporteCapCrg(idUsuario, idEntidad, idFederal);

		// Nombre y orden de los encabezados en el excel
		String[] header = { "metaRG", "avanceCapacitacionRG", "porcentajeCapacitacionRG", "avanceEntregaNombramientoRG",
				"porcentajeAvanceEntregaRG", "metaRC", "avanceCapacitacionRC", "porcentajeCapacitacionRC",
				"avanceEntregaNombramientoRC", "porcentajeAvanceEntregaRC" };

		setWriterFile(response, capacitacionCrgDTOs, header);

	}
	
}
