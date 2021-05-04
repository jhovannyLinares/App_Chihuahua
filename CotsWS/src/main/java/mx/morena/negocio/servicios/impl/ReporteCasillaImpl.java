package mx.morena.negocio.servicios.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import mx.morena.negocio.dto.ReporteAsistenciaCrgDTO;
import mx.morena.negocio.dto.ReporteAsistenciaEstatalDTO;
import mx.morena.negocio.dto.ReporteAsistenciaFederalDTO;
import mx.morena.negocio.dto.ReporteAsistenciaLocalDTO;
import mx.morena.negocio.dto.ReporteAsistenciaMunicipalDTO;
import mx.morena.negocio.dto.ReporteAsistenciaRgDTO;
import mx.morena.negocio.dto.ReporteResultadosDTO;
import mx.morena.negocio.dto.ReporteVotacionDTO;
import mx.morena.negocio.dto.ReporteVotacionMunicipalDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.IReporteCasillaService;
import mx.morena.persistencia.entidad.AsignacionCasillas;
import mx.morena.persistencia.entidad.Casilla;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.Rutas;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.IAsignacionCasillasRepository;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IInstalacionCasillasRepository;
import mx.morena.persistencia.repository.IMunicipioRepository;
import mx.morena.persistencia.repository.IReporteCasillasRepository;
import mx.morena.persistencia.repository.IRepresentantesAsignadosRepository;
import mx.morena.persistencia.repository.IRutasRepository;
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
	
	@Autowired
	private IMunicipioRepository municipioRepository;
	
	@Autowired
	private IAsignacionCasillasRepository asignacionCasillasRepository;
	
	@Autowired
	private IRepresentantesAsignadosRepository repAsignadosRepository;
	
	@Autowired
	private IRutasRepository rutasRepository;
	
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
				
				Long listaNominal = 0L;
				
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

		String[] header = { "idFederal", "rgMeta", "rcMeta", "rgAsistencia", "rgPorcentaje", "rcAsistencia", 
				"rcPorcentaje" };

		setWriterFile(response, reporteDTOs, header);

	}

	@Override
	public List<ReporteAsistenciaLocalDTO> getReporteAsistenciaLocal(Long usuario, Long perfil, Long idFederal,
			Long idLocal) throws CotException, IOException {


		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_LOCAL) {

			List<ReporteAsistenciaLocalDTO> lstDto = new ArrayList<>();
			List<Casilla> lstCasilla = null;
			ReporteAsistenciaLocalDTO total = new ReporteAsistenciaLocalDTO();
			ReporteAsistenciaLocalDTO dto = null;

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
			
			if (perfil == PERFIL_LOCAL) {
				Usuario usr = usuarioRepository.findById(usuario);
				local = usr.getDistritoLocal();
				tipo = 1L;
				dto = getAsistenciaLocales(local, federal, tipo);
				lstDto.add(dto);
			}
			
			if (perfil == PERFIL_ESTATAL) {

				if (idLocal != null) {
					tipo = 2L;
					dto = getAsistenciaLocales(idLocal, idFederal, tipo);
					lstDto.add(dto);
					return lstDto;
				} else {
					tipo = 3L;
					lstCasilla = casillasRepository.getAllDistritosLocales();
					System.out.println("**** " + lstCasilla.size());
					for (Casilla casilla : lstCasilla) {
						dto = getAsistenciaLocales(casilla.getLocal(), idFederal, tipo);
						
						total.setRgMeta(total.getRgMeta() + dto.getRgMeta());
						total.setRcMeta(total.getRcMeta() + dto.getRcMeta());
						total.setRgAsistencia(total.getRgAsistencia() + dto.getRgAsistencia());
						total.setRcAsistencia(total.getRcAsistencia() + dto.getRcAsistencia());
						
						lstDto.add(dto);
					}
					
					total.setRgPorcentaje(dosDecimales((total.getRgAsistencia() * 100.0) / total.getRgMeta()).doubleValue());
					total.setRcPorcentaje(dosDecimales((total.getRcAsistencia() * 100.0) / total.getRcMeta()).doubleValue());
					
					lstDto.add(total);
					
					return lstDto;
				}
			}

			System.out.println("Lista return " + lstDto.size());
			return lstDto;
			
		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	public ReporteAsistenciaLocalDTO getAsistenciaLocales(Long local, Long federal, Long tipo) {
		List<ReporteAsistenciaLocalDTO> lstDto = new ArrayList<>();
		
		ReporteAsistenciaLocalDTO dto = new ReporteAsistenciaLocalDTO();
		
		Long rgMeta = 0L;
		Long rcMeta = 0L;
		Long rgAsistencia = instalacionCasillaRepository.getCountRgByLocalAndAsistencia(local, SI, federal, tipo, 0L);
		Long rcAsistencia = instalacionCasillaRepository.getCountRcByLocalAndAsistencia(local, SI, federal, tipo, 0L);

		dto.setIdLocal(local);
		dto.setRgMeta(50L);
		dto.setRcMeta(50L);
		dto.setRgAsistencia(rgAsistencia);
		dto.setRcAsistencia(rcAsistencia);
		dto.setRgPorcentaje(dosDecimales((dto.getRgAsistencia() * 100.0) / dto.getRgMeta()).doubleValue());
		dto.setRcPorcentaje(dosDecimales((dto.getRcAsistencia() * 100.0) / dto.getRcMeta()).doubleValue());

		lstDto.add(dto);

		return dto;
	}

	@Override
	public void getReporteAsistenciaLocalDownload(HttpServletResponse response, long usuario, long perfil,
			Long idFederal, Long idLocal) throws CotException, IOException {
		setNameFile(response, CSV_ASISTENCIA_LOCAL);

		List<ReporteAsistenciaLocalDTO> reporteDTOs = getReporteAsistenciaLocal(usuario, perfil, idFederal, idLocal);

		String[] header = { "idLocal", "rgMeta", "rcMeta", "rgAsistencia", "rgPorcentaje", "rcAsistencia", 
				"rcPorcentaje" };

		setWriterFile(response, reporteDTOs, header);
	}

	@Override
	public List<ReporteAsistenciaMunicipalDTO> getReporteAsistenciaMunicipal(long usuario, long perfil, Long idFederal,
			Long idLocal, Long idMunicipio) throws CotException, IOException {

		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_MUNICIPAL) {

			List<ReporteAsistenciaMunicipalDTO> lstDto = new ArrayList<>();
			List<Municipio> lstMunicipio = null;
			ReporteAsistenciaMunicipalDTO dto = null;
			Long tipo = 0L;

			if (perfil == PERFIL_MUNICIPAL) {
				Usuario usr = usuarioRepository.findById(usuario);
				tipo = 4L;
				dto = getAsistenciaMunicipal(idLocal, idFederal, tipo, usr.getMunicipio());
				lstDto.add(dto);

			}
			
			if (perfil == PERFIL_ESTATAL) {
				
				if (idFederal == null && idMunicipio == null) {
					tipo = 6L;
					lstMunicipio = municipioRepository.findAll();
					for (Municipio mun : lstMunicipio) {
						dto = getAsistenciaMunicipal(idLocal, mun.getFederalId(), tipo, mun.getId());
						lstDto.add(dto);
					}
				}

				if (idFederal != null && idMunicipio == null) {
					tipo = 6L;
					lstMunicipio = municipioRepository.getByFederal(idFederal);
					for (Municipio mun : lstMunicipio) {
						dto = getAsistenciaMunicipal(idLocal, idFederal, tipo, mun.getId());
						lstDto.add(dto);
					}
				}

				if (idFederal != null && idMunicipio != null) {
					tipo = 7L;
					dto = getAsistenciaMunicipal(idLocal, idFederal, tipo, idMunicipio);
					lstDto.add(dto);
				}
			}

			return lstDto;

		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	public ReporteAsistenciaMunicipalDTO getAsistenciaMunicipal(Long local, Long federal, Long tipo, Long municipio) {
		List<ReporteAsistenciaMunicipalDTO> lstDto = new ArrayList<>();
		
		ReporteAsistenciaMunicipalDTO dto = new ReporteAsistenciaMunicipalDTO();
		
		Long rgMeta = 0L;
		Long rcMeta = 0L;
		Long rgAsistencia = instalacionCasillaRepository.getCountRgByLocalAndAsistencia(local, SI, federal, tipo, municipio);
		Long rcAsistencia = instalacionCasillaRepository.getCountRcByLocalAndAsistencia(local, SI, federal, tipo, municipio);
		String nomMunicipio = municipioRepository.getNombreByIdAndDf(municipio, federal);
		
		dto.setMunicipio(nomMunicipio);
		dto.setRgMeta(50L);
		dto.setRcMeta(50L);
		dto.setRgAsistencia(rgAsistencia);
		dto.setRcAsistencia(rcAsistencia);
		dto.setRgPorcentaje(dosDecimales((dto.getRgAsistencia() * 100.0) / dto.getRgMeta()).doubleValue());
		dto.setRcPorcentaje(dosDecimales((dto.getRcAsistencia() * 100.0) / dto.getRcMeta()).doubleValue());

		lstDto.add(dto);

		return dto;
	}
	

	@Override
	public List<ReporteResultadosDTO> getReporteResultados(Long usuario, Long perfil, Long idReporte)
			throws CotException, IOException {

		if (perfil == PERFIL_RG) {

			List<ReporteResultadosDTO> listaDTO = new ArrayList<>();
			List<DistritoFederal> listDF = null;
			ReporteResultadosDTO dto = null;
			ReporteResultadosDTO total = new ReporteResultadosDTO();

			listDF = distritoFederalRepository.findAll();
			System.out.print("Tamaño de lista: " + listDF.size());

			total.setIdFederal(1L);
			total.setListaNominal(0L);
			total.setPAN(20L);
			total.setPorcentajePAN(0.0);
			total.setPRI(0L);
			total.setPorcentajePRI(0.0);
			total.setPRD(0L);
			total.setPorcentajePRD(0.0);
			total.setPVEM(0L);
			total.setPorcentajePVEM(0.0);
			total.setPT(0L);
			total.setPorcentajePT(0.0);
			total.setMC(0L);
			total.setPorcentajeMC(0.0);
			total.setMORENA(0L);
			total.setPorcentajeMORENA(0.0);
			total.setPES(0L);
			total.setPorcentajePES(0.0);
			total.setRSP(0L);
			total.setPorcentajeRSP(0.0);
			total.setFUERZAMEXICO(0L);
			total.setPorcentajeFUERZAMEXICO(0.0);
			total.setPANAL(0L);
			total.setPorcentajePANAL(0.0);
			total.setNulos(0L);
			total.setPorcentajeNulos(0.0);
			total.setCrg(0L);
			total.setPorcentajeCrg(0.0);
			total.setTotal(20L);
			total.setPorcentajeTotal(0.0);
			total.setCandidato1(0L);
			total.setPorcentajeCandidato1(0.0);
			total.setCandidato2(0L);
			total.setPorcentajeCandidato2(0.0);

			for (DistritoFederal items : listDF) {
				dto = new ReporteResultadosDTO();

				dto.setIdFederal(items.getId());
				dto.setListaNominal(20L);
				dto.setPAN(20L);
				dto.setPRI(20L);
				dto.setPRD(20L);
				dto.setPVEM(20L);
				dto.setPT(20L);
				dto.setMC(20L);
				dto.setMORENA(20L);
				dto.setPES(20L);
				dto.setRSP(20L);
				dto.setFUERZAMEXICO(20L);
				dto.setPANAL(20L);
				dto.setNulos(50L);
				dto.setCrg(20L);

				dto.setTotal(dto.getPAN() + dto.getPRI() + dto.getPRD() + dto.getPRD() + dto.getPVEM() + dto.getPT()
						+ dto.getMC() + dto.getMORENA() + dto.getPES() + dto.getRSP() + dto.getFUERZAMEXICO()
						+ dto.getPANAL() + dto.getNulos() + dto.getCrg());

				double porcentajePartido1 = dosDecimales((dto.getPAN() * 100) / dto.getTotal()).doubleValue();
				dto.setPorcentajePAN(porcentajePartido1);

				double porcentajePartido2 = dosDecimales((dto.getPRI() * 100) / dto.getTotal()).doubleValue();
				dto.setPorcentajePRI(porcentajePartido2);

				double porcentajePartido3 = dosDecimales((dto.getPRD() * 100) / dto.getTotal()).doubleValue();
				dto.setPorcentajePRD(porcentajePartido3);

				double porcentajePartido4 = dosDecimales((dto.getPVEM() * 100) / dto.getTotal()).doubleValue();
				dto.setPorcentajePVEM(porcentajePartido4);

				double porcentajePartido5 = dosDecimales((dto.getPT() * 100) / dto.getTotal()).doubleValue();
				dto.setPorcentajePT(porcentajePartido5);

				double porcentajePartido6 = dosDecimales((dto.getMC() * 100) / dto.getTotal()).doubleValue();
				dto.setPorcentajeMC(porcentajePartido6);

				double porcentajePartido7 = dosDecimales((dto.getMORENA() * 100) / dto.getTotal()).doubleValue();
				dto.setPorcentajeMORENA(porcentajePartido7);

				double porcentajePartido8 = dosDecimales((dto.getPES() * 100) / dto.getTotal()).doubleValue();
				dto.setPorcentajePES(porcentajePartido8);

				double porcentajePartido9 = dosDecimales((dto.getRSP() * 100) / dto.getTotal()).doubleValue();
				dto.setPorcentajeRSP(porcentajePartido9);

				double porcentajePartido10 = dosDecimales((dto.getFUERZAMEXICO() * 100) / dto.getTotal()).doubleValue();
				dto.setPorcentajeFUERZAMEXICO(porcentajePartido10);

				double porcentajePartido11 = dosDecimales((dto.getPANAL() * 100) / dto.getTotal()).doubleValue();
				dto.setPorcentajePANAL(porcentajePartido11);

				double porcentajeNulos = dosDecimales((dto.getNulos() * 100) / dto.getTotal()).doubleValue();
				dto.setPorcentajeNulos(porcentajeNulos);

				double porcentajeCrg = dosDecimales((dto.getCrg() * 100) / dto.getTotal()).doubleValue();
				dto.setPorcentajeCrg(porcentajeCrg);

				double porcentajeTotal = dosDecimales((dto.getTotal() / dto.getListaNominal() * 100)).doubleValue();
				dto.setPorcentajeTotal(porcentajeTotal);
				dto.setCandidato1(500L);
				double porcentajeCandidato1 = dosDecimales((dto.getCandidato1() / dto.getTotal() * 100)).doubleValue();
				dto.setPorcentajeCandidato1(porcentajeCandidato1);
				dto.setCandidato2(454L);
				double porcentajeCandidato2 = dosDecimales((dto.getCandidato2() / dto.getTotal() * 100)).doubleValue();
				dto.setPorcentajeCandidato2(porcentajeCandidato2);

				listaDTO.add(dto);

				total.setIdFederal(null);
				total.setListaNominal(total.getListaNominal() + dto.getListaNominal());
				total.setPAN(total.getPAN() + dto.getPAN());
				total.setPorcentajePAN(total.getPorcentajePAN() + dto.getPorcentajePAN());
				total.setPRI(total.getPRI() + dto.getPRI());
				total.setPorcentajePRI(total.getPorcentajePRI() + dto.getPorcentajePRI());
				total.setPRD(total.getPRD() + dto.getPRD());
				total.setPorcentajePRD(total.getPorcentajePRD() + dto.getPorcentajePRD());
				total.setPVEM(total.getPVEM() + dto.getPVEM());
				total.setPorcentajePVEM(total.getPorcentajePVEM() + dto.getPorcentajePVEM());
				total.setPT(total.getPT() + dto.getPT());
				total.setPorcentajePT(total.getPorcentajePT() + dto.getPorcentajePT());
				total.setMC(total.getMC() + dto.getMC());
				total.setPorcentajeMC(total.getPorcentajeMC() + dto.getPorcentajeMC());
				total.setMORENA(total.getMORENA() + dto.getMORENA());
				total.setPorcentajeMORENA(total.getPorcentajeMORENA() + dto.getPorcentajeMORENA());
				total.setPES(total.getPES() + dto.getPES());
				total.setPorcentajePES(total.getPorcentajePES() + dto.getPorcentajePES());
				total.setRSP(total.getRSP() + dto.getRSP());
				total.setPorcentajeRSP(total.getPorcentajeRSP() + dto.getPorcentajeRSP());
				total.setFUERZAMEXICO(total.getFUERZAMEXICO() + dto.getFUERZAMEXICO());
				total.setPorcentajeFUERZAMEXICO(total.getPorcentajeFUERZAMEXICO() + dto.getPorcentajeFUERZAMEXICO());
				total.setPANAL(total.getPANAL() + dto.getPANAL());
				total.setPorcentajePANAL(total.getPorcentajePANAL() + dto.getPorcentajePANAL());
				total.setNulos(total.getNulos() + dto.getNulos());
				total.setPorcentajeNulos(total.getPorcentajeNulos() + dto.getPorcentajeNulos());
				total.setCrg(total.getCrg() + dto.getCrg());
				total.setPorcentajeCrg(total.getPorcentajeCrg() + dto.getPorcentajeCrg());
				total.setTotal(total.getTotal() + dto.getTotal());
				total.setPorcentajeTotal(total.getPorcentajeTotal() + dto.getPorcentajeTotal());
				total.setCandidato1(total.getCandidato1() + dto.getCandidato1());
				total.setPorcentajeCandidato1(total.getCandidato1() + dto.getPorcentajeCandidato1());
				total.setCandidato2(total.getCandidato2() + dto.getCandidato2());
				total.setPorcentajeCandidato2(total.getPorcentajeCandidato2() + dto.getPorcentajeCandidato2());

			}

			listaDTO.add(total);

			return listaDTO;

		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	@Override
	public void getReporteResultadosDownload(HttpServletResponse response, Long usuario, Long perfil, Long idReporte)
			throws CotException, IOException {

		if (perfil == PERFIL_RG) {
			if (idReporte >= 1 && idReporte <= 5) {

				setNameFile(response, CSV_REPORTE_RESULTADOS);

				List<ReporteResultadosDTO> reporteDTOs = getReporteResultados(usuario, perfil, idReporte);

				String[] header = { "idFederal", "listaNominal", "PAN", "porcentajePAN", "PRI", "porcentajePRI", "PRD",
						"porcentajePRD", "PVEM", "porcentajePVEM", "PT", "porcentajePT", "MC", "porcentajeMC", "MORENA",
						"porcentajeMORENA", "PES", "porcentajePES", "RSP", "porcentajeRSP", "FUERZAMEXICO",
						"porcentajeFUERZAMEXICO", "PANAL", "porcentajePANAL", "nulos", "porcentajeNulos", "crg",
						"porcentajeCrg", "total", "porcentajeTotal", "candidato1", "porcentajeCandidato1", "candidato2",
						"porcentajeCandidato2" };

				setWriterFile(response, reporteDTOs, header);
			} else {
				throw new CotException("No existe el id del reporte a consultar", 404);
			}
		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

	}

	@Override
	public List<ReporteVotacionMunicipalDTO> getReporteMunicipal(Long usuario, Long perfil, Long idReporte)
			throws CotException, IOException {
		
		if(perfil != PERFIL_RC) {
			
			List<ReporteVotacionMunicipalDTO> listaDTO = new ArrayList<>();
			List<Municipio> listDF = null;
			ReporteVotacionMunicipalDTO dto = null;
			ReporteVotacionMunicipalDTO total = new ReporteVotacionMunicipalDTO();
			
			Long entidad = (long) 8;
			listDF = municipioRepository.getByEntidad(entidad);
			System.out.print("Tamaño de lista: " + listDF.size());
			
			total.setIdFederal(20L);
			total.setMunicipio("Municipio");
			total.setPartido1(20L);
			total.setPorcentajePartido1(0.0);
			total.setPartido2(20L);
			total.setPorcentajePartido2(0.0);
			total.setPartido3(20L);
			total.setPorcentajePartido3(0.0);
			total.setPartido4(20L);
			total.setPorcentajePartido4(0.0);
			total.setPartido5(20L);
			total.setPorcentajePartido5(0.0);
			total.setPartido6(20L);
			total.setPorcentajePartido6(0.0);
			total.setNulos(20L);
			total.setPorcentajeNulos(0.0);
			total.setCrg(20L);
			total.setPorcentajeCrg(0.0);
			total.setTotal(20L);
			total.setPorcentajeTotal(0.0);
			total.setCandidato1(0L);
			total.setPorcentajeCandidato1(0.0);
			total.setCandidato2(0L);
			total.setPorcentajeCantidato2(0.0);
			
			for (Municipio items : listDF) {
				dto = new ReporteVotacionMunicipalDTO();

				dto.setIdFederal(items.getId());
				dto.setMunicipio("Municipal");
				dto.setPartido1(10L);
				dto.setPorcentajePartido1(0.0);
				dto.setPartido2(20L);
				dto.setPorcentajePartido2(0.0);
				dto.setPartido3(20L);
				dto.setPorcentajePartido3(0.0);
				dto.setPartido4(20L);
				dto.setPorcentajePartido4(0.0);
				dto.setPartido5(20L);
				dto.setPorcentajePartido5(0.0);
				dto.setPartido6(20L);
				dto.setPorcentajePartido6(0.0);
				dto.setNulos(50L);
				dto.setPorcentajeNulos(0.0);
				dto.setCrg(20L);
				dto.setPorcentajeCrg(0.0);
				dto.setTotal(100L);
				dto.setPorcentajeTotal(0.0);
				dto.setCandidato1(500L);
				dto.setPorcentajeCandidato1(0.0);
				dto.setCandidato2(454L);
				dto.setPorcentajeCantidato2(0.0);

				listaDTO.add(dto);
				
				total.setIdFederal(null);
				total.setPartido1(total.getPartido1() + dto.getPartido1());
				total.setPorcentajePartido1(total.getPorcentajePartido1() + dto.getPorcentajePartido1());
				total.setPartido2(total.getPartido2() +  dto.getPartido2());
				total.setPorcentajePartido2(total.getPorcentajePartido2() + dto.getPorcentajePartido2());
				total.setPartido3(total.getPartido3() + dto.getPartido3());
				total.setPorcentajePartido3(total.getPorcentajePartido3() + dto.getPorcentajePartido3());
				total.setPartido4(total.getPartido4() + dto.getPartido4());
				total.setPorcentajePartido4(total.getPorcentajePartido4() + dto.getPorcentajePartido4());
				total.setPartido5(total.getPartido5() + dto.getPartido5());
				total.setPorcentajePartido5(total.getPorcentajePartido5() +  dto.getPorcentajePartido5());
				total.setPartido6(total.getPartido6() + dto.getPartido6());
				total.setPorcentajePartido6(total.getPorcentajePartido6() +  dto.getPorcentajePartido6());
				total.setNulos(total.getNulos() + dto.getNulos());
				total.setPorcentajeNulos(total.getPorcentajeNulos() + dto.getPorcentajeNulos());
				total.setCrg(total.getCrg() +  dto.getCrg());
				total.setPorcentajeCrg(total.getPorcentajeCrg() +  dto.getPorcentajeCrg());
				total.setTotal(total.getTotal() + dto.getTotal());
				total.setPorcentajeTotal(total.getPorcentajeTotal() +  dto.getPorcentajeTotal());
				total.setCandidato1(total.getCandidato1() + dto.getCandidato1());
				total.setPorcentajeCandidato1(total.getCandidato1() + dto.getPorcentajeCandidato1());
				total.setCandidato2(total.getCandidato2() + dto.getCandidato2());
				total.setPorcentajeCantidato2(total.getPorcentajeCantidato2() + dto.getPorcentajeCantidato2());
				
				

			}
			
			listaDTO.add(total);
			
			return listaDTO;
			
			
		}else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
		
	}

	@Override
	public void getReporteMunicipioDownload(HttpServletResponse response, Long usuario, Long perfil, Long idReporte)
			throws CotException, IOException {
		
		if (perfil != PERFIL_RC) {
			if (idReporte >= 1 && idReporte <= 5) {

				setNameFile(response, CSV_REPORTE_MUNICIPAL);

				List<ReporteVotacionMunicipalDTO> reporteDTOs = getReporteMunicipal(usuario, perfil, idReporte);

				String[] header = { "idFederal", "municipio", "partido1", "porcentajePartido1", "partido2",
						"porcentajePartido2", "partido3", "porcentajePartido3", "partido4", "porcentajePartido4",
						"partido5", "porcentajePartido5", "partido6", "porcentajePartido6", "nulos", "porcentajeNulos",
						"crg", "porcentajeCrg", "total", "porcentajeTotal", "candidato1", "porcentajeCandidato1",
						"candidato2", "porcentajeCantidato2" };

				setWriterFile(response, reporteDTOs, header);
			} else {
				throw new CotException("No existe el id del reporte a consultar", 404);
			}
		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

	}

	@Override
	public void getReporteAsistenciaMunicipalDownload(HttpServletResponse response, long usuario, long perfil,
			Long idFederal, Long idLocal, Long idMunicipio) throws CotException, IOException {
		setNameFile(response, CSV_ASISTENCIA_MUNICIPAL);

		List<ReporteAsistenciaMunicipalDTO> reporteDTOs = getReporteAsistenciaMunicipal(usuario, perfil, idFederal, idLocal, idMunicipio);

		String[] header = { "municipio", "rgMeta", "rcMeta", "rgAsistencia", "rgPorcentaje", "rcAsistencia", 
				"rcPorcentaje" };

		setWriterFile(response, reporteDTOs, header);
	}

	@Override
	public List<ReporteAsistenciaCrgDTO> getReporteAsistenciaCrg(long usuario, long perfil, Long idFederal,
			Long idLocal, Long idMunicipio) throws CotException, IOException {

		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_CRG) {

			List<ReporteAsistenciaCrgDTO> lstDto = new ArrayList<>();
			ReporteAsistenciaCrgDTO dto = null;
			ReporteAsistenciaCrgDTO total = new ReporteAsistenciaCrgDTO();
			List<AsignacionCasillas> lstCasillas = null;
			Long tipo = 0L;

			total.setIdFederal(null);
			total.setCasillas(0L);
			total.setRgMeta(0L);
			total.setRcMeta(0L);
			total.setRgAsistencia(0L);
			total.setRcAsistencia(0L);
			total.setRgPorcentaje(0.0);
			total.setRcPorcentaje(0.0);

			if (perfil == PERFIL_CRG) {
				Usuario usr = usuarioRepository.findById(usuario);
				lstCasillas = asignacionCasillasRepository.getRutasByIdCrg(usr.getId());
				tipo = 1L;

				for (AsignacionCasillas aCasillas : lstCasillas) {

					dto = getAsistenciaCrg(idLocal, idFederal, tipo, idMunicipio, usr.getId(), aCasillas.getId(),
							aCasillas.getFederalId(), aCasillas.getRuta());

					total.setCasillas(total.getCasillas() + dto.getCasillas());
					total.setRgMeta(total.getRgMeta() + dto.getRgMeta());
					total.setRcMeta(total.getRcMeta() + dto.getRcMeta());
					total.setRgAsistencia(total.getRgAsistencia() + dto.getRgAsistencia());
					total.setRcAsistencia(total.getRcAsistencia() + dto.getRcAsistencia());

					lstDto.add(dto);

				}

				total.setRgPorcentaje(
						dosDecimales((total.getRgAsistencia() * 100.0) / total.getRgMeta()).doubleValue());
				total.setRcPorcentaje(
						dosDecimales((total.getRcAsistencia() * 100.0) / total.getRcMeta()).doubleValue());

				lstDto.add(total);

				return lstDto;
			}

			if (perfil == PERFIL_ESTATAL) {

				if (idFederal == null && idMunicipio == null) {
					tipo = 2L;
					lstCasillas = asignacionCasillasRepository.getAll();
					System.out.println("***** " + lstCasillas.size());
					for (AsignacionCasillas aCasillas : lstCasillas) {
						dto = getAsistenciaCrg(idLocal, aCasillas.getFederalId(), tipo, idMunicipio, 0L, 0L,
								aCasillas.getFederalId(), aCasillas.getRuta());

						total.setCasillas(total.getCasillas() + dto.getCasillas());
						total.setRgMeta(total.getRgMeta() + dto.getRgMeta());
						total.setRcMeta(total.getRcMeta() + dto.getRcMeta());
						total.setRgAsistencia(total.getRgAsistencia() + dto.getRgAsistencia());
						total.setRcAsistencia(total.getRcAsistencia() + dto.getRcAsistencia());

						lstDto.add(dto);
					}
					total.setRgPorcentaje(
							dosDecimales((total.getRgAsistencia() * 100.0) / total.getRgMeta()).doubleValue());
					total.setRcPorcentaje(
							dosDecimales((total.getRcAsistencia() * 100.0) / total.getRcMeta()).doubleValue());

					lstDto.add(total);
				}

				if (idFederal != null && idMunicipio == null) {
					tipo = 3L;
					lstCasillas = asignacionCasillasRepository.getRutasByFederal(idFederal);
//					System.out.println("***** " + lstCasillas.size());
					for (AsignacionCasillas aCasillas : lstCasillas) {
						dto = getAsistenciaCrg(idLocal, idFederal, tipo, idMunicipio, 0L, 0L, idFederal,
								aCasillas.getRuta());

						total.setCasillas(total.getCasillas() + dto.getCasillas());
						total.setRgMeta(total.getRgMeta() + dto.getRgMeta());
						total.setRcMeta(total.getRcMeta() + dto.getRcMeta());
						total.setRgAsistencia(total.getRgAsistencia() + dto.getRgAsistencia());
						total.setRcAsistencia(total.getRcAsistencia() + dto.getRcAsistencia());

						lstDto.add(dto);
					}

					total.setRgPorcentaje(
							dosDecimales((total.getRgAsistencia() * 100.0) / total.getRgMeta()).doubleValue());
					total.setRcPorcentaje(
							dosDecimales((total.getRcAsistencia() * 100.0) / total.getRcMeta()).doubleValue());

					lstDto.add(total);
				}

				if (idFederal != null && idMunicipio != null) {
					tipo = 4L;
					dto = getAsistenciaCrg(idLocal, idFederal, tipo, idMunicipio, 0L, 0L, idFederal, 0L);
					lstDto.add(dto);
				}

				return lstDto;
			}

		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

		return null;
	}
	
	public ReporteAsistenciaCrgDTO getAsistenciaCrg(Long local, Long federal, Long tipo, Long municipio, Long idCrg, Long casillaId, Long casillaIdFederal, Long casillaRuta) {
		List<ReporteAsistenciaCrgDTO> lstDto = new ArrayList<>();
		
		ReporteAsistenciaCrgDTO dto = new ReporteAsistenciaCrgDTO();
		
		Long rgMeta = 0L;
		Long rcMeta = 0L;
		Long rgAsistencia = instalacionCasillaRepository.getCountRgByLocalAndAsistenciaCrg(SI, idCrg, casillaRuta, tipo, federal, municipio);
		Long rcAsistencia = instalacionCasillaRepository.getCountRcByLocalAndAsistenciaCrg(SI, idCrg, casillaRuta, tipo, federal, municipio);
		Long casillas = asignacionCasillasRepository.countCasillasByIdCrgAndRuta(idCrg, casillaRuta, tipo, federal, municipio);
		
		dto.setIdFederal(casillaIdFederal);
		dto.setRuta(casillaRuta);
		dto.setCasillas(casillas);
		
		dto.setRgMeta(50L);
		dto.setRcMeta(50L);
		dto.setRgAsistencia(rgAsistencia);
		dto.setRcAsistencia(rcAsistencia);
		dto.setRgPorcentaje(dosDecimales((dto.getRgAsistencia() * 100.0) / dto.getRgMeta()).doubleValue());
		dto.setRcPorcentaje(dosDecimales((dto.getRcAsistencia() * 100.0) / dto.getRcMeta()).doubleValue());

		lstDto.add(dto);

		return dto;
	}

	@Override
	public void getReporteAsistenciaCrgDownload(HttpServletResponse response, long usuario, long perfil, Long idFederal,
			Long idLocal, Long idMunicipio) throws CotException, IOException {
		setNameFile(response, CSV_ASISTENCIA_CRG);

		List<ReporteAsistenciaCrgDTO> reporteDTOs = getReporteAsistenciaCrg(usuario, perfil, idFederal, idLocal, idMunicipio);

		String[] header = {"idFederal", "ruta", "casillas", "rgMeta", "rcMeta", "rgAsistencia", "rgPorcentaje", "rcAsistencia", 
				"rcPorcentaje" };

		setWriterFile(response, reporteDTOs, header);
	}

	@Override
	public List<ReporteAsistenciaRgDTO> getReporteAsistenciaRg(Long usuario, Long perfil, Long idFederal, Long idLocal,
			Long idMunicipio, String idRutaRg) throws CotException, IOException {
		
		
		if(perfil == PERFIL_ESTATAL || perfil == PERFIL_RG) {
			
			List<ReporteAsistenciaRgDTO> lstDto = new ArrayList<>();
			ReporteAsistenciaRgDTO dto = null;
			List<AsignacionCasillas> lstAsignacion = null;
			List<Rutas> lstRutas = null;
			List<DistritoFederal> lstDistritos = null; 
			List<Casilla> lstCasillas = null;
			
			Long tipo = 0L;
			Long local = 0L;
			Long crg = 0L;
			Long rg = 0L;
			
			
			if (perfil == PERFIL_RG) {
				Long rutaId = repAsignadosRepository.getRutaIdByRepresentante(usuario);
				String RutaRg = rutasRepository.getIdRuraById(rutaId);
				lstAsignacion = asignacionCasillasRepository.getTipoCasillasByRutaRg(RutaRg);
				tipo = 1L;
				
				for (AsignacionCasillas aCAsillas : lstAsignacion) {
					dto = getAsistenciaRg(tipo, aCAsillas.getFederalId(), 0L, 0L, aCAsillas.getSeccionId(), crg, rg, aCAsillas.getIdCasilla(), aCAsillas.getTipoCasilla(), RutaRg);
					lstDto.add(dto);
				}
			
			}
			
			if (perfil == PERFIL_ESTATAL) {
				
				tipo = 2L;
				lstCasillas = casillasRepository.getCasillasAsistencia(idFederal, idLocal, idMunicipio, crg, rg, idRutaRg);
				
				for (Casilla casillas : lstCasillas) {
					dto = getAsistenciaRg(tipo, casillas.getFederal(), casillas.getLocal(),  casillas.getMunicipio(), casillas.getSeccionElectoral(), crg, rg, casillas.getId(), casillas.getTipo(), idRutaRg);
					lstDto.add(dto);
				}
				
			}
			
			return lstDto;
		}else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}
	
	public ReporteAsistenciaRgDTO getAsistenciaRg(Long tipo, Long federal, Long local,  Long municipio, Long seccion, Long crg, Long rg, Long IdCAsilla, String tipoCasilla, String idRutaRg) {
		List<ReporteAsistenciaRgDTO> lstDto = new ArrayList<>();
		
		ReporteAsistenciaRgDTO dto = new ReporteAsistenciaRgDTO();
		
		Long rcMeta = 0L;
		Long rcAsistencia = 0L;
		
		if (tipo == 1L) {
			rcAsistencia = instalacionCasillaRepository.getCountRcByRutaRg(SI, federal, local, municipio, seccion, crg,
					rg, tipo, tipoCasilla, idRutaRg);
		}
		if (tipo == 2L) {
			rcAsistencia = instalacionCasillaRepository.getCountRcByAll(SI, federal, local, municipio, seccion, crg, rg,
					tipo, tipoCasilla, idRutaRg);
		}
		
		Long idMunicipio = casillasRepository.getIdMunicipioByIdCasilla(IdCAsilla);
		String nomMunicipio = municipioRepository.getNombreByIdAndDf(idMunicipio, federal);
		
		dto.setMunicipio(nomMunicipio);
		dto.setIdFederal(federal);
		dto.setSecion(seccion);
		dto.setCasillas(tipoCasilla);
		
		dto.setRcMeta(50L);
		dto.setRcAsistencia(rcAsistencia);
		dto.setRcPorcentaje(dosDecimales((dto.getRcAsistencia() * 100.0) / dto.getRcMeta()).doubleValue());

		lstDto.add(dto);

		return dto;
	}
	

	@Override
	public void getReporteAsistenciaRgDownload(HttpServletResponse response, long usuario, long perfil, Long idFederal,
			Long idLocal, Long idMunicipio, String idRutaRg) throws CotException, IOException {

		setNameFile(response, CSV_ASISTENCIA_RG);

		List<ReporteAsistenciaRgDTO> reporteDTOs = getReporteAsistenciaRg(usuario, perfil, idFederal, idLocal, idMunicipio, idRutaRg);

		String[] header = {"municipio", "idFederal", "secion", "casillas", "rcMeta", "rcAsistencia", 
				"rcPorcentaje" };

		setWriterFile(response, reporteDTOs, header);
		
	}
}
