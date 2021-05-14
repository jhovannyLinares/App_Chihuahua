package mx.morena.negocio.servicios.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.PartidosDTO;
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
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.AsignacionCasillas;
import mx.morena.persistencia.entidad.Casilla;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Metas;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.Partido;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.IAsignacionCasillasRepository;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IInstalacionCasillasRepository;
import mx.morena.persistencia.repository.IMetasFederalRepository;
import mx.morena.persistencia.repository.IMunicipioRepository;
import mx.morena.persistencia.repository.IPartidosRepository;
import mx.morena.persistencia.repository.IReporteCasillasRepository;
import mx.morena.persistencia.repository.IRepresentantesAsignadosRepository;
import mx.morena.persistencia.repository.IRutasRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.persistencia.repository.IVotosPartidoAmbitoRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ReporteCasillaImpl extends MasterService implements IReporteCasillaService {

	@Autowired
	private IReporteCasillasRepository reporteCasillaRepository;

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

	@Autowired
	private IVotosPartidoAmbitoRepository votosRepository;

	@Autowired
	private IPartidosRepository partidoRepository;

	@Autowired
	private IMetasFederalRepository metaRepository;

	private String once = "11:00:00";

	private String quince = "15:00:00";

	private String dieciocho = "18:00:00";

	private String SI = "si";

//	private String NO = "no";

	@Override
	public List<ReporteVotacionDTO> getReporteVotacion(Long usuario, Long perfil, Long idReporte)
			throws CotException, IOException {

		if (perfil == PERFIL_RG) {

			Metas metas = new Metas();

			if (idReporte >= 1 && idReporte <= 5) {

				List<ReporteVotacionDTO> lstDto = new ArrayList<>();
				List<DistritoFederal> lstSeccion = null;
				ReporteVotacionDTO dto = null;
				ReporteVotacionDTO total = new ReporteVotacionDTO();

				lstSeccion = distritoFederalRepository.findAll();
				logger.debug("**** " + lstSeccion.size());

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

//				Long listaNominal = 0L;

					Long votos11 = reporteCasillaRepository.getCountByDistritoAndTipoVotacion(se.getId(), idReporte,
							once);
					Long votos15 = reporteCasillaRepository.getCountByDistritoAndTipoVotacion(se.getId(), idReporte,
							quince);
					Long votos18 = reporteCasillaRepository.getCountByDistritoAndTipoVotacion(se.getId(), idReporte,
							dieciocho);
					metas = metaRepository.getMetasByFederal(se.getId());

					dto.setIdFederal(se.getId());
					dto.setListaNominal(metas.getListaNominal());

					dto.setVotacion11hrs(votos11);
					double porcentaje11 = dosDecimales((dto.getVotacion11hrs() * 100.0) / dto.getListaNominal())
							.doubleValue();
					dto.setPorcentajeVotacion11hrs(porcentaje11);

					dto.setVotacion15hrs(votos15);
					double porcentaje15 = dosDecimales((dto.getVotacion15hrs() * 100.0) / dto.getListaNominal())
							.doubleValue();
					dto.setPorcentajeVotacion15hrs(porcentaje15);

					dto.setVotacion18hrs(votos18);
					double porcentaje18 = dosDecimales((dto.getVotacion18hrs() * 100.0) / dto.getListaNominal())
							.doubleValue();
					dto.setPorcentajeVotacion18hrs(porcentaje18);

					lstDto.add(dto);

					total.setListaNominal(total.getListaNominal() + dto.getListaNominal());
					total.setVotacion11hrs(total.getVotacion11hrs() + dto.getVotacion11hrs());
					total.setVotacion15hrs(total.getVotacion15hrs() + dto.getVotacion15hrs());
					total.setVotacion18hrs(total.getVotacion18hrs() + dto.getVotacion18hrs());

				}

				total.setPorcentajeVotacion11hrs(
						dosDecimales((total.getVotacion11hrs() * 100.0) / total.getListaNominal()).doubleValue());
				total.setPorcentajeVotacion15hrs(
						dosDecimales((total.getVotacion15hrs() * 100.0) / total.getListaNominal()).doubleValue());
				total.setPorcentajeVotacion18hrs(
						dosDecimales((total.getVotacion18hrs() * 100.0) / total.getListaNominal()).doubleValue());

				lstDto.add(total);

				return lstDto;

			} else {
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

				total.setRgPorcentaje(
						dosDecimales((total.getRgAsistencia() * 100.0) / total.getRgMeta()).doubleValue());
				total.setRcPorcentaje(
						dosDecimales((total.getRcAsistencia() * 100.0) / total.getRcMeta()).doubleValue());

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
	public void getReporteAsistenciaEstatalDownload(HttpServletResponse response, Long usuario, Long perfil,
			Long idFederal) throws CotException, IOException {

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
		Metas metas = new Metas();

//		Long rgMeta;
//		Long rcMeta;
		Long rgAsistencia = instalacionCasillaRepository.getCountRgByDfAndAsistencia(federal, SI);
		Long rcAsistencia = instalacionCasillaRepository.getCountRcByDfAndAsistencia(federal, SI);
		metas = metaRepository.getMetasByFederal(federal);

		dto.setIdFederal(federal);
		dto.setRgMeta(metas.getMetaRg());
		dto.setRcMeta(metas.getMetaRc());
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

			Metas metas = new Metas();

			List<ReporteAsistenciaFederalDTO> lstDto = new ArrayList<ReporteAsistenciaFederalDTO>();
			ReporteAsistenciaFederalDTO dto = new ReporteAsistenciaFederalDTO();

			Long distrito = 0L;

			Usuario usr = usuarioRepository.findById(usuario);
			distrito = usr.getFederal();

//			Long rgMeta = 0L;
//			Long rcMeta = 0L;
			Long rgAsistencia = instalacionCasillaRepository.getCountRgByDfAndAsistencia(distrito, SI);
			Long rcAsistencia = instalacionCasillaRepository.getCountRcByDfAndAsistencia(distrito, SI);
			metas = metaRepository.getMetasByFederal(distrito);

			dto.setIdFederal(distrito);
			dto.setRgMeta(metas.getMetaRg());
			dto.setRcMeta(metas.getMetaRc());
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
	public void getReporteAsistenciaDistritalDownload(HttpServletResponse response, Long usuario, Long perfil)
			throws CotException, IOException {

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
					logger.debug("**** " + lstCasilla.size());
					for (Casilla casilla : lstCasilla) {
						dto = getAsistenciaLocales(casilla.getLocal(), idFederal, tipo);

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
			}

			logger.debug("Lista return " + lstDto.size());
			return lstDto;

		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	public ReporteAsistenciaLocalDTO getAsistenciaLocales(Long local, Long federal, Long tipo) {
		List<ReporteAsistenciaLocalDTO> lstDto = new ArrayList<>();

		ReporteAsistenciaLocalDTO dto = new ReporteAsistenciaLocalDTO();

		Metas metas = metaRepository.getMetasByLocal(local);

		Long rgAsistencia = instalacionCasillaRepository.getCountRgByLocalAndAsistencia(local, SI, federal, tipo, 0L);
		Long rcAsistencia = instalacionCasillaRepository.getCountRcByLocalAndAsistencia(local, SI, federal, tipo, 0L);

		dto.setIdLocal(local);
		dto.setRgMeta(metas.getMetaRg());
		dto.setRcMeta(metas.getMetaRc());
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

		Metas metas = metaRepository.getMetasByMunicipio(municipio);

		Long rgAsistencia = instalacionCasillaRepository.getCountRgByLocalAndAsistencia(local, SI, federal, tipo,
				municipio);
		Long rcAsistencia = instalacionCasillaRepository.getCountRcByLocalAndAsistencia(local, SI, federal, tipo,
				municipio);

		String nomMunicipio = municipioRepository.getNombreByIdGroup(municipio);

		dto.setMunicipio(nomMunicipio);
		dto.setRgMeta(metas.getMetaRg());
		dto.setRcMeta(metas.getMetaRc());
		dto.setRgAsistencia(rgAsistencia);
		dto.setRcAsistencia(rcAsistencia);
		dto.setRgPorcentaje(dosDecimales((dto.getRgAsistencia() * 100.0) / dto.getRgMeta()).doubleValue());
		dto.setRcPorcentaje(dosDecimales((dto.getRcAsistencia() * 100.0) / dto.getRcMeta()).doubleValue());

		lstDto.add(dto);

		return dto;
	}

	@Override
	public List<ReporteResultadosDTO> getReporteResultados(Long usuario, Long perfil, Long idReporte, Long idUbicacion)
			throws CotException, IOException {

		if (perfil == PERFIL_RG) {

			Metas metas = new Metas();

			if (idReporte >= 1 && idReporte <= 5) {

//				Usuario user = usuarioRepository.findById(usuario);
//				Long idEntidad = user.getEntidad();

				List<ReporteResultadosDTO> listaDTO = new ArrayList<>();
				List<DistritoFederal> listDF = null;
				List<Partido> lstPartidos = null;
				ReporteResultadosDTO dto = null;
//				ReporteResultadosDTO total = new ReporteResultadosDTO();

				listDF = distritoFederalRepository.findAll();

				for (DistritoFederal items : listDF) {

					if (idReporte == 1) {
						lstPartidos = votosRepository.getPartidosGobernador();
					} else if (idReporte == 2) {
						if (idUbicacion == null || idUbicacion == 0) {
							throw new CotException("El Campo idUbicacion es requerido", 401);
						} else {
							lstPartidos = votosRepository.getPartidosMunicipal(idUbicacion);
						}
					} else if (idReporte == 3) {
						if (idUbicacion == null || idUbicacion == 0) {
							throw new CotException("El Campo idUbicacion es requerido", 401);
						} else {
							lstPartidos = votosRepository.getPartidosSindico(idUbicacion);
						}
					} else if (idReporte == 4) {
						lstPartidos = votosRepository.getPartidosLocal(items.getId());
					} else if (idReporte == 5) {
						throw new CotException("No se encontraron datos para el Ambito Diputado Federal", 401);
						// lstPartidos = votosRepository.getPartidosFederal();
					}
				}

				Long p1 = 0L;
				Long p2 = 0L;
				Long p3 = 0L;
				Long p4 = 0L;
				Long p5 = 0L;
				Long p6 = 0L;
				Long p7 = 0L;
				Long p8 = 0L;
				Long p9 = 0L;
				Long p10 = 0L;
				Long p11 = 0L;
				Long p12 = 0L;
				Long p13 = 0L;
				Long nulos = 0L;
				Long totalVotos = 0L;

				listDF = distritoFederalRepository.findAll();

				if (idReporte == 1 || idReporte == 2 || idReporte == 3) {

					for (DistritoFederal items : listDF) {

						dto = new ReporteResultadosDTO();

						if (lstPartidos.size() >= 1)
							p1 = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(0).getId());
						if (lstPartidos.size() >= 2)
							p2 = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(1).getId());
						if (lstPartidos.size() >= 3)
							p3 = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(2).getId());
						if (lstPartidos.size() >= 4)
							p4 = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(3).getId());
						if (lstPartidos.size() >= 5)
							p5 = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(4).getId());
						if (lstPartidos.size() >= 6)
							p6 = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(5).getId());
						if (lstPartidos.size() >= 7)
							p7 = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(6).getId());
						if (lstPartidos.size() >= 8)
							p8 = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(7).getId());
						if (lstPartidos.size() >= 9)
							p9 = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(8).getId());
						if (lstPartidos.size() >= 10)
							p10 = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(9).getId());
						if (lstPartidos.size() >= 11)
							p11 = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(10).getId());
						if (lstPartidos.size() >= 12)
							p12 = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(11).getId());
						if (lstPartidos.size() >= 13)
							p13 = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(12).getId());
						if (lstPartidos.size() >= 14)
							nulos = votosRepository.getVotosByEleccionAndPartido(items.getId(), idReporte,
									lstPartidos.get(13).getId());

						totalVotos = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9 + p10 + p11 + p12 + p13 + nulos;

						Double porcentajeNulos = 0.0;
						if (nulos > 0) {

							porcentajeNulos = dosDecimales((nulos * 100.0) / totalVotos).doubleValue();
							dto.setPorcentajeNulos(porcentajeNulos);
						}

						metas = metaRepository.getMetasByFederal(items.getId());

						Long listaNominal = metas.getListaNominal();

						dto.setIdFederal(items.getId());
						dto.setListaNominal(listaNominal);
						dto.setPartido1(p1);
						dto.setPartido2(p2);
						dto.setPartido3(p3);
						dto.setPartido4(p4);
						dto.setPartido5(p5);
						dto.setPartido6(p6);
						dto.setPartido7(p7);
						dto.setPartido8(p8);
						dto.setPartido9(p9);
						dto.setPartido10(p10);
						dto.setPartido11(p11);
						dto.setPartido12(p12);
						dto.setPartido13(p13);
						dto.setTotal(p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9 + p10 + p11 + p12 + p13);
						dto.setNulos(nulos);
						dto.setPorcentajeNulos(porcentajeNulos);

						if (dto.getTotal() != 0) {
							dto.setPorcentajePartido1(
									dosDecimales((dto.getPartido1() * 100.00) / dto.getTotal()).doubleValue());
							dto.setPorcentajePartido2(
									dosDecimales((dto.getPartido2() * 100.00) / dto.getTotal()).doubleValue());
							dto.setPorcentajePartido3(
									dosDecimales((dto.getPartido3() * 100.00) / dto.getTotal()).doubleValue());
							dto.setPorcentajePartido4(
									dosDecimales((dto.getPartido4() * 100.00) / dto.getTotal()).doubleValue());
							dto.setPorcentajePartido5(
									dosDecimales((dto.getPartido5() * 100.00) / dto.getTotal()).doubleValue());
							dto.setPorcentajePartido6(
									dosDecimales((dto.getPartido6() * 100.00) / dto.getTotal()).doubleValue());
							dto.setPorcentajePartido7(
									dosDecimales((dto.getPartido7() * 100.00) / dto.getTotal()).doubleValue());
							dto.setPorcentajePartido8(
									dosDecimales((dto.getPartido8() * 100.00) / dto.getTotal()).doubleValue());
							dto.setPorcentajePartido9(
									dosDecimales((dto.getPartido9() * 100.00) / dto.getTotal()).doubleValue());
							dto.setPorcentajePartido10(
									dosDecimales((dto.getPartido10() * 100.00) / dto.getTotal()).doubleValue());
							dto.setPorcentajePartido11(
									dosDecimales((dto.getPartido11() * 100.00) / dto.getTotal()).doubleValue());
							dto.setPorcentajePartido12(
									dosDecimales((dto.getPartido12() * 100.00) / dto.getTotal()).doubleValue());
							dto.setPorcentajePartido13(
									dosDecimales((dto.getPartido13() * 100.00) / dto.getTotal()).doubleValue());
						}

						dto.setIdFederal(items.getId());
						dto.setListaNominal(listaNominal);

						dto.setCoalicion1(votosRepository.getCoalicionByCoalicion(items.getId(), idReporte, 1L));
						dto.setCoalicion2(votosRepository.getCoalicionByCoalicion(items.getId(), idReporte, 2L));
						dto.setCoalicion3(votosRepository.getCoalicionByCoalicion(items.getId(), idReporte, 3L));
						dto.setCoalicion4(votosRepository.getCoalicionByCoalicion(items.getId(), idReporte, 4L));
						dto.setCoalicion5(votosRepository.getCoalicionByCoalicion(items.getId(), idReporte, 5L));

						dto.setIdCoalicion1(1L);
						dto.setIdCoalicion2(2L);
						dto.setIdCoalicion3(3L);
						dto.setIdCoalicion4(4L);
						dto.setIdCoalicion5(5L);

						if (dto.getCoalicion1() > 0)
							dto.setPorcentajeCoalicion1(
									dosDecimales((dto.getCoalicion1() * 100.0) / dto.getTotal()).doubleValue());

						if (dto.getCoalicion2() > 0)
							dto.setPorcentajeCoalicion2(
									dosDecimales((dto.getCoalicion2() * 100.0) / dto.getTotal()).doubleValue());

						if (dto.getCoalicion3() > 0)
							dto.setPorcentajeCoalicion3(
									dosDecimales((dto.getCoalicion3() * 100.0) / dto.getTotal()).doubleValue());

						if (dto.getCoalicion4() > 0)
							dto.setPorcentajeCoalicion4(
									dosDecimales((dto.getCoalicion4() * 100.0) / dto.getTotal()).doubleValue());

						if (dto.getCoalicion5() > 0)
							dto.setPorcentajeCoalicion5(
									dosDecimales((dto.getCoalicion5() * 100.0) / dto.getTotal()).doubleValue());

						dto.setTotal(dto.getTotal());
						double porcentajeTotal = (dto.getTotal() * 100.0) / listaNominal;
						dto.setPorcentajeTotal(dosDecimales(porcentajeTotal).doubleValue());

						listaDTO.add(dto);

					}

				} else {

					dto = new ReporteResultadosDTO();

					if (lstPartidos.size() >= 1)
						p1 = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(0).getId());
					if (lstPartidos.size() >= 2)
						p2 = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(1).getId());
					if (lstPartidos.size() >= 3)
						p3 = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(2).getId());
					if (lstPartidos.size() >= 4)
						p4 = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(3).getId());
					if (lstPartidos.size() >= 5)
						p5 = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(4).getId());
					if (lstPartidos.size() >= 6)
						p6 = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(5).getId());
					if (lstPartidos.size() >= 7)
						p7 = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(6).getId());
					if (lstPartidos.size() >= 8)
						p8 = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(7).getId());
					if (lstPartidos.size() >= 9)
						p9 = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(8).getId());
					if (lstPartidos.size() >= 10)
						p10 = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(9).getId());
					if (lstPartidos.size() >= 11)
						p11 = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(10).getId());
					if (lstPartidos.size() >= 12)
						p12 = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(11).getId());
					if (lstPartidos.size() >= 13)
						p13 = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(12).getId());
					if (lstPartidos.size() >= 14)
						nulos = votosRepository.getVotosByPartido(idReporte, lstPartidos.get(13).getId());

					totalVotos = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9 + p10 + p11 + p12 + p13 + nulos;

					Double porcentajeNulos = 0.0;
					if (nulos > 0) {

						porcentajeNulos = dosDecimales((nulos * 100.0) / totalVotos).doubleValue();
						dto.setPorcentajeNulos(porcentajeNulos);
					}

					metas = metaRepository.getMetasByMunicipio(idUbicacion);

					Long listaNominal = metas.getListaNominal();

					dto.setIdFederal(idUbicacion);
					dto.setListaNominal(listaNominal);
					dto.setPartido1(p1);
					dto.setPartido2(p2);
					dto.setPartido3(p3);
					dto.setPartido4(p4);
					dto.setPartido5(p5);
					dto.setPartido6(p6);
					dto.setPartido7(p7);
					dto.setPartido8(p8);
					dto.setPartido9(p9);
					dto.setPartido10(p10);
					dto.setPartido11(p11);
					dto.setPartido12(p12);
					dto.setPartido13(p13);
					dto.setTotal(p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9 + p10 + p11 + p12 + p13);
					dto.setNulos(nulos);
					dto.setPorcentajeNulos(porcentajeNulos);

					if (dto.getTotal() != 0) {
						dto.setPorcentajePartido1(
								dosDecimales((dto.getPartido1() * 100.00) / dto.getTotal()).doubleValue());
						dto.setPorcentajePartido2(
								dosDecimales((dto.getPartido2() * 100.00) / dto.getTotal()).doubleValue());
						dto.setPorcentajePartido3(
								dosDecimales((dto.getPartido3() * 100.00) / dto.getTotal()).doubleValue());
						dto.setPorcentajePartido4(
								dosDecimales((dto.getPartido4() * 100.00) / dto.getTotal()).doubleValue());
						dto.setPorcentajePartido5(
								dosDecimales((dto.getPartido5() * 100.00) / dto.getTotal()).doubleValue());
						dto.setPorcentajePartido6(
								dosDecimales((dto.getPartido6() * 100.00) / dto.getTotal()).doubleValue());
						dto.setPorcentajePartido7(
								dosDecimales((dto.getPartido7() * 100.00) / dto.getTotal()).doubleValue());
						dto.setPorcentajePartido8(
								dosDecimales((dto.getPartido8() * 100.00) / dto.getTotal()).doubleValue());
						dto.setPorcentajePartido9(
								dosDecimales((dto.getPartido9() * 100.00) / dto.getTotal()).doubleValue());
						dto.setPorcentajePartido10(
								dosDecimales((dto.getPartido10() * 100.00) / dto.getTotal()).doubleValue());
						dto.setPorcentajePartido11(
								dosDecimales((dto.getPartido11() * 100.00) / dto.getTotal()).doubleValue());
						dto.setPorcentajePartido12(
								dosDecimales((dto.getPartido12() * 100.00) / dto.getTotal()).doubleValue());
						dto.setPorcentajePartido13(
								dosDecimales((dto.getPartido13() * 100.00) / dto.getTotal()).doubleValue());
					}

					dto.setListaNominal(listaNominal);

					dto.setCoalicion1(votosRepository.getCoalicion(idUbicacion, idReporte, 1L));
					dto.setCoalicion2(votosRepository.getCoalicion(idUbicacion, idReporte, 2L));
					dto.setCoalicion3(votosRepository.getCoalicion(idUbicacion, idReporte, 3L));
					dto.setCoalicion4(votosRepository.getCoalicion(idUbicacion, idReporte, 4L));
					dto.setCoalicion5(votosRepository.getCoalicion(idUbicacion, idReporte, 5L));

					dto.setIdCoalicion1(1L);
					dto.setIdCoalicion2(2L);
					dto.setIdCoalicion3(3L);
					dto.setIdCoalicion4(4L);
					dto.setIdCoalicion5(5L);

					if (dto.getCoalicion1() > 0)
						dto.setPorcentajeCoalicion1(
								dosDecimales((dto.getCoalicion1() * 100.0) / dto.getTotal()).doubleValue());

					if (dto.getCoalicion2() > 0)
						dto.setPorcentajeCoalicion2(
								dosDecimales((dto.getCoalicion2() * 100.0) / dto.getTotal()).doubleValue());

					if (dto.getCoalicion3() > 0)
						dto.setPorcentajeCoalicion3(
								dosDecimales((dto.getCoalicion3() * 100.0) / dto.getTotal()).doubleValue());

					if (dto.getCoalicion4() > 0)
						dto.setPorcentajeCoalicion4(
								dosDecimales((dto.getCoalicion4() * 100.0) / dto.getTotal()).doubleValue());

					if (dto.getCoalicion5() > 0)
						dto.setPorcentajeCoalicion5(
								dosDecimales((dto.getCoalicion5() * 100.0) / dto.getTotal()).doubleValue());

					dto.setTotal(dto.getTotal());
					double porcentajeTotal = (dto.getTotal() * 100.0) / listaNominal;
					dto.setPorcentajeTotal(dosDecimales(porcentajeTotal).doubleValue());

					listaDTO.add(dto);

				}

				return listaDTO;

			} else {
				throw new CotException("Ambito incorrecto, favor de ingresar un Ambito correcto", 401);
			}

		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	@Override
	public void getReporteResultadosDownload(HttpServletResponse response, Long usuario, Long perfil, Long idReporte,
			Long idUbicacion) throws CotException, IOException {
		logger.debug("Valor de idUbicacion: " + idUbicacion);
		if (perfil == PERFIL_RG) {
			if (idReporte >= 1 && idReporte <= 5) {

				if (idReporte == 2 || idReporte == 3) {
					if (idUbicacion == null) {
						throw new CotException("El Campo idUbicacion es requerido para este Ambito.", 404);
					}
					if (idUbicacion > 67 || idUbicacion <= 0) {
						throw new CotException("Ubicacion no permitida.", 404);
					}
				}
				if (idReporte == 5)
					throw new CotException("No se encontraron datos para el Ambito Diputado Federal", 401);
				setNameFile(response, CSV_REPORTE_RESULTADOS);

				List<ReporteResultadosDTO> reporteDTOs = getReporteResultados(usuario, perfil, idReporte, idUbicacion);

				String[] header = { "idFederal", "listaNominal", "partido1", "porcentajePartido1", "partido2",
						"porcentajePartido2", "partido3", "porcentajePartido3", "partido4", "porcentajePartido4",
						"partido5", "porcentajePartido5", "partido6", "porcentajePartido6", "partido7",
						"porcentajePartido7", "partido8", "porcentajePartido8", "partido9", "porcentajePartido9",
						"partido10", "porcentajePartido10", "partido11", "porcentajePartido11", "partido12",
						"porcentajePartido12", "partido13", "porcentajePartido13", "nulos", "porcentajeNulos",
						"idCoalicion1","coalicion1", "porcentajeCoalicion1", "idCoalicion2", "coalicion2", "porcentajeCoalicion2", "idCoalicion3", "coalicion3",
						"porcentajeCoalicion3", "idCoalicion4", "coalicion4", "porcentajeCoalicion4", "idCoalicion5", "coalicion5",
						"porcentajeCoalicion5", "total", "porcentajeTotal" };

				String[] encabezadoCSV = { "NO FEDERAL", "LISTA NOMINAL", "PAN", "% PAN", "PRI", "% PRI", "PRD",
						"% PRD", "PVEM", "% PVEM", "PT", "% PT", "MC", "% MC", "MORENA", "% MORENA", "PES", "% PES",
						"RSP", "% RSP", "FUERZA POR MEXICO", "% FUERZA POR MEXICO", "PANAL", "% PANAL",
						"MORENA-PT-PANAL", "% MORENA-PT-PANAL", "PAN-PRD", "% PAN-PRD", "NULOS", "% NULOS",
						"ID_COALICION 1","COALICION 1", "% COALICION 1","ID_COALICION 2", "COALICION 2", "% COALICION 2", "ID_COALICION 3", "COALICION 3", "% COALICION 3",
						"ID_COALICION 4", "COALICION 4", "% COALICION 4", "ID_COALICION 5", "COALICION 5", "% COALICION 5", "TOTAL", "% TOTAL" };

				setWriterFile(response, reporteDTOs, header, encabezadoCSV);
			} else {
				throw new CotException("No existe el id del reporte a consultar", 404);
			}
		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

	}

	@Override
	public List<ReporteVotacionMunicipalDTO> getReporteMunicipal(Long idUsuario, Long idEleccion) throws CotException {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
		Long idEntidad = usuario.getEntidad();
		Long idDf = usuario.getFederal();
		Long idMunicipio = usuario.getMunicipio();

		List<ReporteVotacionMunicipalDTO> resultados = new ArrayList<ReporteVotacionMunicipalDTO>();
		ReporteVotacionMunicipalDTO dto = null;

		ReporteVotacionMunicipalDTO totales = new ReporteVotacionMunicipalDTO();
		totales.setIdFederal(null);
		totales.setMunicipio("Total");
		totales.setListaNominal(0L);

		List<Municipio> municipios = new ArrayList<Municipio>();

		if (perfil == PERFIL_ESTATAL) {
			municipios = municipioRepository.getByEstadoAndDfAndMunicipio(idEntidad, null, null);
		} else if (perfil == PERFIL_FEDERAL || perfil == PERFIL_LOCAL) {
			municipios = municipioRepository.getByEstadoAndDfAndMunicipio(idEntidad, idDf, null);
		} else if (perfil > PERFIL_LOCAL && perfil < PERFIL_RC) {
			municipios = municipioRepository.getByEstadoAndDfAndMunicipio(idEntidad, idDf, idMunicipio);
		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

		if (municipios != null) {
			for (Municipio municipio : municipios) {
				dto = new ReporteVotacionMunicipalDTO();
				dto = calcularReporteResultados(idEntidad, municipio.getFederalId(), municipio.getId(),
						municipio.getDescripcion(), idEleccion);

				resultados.add(dto);
				/*
				 * totales.setPanal(totales.getPanal() + dto.getPanal());
				 * totales.setNulos(totales.getNulos() + dto.getNulos());
				 * totales.setCrg(totales.getCrg() + dto.getCrg());
				 * totales.setTotal(totales.getTotal() + dto.getTotal());
				 * totales.setCandidato1(totales.getCandidato1() + dto.getCandidato1());
				 * totales.setListaNominal(totales.getListaNominal() + dto.getListaNominal());
				 */
			}
			/*
			 * totales.setPorcentajePanal(dosDecimales((totales.getPanal()*100.00)/totales.
			 * getTotal()).doubleValue());
			 * totales.setPorcentajeNulos(dosDecimales((totales.getNulos()*100.00)/totales.
			 * getTotal()).doubleValue());
			 * totales.setPorcentajeCrg(dosDecimales((totales.getCrg()*100.00)/totales.
			 * getTotal()).doubleValue());
			 * totales.setPorcentajeTotal(dosDecimales((totales.getTotal()*100.00)/totales.
			 * getListaNominal()).doubleValue());
			 * totales.setPorcentajeCandidato1(dosDecimales((totales.getCandidato1()*100.00)
			 * /totales.getTotal()).doubleValue());
			 * 
			 * resultados.add(totales);
			 */
		} else {
			throw new CotException("No se encontraron datos", 404);
		}
//		logger.debug("**************** " + resultados.size());
		return resultados;
	}

	public ReporteVotacionMunicipalDTO calcularReporteResultados(Long idEntidad, Long idDistrito, Long idMunicipio,
			String municipio, Long idEleccion) throws CotException {
		List<Partido> partidos = new ArrayList<Partido>();
		List<PartidosDTO> partidosDto = new ArrayList<PartidosDTO>();
		if (idEleccion == 1) {
			partidos = partidoRepository.getGobernadorByEntidad(idEntidad);
		} else if (idEleccion == 2) {
			partidos = partidoRepository.getMunicipalByMunicipio(idMunicipio);
		} else if (idEleccion == 3) {
//			System.err.println(idMunicipio);
			partidos = partidoRepository.getSindicoByMunicipio(idMunicipio);
		} else if (idEleccion == 4) {
			partidos = partidoRepository.getDiputadoLocalByFederal(idDistrito);
		} else if (idEleccion == 5) {
			partidos = partidoRepository.getDiputadoFederalByFederal(idDistrito);
		} else {
			throw new CotException("Ingrese un ambito valido", 400);
		}

		ReporteVotacionMunicipalDTO dto = null;
//		List<CoalicionesDTO> coaliciones = new ArrayList<CoalicionesDTO>();
//		CoalicionesDTO coalicion = null;

		Long totalVotos = 0L;

		// PartidosDTO partidoDto = new PartidosDTO();
		if (partidos != null) {
			partidosDto = MapperUtil.mapAll(partidos, PartidosDTO.class);
			dto = new ReporteVotacionMunicipalDTO();

			String partidoNulos = "NULOS";

			Long p1 = 0L;
			Long p2 = 0L;
			Long p3 = 0L;
			Long p4 = 0L;
			Long p5 = 0L;
			Long p6 = 0L;
			Long p7 = 0L;
			Long p8 = 0L;
			Long p9 = 0L;
			Long p10 = 0L;
			Long p11 = 0L;
			Long p12 = 0L;
			Long p13 = 0L;

			if (partidosDto.size() >= 1 && !partidos.get(0).getPartido().trim().equalsIgnoreCase(partidoNulos))
				p1 = votosRepository.getVotosPartidoByDistrito(idMunicipio, idDistrito, idEleccion,
						partidosDto.get(0).getId());

			if (partidosDto.size() >= 2 && !partidos.get(1).getPartido().trim().equalsIgnoreCase(partidoNulos))
				p2 = votosRepository.getVotosPartidoByDistrito(idMunicipio, idDistrito, idEleccion,
						partidosDto.get(1).getId());

			if (partidosDto.size() >= 3 && !partidos.get(2).getPartido().trim().equalsIgnoreCase(partidoNulos))
				p3 = votosRepository.getVotosPartidoByDistrito(idMunicipio, idDistrito, idEleccion,
						partidosDto.get(2).getId());

			if (partidosDto.size() >= 4 && !partidos.get(3).getPartido().trim().equalsIgnoreCase(partidoNulos))
				p4 = votosRepository.getVotosPartidoByDistrito(idMunicipio, idDistrito, idEleccion,
						partidosDto.get(3).getId());

			if (partidosDto.size() >= 5 && !partidos.get(4).getPartido().trim().equalsIgnoreCase(partidoNulos))
				p5 = votosRepository.getVotosPartidoByDistrito(idMunicipio, idDistrito, idEleccion,
						partidosDto.get(4).getId());

			if (partidosDto.size() >= 6 && !partidos.get(5).getPartido().trim().equalsIgnoreCase(partidoNulos))
				p6 = votosRepository.getVotosPartidoByDistrito(idMunicipio, idDistrito, idEleccion,
						partidosDto.get(5).getId());

			if (partidosDto.size() >= 7 && !partidos.get(6).getPartido().trim().equalsIgnoreCase(partidoNulos))
				p7 = votosRepository.getVotosPartidoByDistrito(idMunicipio, idDistrito, idEleccion,
						partidosDto.get(6).getId());

			if (partidosDto.size() >= 8 && !partidos.get(7).getPartido().trim().equalsIgnoreCase(partidoNulos))
				p8 = votosRepository.getVotosPartidoByDistrito(idMunicipio, idDistrito, idEleccion,
						partidosDto.get(7).getId());

			if (partidosDto.size() >= 9 && !partidos.get(8).getPartido().trim().equalsIgnoreCase(partidoNulos))
				p9 = votosRepository.getVotosPartidoByDistrito(idMunicipio, idDistrito, idEleccion,
						partidosDto.get(8).getId());

			if (partidosDto.size() >= 10 && !partidos.get(9).getPartido().trim().equalsIgnoreCase(partidoNulos))
				p10 = votosRepository.getVotosPartidoByDistrito(idMunicipio, idDistrito, idEleccion,
						partidosDto.get(9).getId());

			if (partidosDto.size() >= 11 && !partidos.get(10).getPartido().trim().equalsIgnoreCase(partidoNulos))
				p11 = votosRepository.getVotosPartidoByDistrito(idMunicipio, idDistrito, idEleccion,
						partidosDto.get(10).getId());

			if (partidosDto.size() >= 12 && !partidos.get(11).getPartido().trim().equalsIgnoreCase(partidoNulos))
				p12 = votosRepository.getVotosPartidoByDistrito(idMunicipio, idDistrito, idEleccion,
						partidosDto.get(11).getId());

			if (partidosDto.size() >= 13 && !partidos.get(12).getPartido().trim().equalsIgnoreCase(partidoNulos))
				p13 = votosRepository.getVotosPartidoByDistrito(idMunicipio, idDistrito, idEleccion,
						partidosDto.get(12).getId());

			dto.setPartido1(p1);
			dto.setPartido2(p2);
			dto.setPartido3(p3);
			dto.setPartido4(p4);
			dto.setPartido5(p5);
			dto.setPartido6(p6);
			dto.setPartido7(p7);
			dto.setPartido8(p8);
			dto.setPartido9(p9);
			dto.setPartido10(p10);
			dto.setPartido11(p11);
			dto.setPartido12(p12);
			dto.setPartido13(p13);

			Long nulos = votosRepository.getNulosByMunicipio(idMunicipio, idDistrito, idEleccion, partidoNulos);
			totalVotos = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9 + p10 + p11 + p12 + p13 + nulos;

			Double porcentajeNulos = 0.0;
			if (nulos > 0) {

				porcentajeNulos = dosDecimales((nulos * 100.0) / totalVotos).doubleValue();
				dto.setPorcentajeNulos(porcentajeNulos);
			}

			if (p1 > 0)
				dto.setPorcentajePartido1(dosDecimales((p1 * 100.0) / totalVotos).doubleValue());

			if (p2 > 0)
				dto.setPorcentajePartido2(dosDecimales((p2 * 100.0) / totalVotos).doubleValue());

			if (p3 > 0)
				dto.setPorcentajePartido3(dosDecimales((p3 * 100.0) / totalVotos).doubleValue());

			if (p4 > 0)
				dto.setPorcentajePartido4(dosDecimales((p4 * 100.0) / totalVotos).doubleValue());

			if (p5 > 0)
				dto.setPorcentajePartido5(dosDecimales((p5 * 100.0) / totalVotos).doubleValue());

			if (p6 > 0)
				dto.setPorcentajePartido6(dosDecimales((p6 * 100.0) / totalVotos).doubleValue());

			if (p7 > 0)
				dto.setPorcentajePartido7(dosDecimales((p7 * 100.0) / totalVotos).doubleValue());

			if (p8 > 0)
				dto.setPorcentajePartido8(dosDecimales((p8 * 100.0) / totalVotos).doubleValue());

			if (p9 > 0)
				dto.setPorcentajePartido9(dosDecimales((p9 * 100.0) / totalVotos).doubleValue());

			if (p10 > 0)
				dto.setPorcentajePartido10(dosDecimales((p10 * 100.0) / totalVotos).doubleValue());

			if (p11 > 0)
				dto.setPorcentajePartido11(dosDecimales((p11 * 100.0) / totalVotos).doubleValue());

			if (p12 > 0)
				dto.setPorcentajePartido12(dosDecimales((p12 * 100.0) / totalVotos).doubleValue());

			if (p13 > 0)
				dto.setPorcentajePartido13(dosDecimales((p13 * 100.0) / totalVotos).doubleValue());

//			logger.debug("Total: " + totalVotos);

			Metas metas = metaRepository.getMetasByMunicipio(idMunicipio);
			Long listaNominal = metas.getListaNominal();
			dto.setIdFederal(idDistrito);
			dto.setMunicipio(municipio);
			dto.setListaNominal(listaNominal);
			dto.setNulos(nulos);

			dto.setCoalicion1(votosRepository.getCoalicionByIdCoalicion(idMunicipio, idDistrito, idEleccion, 1L));
			dto.setCoalicion2(votosRepository.getCoalicionByIdCoalicion(idMunicipio, idDistrito, idEleccion, 2L));
			dto.setCoalicion3(votosRepository.getCoalicionByIdCoalicion(idMunicipio, idDistrito, idEleccion, 3L));
			dto.setCoalicion4(votosRepository.getCoalicionByIdCoalicion(idMunicipio, idDistrito, idEleccion, 4L));
			dto.setCoalicion5(votosRepository.getCoalicionByIdCoalicion(idMunicipio, idDistrito, idEleccion, 5L));

			dto.setIdCoalicion1(1L);
			dto.setIdCoalicion2(2L);
			dto.setIdCoalicion3(3L);
			dto.setIdCoalicion4(4L);
			dto.setIdCoalicion5(5L);

			if (dto.getCoalicion1() > 0)
				dto.setPorcentajeCoalicion1(dosDecimales((dto.getCoalicion1() * 100.0) / totalVotos).doubleValue());

			if (dto.getCoalicion2() > 0)
				dto.setPorcentajeCoalicion2(dosDecimales((dto.getCoalicion2() * 100.0) / totalVotos).doubleValue());

			if (dto.getCoalicion3() > 0)
				dto.setPorcentajeCoalicion3(dosDecimales((dto.getCoalicion3() * 100.0) / totalVotos).doubleValue());

			if (dto.getCoalicion4() > 0)
				dto.setPorcentajeCoalicion4(dosDecimales((dto.getCoalicion4() * 100.0) / totalVotos).doubleValue());

			if (dto.getCoalicion5() > 0)
				dto.setPorcentajeCoalicion5(dosDecimales((dto.getCoalicion5() * 100.0) / totalVotos).doubleValue());

			dto.setTotal(totalVotos);
			double porcentajeTotal = (totalVotos * 100.0) / listaNominal;
			dto.setPorcentajeTotal(dosDecimales(porcentajeTotal).doubleValue());

		} else {
			throw new CotException(
					"El municipio: " + idMunicipio + ", no tiene elecciones para el ambito " + idEleccion, 400);
		}
		return dto;

	}

	@Override
	public void getReporteMunicipioDownload(HttpServletResponse response, Long idUsuario, Long perfil, Long idEleccion)
			throws CotException, IOException {

		if (perfil >= PERFIL_ESTATAL && perfil < PERFIL_RC) {
			if (idEleccion >= 1 && idEleccion <= 5) {
				setNameFile(response, CSV_REPORTE_RESULTADOS_PMUNICIPAL);
				List<ReporteVotacionMunicipalDTO> dto = getReporteMunicipal(idUsuario, idEleccion);

				String[] header = { "idFederal", "municipio", "listaNominal", "partido1", "porcentajePartido1",
						"partido2", "porcentajePartido2", "partido3", "porcentajePartido3", "partido4",
						"porcentajePartido4", "partido5", "porcentajePartido5", "partido6", "porcentajePartido6",
						"partido7", "porcentajePartido7", "partido8", "porcentajePartido8", "partido9",
						"porcentajePartido9", "partido10", "porcentajePartido10", "partido11", "porcentajePartido11",
						"partido12", "porcentajePartido12", "partido13", "porcentajePartido13", "nulos",
						"porcentajeNulos", "total", "porcentajeTotal", "Coalicion1", "porcentajeCoalicion1",
						"Coalicion2", "porcentajeCoalicion2", "Coalicion3", "porcentajeCoalicion3", "Coalicion4",
						"porcentajeCoalicion4", "Coalicion5", "porcentajeCoalicion5" };

				String[] header2 = { "NO FEDERAL", "MUNICIPIO", "LISTA NOMINAL", "PAN", "% PAN", "PRI", "% PRI", "PRD",
						"% PRD", "PVEM", "% PVEM", "PT", "% PT", "MC", "% MC", "MORENA", "% MORENA", "PES", "% PES",
						"RSP", "% RSP", "FUERZA POR MEXICO", "% FUERZA POR MEXICO", "PANAL", "% PANAL",
						"MORENA-PT-PANAL", "% MORENA-PT-PANAL", "PAN-PRD", "% PAN-PRD", "NULOS", "% NULOS", "TOTAL",
						"% TOTAL", "COALICION 1", "% COALICION 1", "COALICION 2", "% COALICION 2", "COALICION 3",
						"% COALICION 3", "COALICION 4", "% COALICION 4", "COALICION 5", "% COALICION 5" };

				setWriterFile(response, dto, header, header2);

			} else {
				throw new CotException("Ingrese un ambito valido", 404);
			}

		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

	}

	@Override
	public void getReporteAsistenciaMunicipalDownload(HttpServletResponse response, long usuario, long perfil,
			Long idFederal, Long idLocal, Long idMunicipio) throws CotException, IOException {
		setNameFile(response, CSV_ASISTENCIA_MUNICIPAL);

		List<ReporteAsistenciaMunicipalDTO> reporteDTOs = getReporteAsistenciaMunicipal(usuario, perfil, idFederal,
				idLocal, idMunicipio);

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
				if (lstCasillas == null) {
					throw new CotException("No se encontraron datos para el usuario.", 404);
				}
				tipo = 1L;

				for (AsignacionCasillas aCasillas : lstCasillas) {

					dto = getAsistenciaCrg(idLocal, aCasillas.getFederalId(), tipo, idMunicipio, usr.getId(),
							aCasillas.getId(), aCasillas.getRuta());

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
					logger.debug("***** " + lstCasillas.size());
					for (AsignacionCasillas aCasillas : lstCasillas) {
						dto = getAsistenciaCrg(idLocal, aCasillas.getFederalId(), tipo, idMunicipio, 0L, 0L,
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

				if (idFederal != null && idMunicipio == null) {
					tipo = 3L;
					lstCasillas = asignacionCasillasRepository.getRutasByFederal(idFederal);
//					logger.debug("***** " + lstCasillas.size());
					for (AsignacionCasillas aCasillas : lstCasillas) {
						dto = getAsistenciaCrg(idLocal, idFederal, tipo, idMunicipio, 0L, 0L, aCasillas.getRuta());

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
					dto = getAsistenciaCrg(idLocal, idFederal, tipo, idMunicipio, 0L, 0L, 0L);
					lstDto.add(dto);
				}

				return lstDto;
			}

		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

		return null;
	}

	public ReporteAsistenciaCrgDTO getAsistenciaCrg(Long local, Long federal, Long tipo, Long municipio, Long idCrg,
			Long casillaId, Long casillaRuta) {
		List<ReporteAsistenciaCrgDTO> lstDto = new ArrayList<>();

		ReporteAsistenciaCrgDTO dto = new ReporteAsistenciaCrgDTO();

//		Long rgMeta = 0L;
//		Long rcMeta = 0L;
		Long rgAsistencia = instalacionCasillaRepository.getCountRgByLocalAndAsistenciaCrg(SI, idCrg, casillaRuta, tipo,
				federal, municipio);
		Long rcAsistencia = instalacionCasillaRepository.getCountRcByLocalAndAsistenciaCrg(SI, idCrg, casillaRuta, tipo,
				federal, municipio);
		Long casillas = asignacionCasillasRepository.countCasillasByIdCrgAndRuta(idCrg, casillaRuta, tipo, federal,
				municipio);

		dto.setIdFederal(federal);
		dto.setRuta(casillaRuta);
		dto.setCasillas(casillas);

		Metas metas = null;
		if (tipo == 4) {
			metas = metaRepository.getMetasByMunicipio(municipio);
		} else {
			metas = metaRepository.getMetasByFederal(federal);
		}

		dto.setRgMeta(metas.getMetaRg());
		dto.setRcMeta(metas.getMetaRc());

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

		List<ReporteAsistenciaCrgDTO> reporteDTOs = getReporteAsistenciaCrg(usuario, perfil, idFederal, idLocal,
				idMunicipio);

		String[] header = { "idFederal", "ruta", "casillas", "rgMeta", "rcMeta", "rgAsistencia", "rgPorcentaje",
				"rcAsistencia", "rcPorcentaje" };

		setWriterFile(response, reporteDTOs, header);
	}

	@Override
	public List<ReporteAsistenciaRgDTO> getReporteAsistenciaRg(Long usuario, Long perfil, Long idFederal, Long idLocal,
			Long idMunicipio, String idRutaRg) throws CotException, IOException {

		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_RG) {

			List<ReporteAsistenciaRgDTO> lstDto = new ArrayList<>();
			ReporteAsistenciaRgDTO dto = null;
			List<AsignacionCasillas> lstAsignacion = null;
//			List<Rutas> lstRutas = null;
//			List<DistritoFederal> lstDistritos = null; 
			List<Casilla> lstCasillas = null;

			Long tipo = 0L;
//			Long local = 0L;
			Long crg = 0L;
			Long rg = 0L;

			if (perfil == PERFIL_RG) {
				Long rutaId = repAsignadosRepository.getRutaIdByRepresentante(usuario);
				String RutaRg = rutasRepository.getIdRuraById(rutaId);
				lstAsignacion = asignacionCasillasRepository.getTipoCasillasByRutaRg(RutaRg);
				tipo = 1L;

				for (AsignacionCasillas aCAsillas : lstAsignacion) {
					dto = getAsistenciaRg(tipo, aCAsillas.getFederalId(), 0L, 0L, aCAsillas.getSeccionId(), crg, rg,
							aCAsillas.getIdCasilla(), aCAsillas.getTipoCasilla(), RutaRg);
					lstDto.add(dto);
				}

			}

			if (perfil == PERFIL_ESTATAL) {

				tipo = 2L;
				lstCasillas = casillasRepository.getCasillasAsistencia(idFederal, idLocal, idMunicipio, crg, rg,
						idRutaRg);

				for (Casilla casillas : lstCasillas) {
					dto = getAsistenciaRg(tipo, casillas.getFederal(), casillas.getLocal(), casillas.getMunicipio(),
							casillas.getSeccionElectoral(), crg, rg, casillas.getId(), casillas.getTipo(), idRutaRg);
					lstDto.add(dto);
				}

			}

			return lstDto;
		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	public ReporteAsistenciaRgDTO getAsistenciaRg(Long tipo, Long federal, Long local, Long municipio, Long seccion,
			Long crg, Long rg, Long IdCAsilla, String tipoCasilla, String idRutaRg) {
		List<ReporteAsistenciaRgDTO> lstDto = new ArrayList<>();

		ReporteAsistenciaRgDTO dto = new ReporteAsistenciaRgDTO();

//		Long rcMeta = 0L;
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

		List<ReporteAsistenciaRgDTO> reporteDTOs = getReporteAsistenciaRg(usuario, perfil, idFederal, idLocal,
				idMunicipio, idRutaRg);

		String[] header = { "municipio", "idFederal", "secion", "casillas", "rcMeta", "rcAsistencia", "rcPorcentaje" };

		setWriterFile(response, reporteDTOs, header);

	}
}
