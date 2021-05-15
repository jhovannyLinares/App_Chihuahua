package mx.morena.negocio.servicios.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ReporteInstalacionCasillaCrgDTO;
import mx.morena.negocio.dto.ReporteInstalacionCasillaDTO;
import mx.morena.negocio.dto.ReporteInstalacionCasillaMuniDTO;
import mx.morena.negocio.dto.ReporteInstalacionCasillaRgDTO;
import mx.morena.negocio.dto.ReporteSeguimintoVotoDTO;
import mx.morena.negocio.dto.SeguimientoVotoDTO;
import mx.morena.negocio.exception.SeguimientoVotoException;
import mx.morena.negocio.servicios.IReporteSeguimientoVotoService;
import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Rutas;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IConvencidosRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;
import mx.morena.persistencia.repository.ISeguimientoVotoRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ReporteSeguimientoVotoImpl extends MasterService implements IReporteSeguimientoVotoService {

	@Autowired
	private ISeguimientoVotoRepository seguimientoRepository;

	@Autowired
	private ISeccionElectoralRepository seccionRepository;

	@Autowired
	private ICasillaRepository casillasRepository;

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Autowired
	private IConvencidosRepository convencidoRepository;

	@Autowired
	private IDistritoFederalRepository distritoRepository;

	@Override
	public List<ReporteSeguimintoVotoDTO> getSeguimeitoVoto(Long perfil, Long usuario) throws SeguimientoVotoException {
		
		Usuario idUsuario = usuarioRepository.findById(usuario);
		Long federal = idUsuario.getFederal();
//		Long local = idUsuario.getDistritoLocal();
		Long municipal = idUsuario.getMunicipio();
		
		
		List<ReporteSeguimintoVotoDTO> lstvoto = new ArrayList<ReporteSeguimintoVotoDTO>();
		List<SeccionElectoral> lstSeccion = null;
		ReporteSeguimintoVotoDTO dto = null;
		ReporteSeguimintoVotoDTO totales = new ReporteSeguimintoVotoDTO();
		
		totales.setIdDistrito(0L);
		totales.setDistrito("Totales");
		totales.setSecciones(0L);
		totales.setUrbanas(0L);
		totales.setNoUrbanas(0L);
		totales.setMetaConvencidos(0L);
		totales.setTotalConvencidos(0L);
		totales.setPorcentajeAvanceConvencidos(0.00);
		totales.setNotificado(0L);
		totales.setPorcentajeAvanceNotificado(0.00);
		
		if (perfil == PERFIL_ESTATAL) {
			
			lstSeccion = seccionRepository.getDistritos();

			for (SeccionElectoral seccion : lstSeccion) {

				Long countSecciones = seccionRepository.getSecciones(seccion.getDistritoId());
				Long urbanas = casillasRepository.countByDistritoAndTipologia(seccion.getDistritoId(), URBANAS);
				Long noUrbanas = casillasRepository.countByDistritoAndTipologia(seccion.getDistritoId(), NO_URBANAS);
				Long convencidos = seguimientoRepository.countByLocalAndTipo(seccion.getDistritoId(), CONVENCIDO);
				Long metaCon = seguimientoRepository.getConvencidosByFederal(seccion.getDistritoId());
				Long notificados = seguimientoRepository.countNotificados(seccion.getDistritoId());
				dto = new ReporteSeguimintoVotoDTO();

				dto.setIdDistrito(seccion.getDistritoId());
				dto.setDistrito(seccion.getDistritoId() + "-" + seccion.getNombreDistrito());
				dto.setSecciones(countSecciones);
				dto.setUrbanas(urbanas);
				dto.setNoUrbanas(noUrbanas);
				dto.setMetaConvencidos(metaCon);
				dto.setTotalConvencidos(convencidos);
				double sub2 = (convencidos * 100.00) / dto.getMetaConvencidos();
				dto.setPorcentajeAvanceConvencidos(dosDecimales(sub2).doubleValue());
				dto.setNotificado(notificados);
				double sub1 = (notificados * 100.00) / dto.getTotalConvencidos();
				dto.setPorcentajeAvanceNotificado(dosDecimales(sub1).doubleValue());

				totales.setSecciones(totales.getSecciones() + countSecciones);
				totales.setUrbanas(totales.getUrbanas() + urbanas);
				totales.setNoUrbanas(totales.getNoUrbanas() + noUrbanas);
				totales.setMetaConvencidos(totales.getMetaConvencidos() + dto.getMetaConvencidos());
				totales.setTotalConvencidos(totales.getTotalConvencidos() + convencidos);
				totales.setNotificado(totales.getNotificado() + dto.getNotificado());

				lstvoto.add(dto);
			}

			totales.setPorcentajeAvanceConvencidos(
					dosDecimales((totales.getTotalConvencidos() * 100.00) / totales.getMetaConvencidos())
							.doubleValue());
			totales.setPorcentajeAvanceNotificado(
					dosDecimales((totales.getTotalConvencidos() * 100.00) / totales.getMetaConvencidos())
							.doubleValue());

			lstvoto.add(totales);

			return lstvoto;
		}else if(perfil == PERFIL_FEDERAL || perfil == PERFIL_LOCAL) {
			
//			if(perfil == PERFIL_LOCAL) {
//				
//				Long df = distritoRepository.findDstMunicipio(municipal);
//				
//				federal = df;
//			}
			
			String distrito = distritoRepository.findDstritoFederal(federal);
			
			Long countSecciones = seccionRepository.getSecciones(federal);
			Long urbanas = casillasRepository.countByDistritoAndTipologia(federal, URBANAS);
			Long noUrbanas = casillasRepository.countByDistritoAndTipologia(federal, NO_URBANAS);
			Long convencidos = seguimientoRepository.countByLocalAndTipo(federal, CONVENCIDO);
			Long metaCon = seguimientoRepository.getConvencidosByFederal(federal);
			Long notificados = seguimientoRepository.countNotificados(federal);
			dto = new ReporteSeguimintoVotoDTO();

			dto.setIdDistrito(federal);
			dto.setDistrito(federal + "-" + distrito);
			dto.setSecciones(countSecciones);
			dto.setUrbanas(urbanas);
			dto.setNoUrbanas(noUrbanas);
			dto.setMetaConvencidos(metaCon);
			dto.setTotalConvencidos(convencidos);
			double sub2 = (convencidos * 100.00) / dto.getMetaConvencidos();
			dto.setPorcentajeAvanceConvencidos(dosDecimales(sub2).doubleValue());
			dto.setNotificado(notificados);
			double sub1 = (notificados * 100.00) / dto.getTotalConvencidos();
			dto.setPorcentajeAvanceNotificado(dosDecimales(sub1).doubleValue());

			lstvoto.add(dto);

		return lstvoto;

		}else if(perfil == PERFIL_MUNICIPAL) {
			String distrito = distritoRepository.findDstritoFederal(federal);
			
			Long countSecciones = seccionRepository.getSeccionesByMunicipio(municipal);
			Long urbanas = casillasRepository.countByMunicipioAndTipologia(municipal, URBANAS);
			Long noUrbanas = casillasRepository.countByMunicipioAndTipologia(municipal, NO_URBANAS);
			Long convencidos = seguimientoRepository.countByMunicipioAndTipo(municipal, CONVENCIDO);
			Long metaCon = seguimientoRepository.getConvencidosByMunicipal(municipal);
			Long notificados = seguimientoRepository.countNotificadosMunicipal(municipal);
			dto = new ReporteSeguimintoVotoDTO();

			dto.setIdDistrito(federal);
			dto.setDistrito(federal + "-" + distrito);
			dto.setSecciones(countSecciones);
			dto.setUrbanas(urbanas);
			dto.setNoUrbanas(noUrbanas);
			dto.setMetaConvencidos(metaCon);
			dto.setTotalConvencidos(convencidos);
			double sub2 = (convencidos * 100.00) / dto.getMetaConvencidos();
			dto.setPorcentajeAvanceConvencidos(dosDecimales(sub2).doubleValue());
			dto.setNotificado(notificados);
			double sub1 = (notificados * 100.00) / dto.getTotalConvencidos();
			dto.setPorcentajeAvanceNotificado(dosDecimales(sub1).doubleValue());

			lstvoto.add(dto);

		return lstvoto;

		}else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	public BigDecimal dosDecimales(double numero) {

		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;

	}

	@Override
	public List<SeguimientoVotoDTO> getCasillaBySeccion(long idPerfil, Long idSeccion) throws SeguimientoVotoException {

		if (idPerfil == PERFIL_BRIGADISTA) {
			List<Convencidos> conven = new ArrayList<Convencidos>();

			List<SeguimientoVotoDTO> reporteDto = new ArrayList<SeguimientoVotoDTO>();
//			SeguimientoVotoDTO dto = null;
			SeguimientoVotoDTO totales = new SeguimientoVotoDTO();

			totales.setId(0L);
			totales.setNombreCompleto("");
			totales.setDomicilio("");
			totales.setTelefono("");
			totales.setUbicacionCasilla("");
			totales.setReferenciaCasilla("");
			totales.setTipoCasilla("");
			totales.setIsNotificado(false);

			if (idSeccion != null) {

				conven = seguimientoRepository.getConvencidos(idSeccion);
				if (conven != null) {
					reporteDto = conven.stream().map(this::convertirADto).collect(Collectors.toList());

				} else {
					throw new SeguimientoVotoException("No se encontraron datos", 404);
				}

				return reporteDto;

			} else {
				throw new SeguimientoVotoException("Favor de seleccionar una seccion", 401);
			}

		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes", 401);
		}

	}

	public SeguimientoVotoDTO convertirADto(Convencidos con) {
		SeguimientoVotoDTO convencidosDto = new SeguimientoVotoDTO();

		convencidosDto.setId(con.getId());
		convencidosDto.setNombreCompleto(con.getNombre());
		convencidosDto.setDomicilio(con.getCalle());
		convencidosDto.setTelefono(con.getTelCelular());
		convencidosDto.setUbicacionCasilla(con.getColonia_casilla());
		convencidosDto.setReferenciaCasilla(con.getReferencia_casilla());
		convencidosDto.setTipoCasilla(con.getNombreSeccion());
		convencidosDto.setIsNotificado(con.getIsNotificado());

		return convencidosDto;
	}

	@Override
	public void getReporteSeguimientoVotoDownload(HttpServletResponse response, Long perfil, Long usuario)
			throws SeguimientoVotoException, IOException {
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_LOCAL || perfil == PERFIL_MUNICIPAL) {
			setNameFile(response, CSV_SEGUIMIENTOVOTO);

			List<ReporteSeguimintoVotoDTO> seguimientoDTOs = getSeguimeitoVoto(perfil, usuario);

			String[] header = { "idDistrito", "distrito", "secciones", "urbanas", "noUrbanas", "metaConvencidos",
					"totalConvencidos", "porcentajeAvanceConvencidos", "notificado", "porcentajeAvanceNotificado" };

			setWriterFile(response, seguimientoDTOs, header);

		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para descargar el reporte", 401);
		}
	}

	@Override
	public String marcarConvencido(Long idUsuario, Long idConvencido, boolean IsNotificado)
			throws SeguimientoVotoException {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
		Long idEstado = usuario.getEntidad();

		if (perfil == PERFIL_BRIGADISTA) {

			Convencidos convencido = new Convencidos();
			String notificar = null;

			if (idConvencido != null && idConvencido > 0) {

				if (IsNotificado == true) {
					convencido = convencidoRepository.getByIdAndTipoAndIsNotificado(idEstado, idConvencido, CONVENCIDO,
							false);
					notificar = "marcado";
				} else {
					convencido = convencidoRepository.getByIdAndTipoAndIsNotificado(idEstado, idConvencido, CONVENCIDO,
							true);
					notificar = "desmarcado";
				}

				if (convencido != null) {
					Long resultado = convencidoRepository.updateMarcarOrDesmarcar(idConvencido, CONVENCIDO,
							IsNotificado);

					if (resultado == 1L) {
						return "Se ha " + notificar + " el convencido";
					} else {
						throw new SeguimientoVotoException("No se pudo " + notificar + " al convencido", 409);
					}

				} else {
					throw new SeguimientoVotoException("No se encontraron datos", 404);
				}

			} else {
				throw new SeguimientoVotoException("Ingrese datos validos", 400);
			}

		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes", 401);
		}

	}

	// -------------------------Reportes--------------
	@Override
	public List<ReporteInstalacionCasillaDTO> getInstalacionCasillaEstatal(Long idUsuario, Long idDistritoFederal,
			Long idDistritoLocal, Long idMunicipal)
			throws SeguimientoVotoException, IOException {

		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
		Long idEstado = usuario.getEntidad();
//		Long df = usuario.getFederal();

		if (perfil == PERFIL_ESTATAL) {
			
			List<ReporteInstalacionCasillaDTO> reporteDistDto = new ArrayList<ReporteInstalacionCasillaDTO>();
			ReporteInstalacionCasillaDTO dto = null;

			List<DistritoFederal> distritos = distritoRepository.findByEntidad(idEstado);
			
			if(idDistritoFederal == null && idDistritoLocal == null && idMunicipal == null) {
	
				for (DistritoFederal df1 : distritos) {
					dto = new ReporteInstalacionCasillaDTO();
	
					Long countCasillas = seguimientoRepository.getCasillasByDistrito(df1.getId());
					Long instaladas1 = seguimientoRepository.getCasillasInstaladas1(df1.getId());
					Long instaladas2 = seguimientoRepository.getCasillasInstaladas2(df1.getId());
					Long instaladas3 = seguimientoRepository.getCasillasInstaladas3(df1.getId());
					Long instaladas4 = seguimientoRepository.getCasillasInstaladas4(df1.getId());
					Long instaladas5 = seguimientoRepository.getCasillasInstaladas5(df1.getId());
					Long instaladas6 = seguimientoRepository.getCasillasInstaladas6(df1.getId());
					Long totalCasillas = seguimientoRepository.getTotalCasillasInstaladas(df1.getId());
	
					dto.setIdDistrito(df1.getId());
					dto.setCasillas(countCasillas);
					dto.setInstaladas7a730(instaladas1);
					dto.setInstaladas731a8(instaladas2);
					dto.setInstaladas8a830(instaladas3);
					dto.setInstaladas831a9(instaladas4);
					dto.setInstaladasDespues9(instaladas5);
					dto.setInstaladasDespues10(instaladas6);
					dto.setTotalInstaladas(totalCasillas);
					dto.setNoInstaladas(countCasillas - totalCasillas);
	
					reporteDistDto.add(dto);
	
				}
	
				return reporteDistDto;
			
			}else {
				
				dto = new ReporteInstalacionCasillaDTO();
				
				Long countCasillas = seguimientoRepository.getCasillasByDistritoFederal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas1 = seguimientoRepository.getCasillasInstaladas1Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas2 = seguimientoRepository.getCasillasInstaladas2Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas3 = seguimientoRepository.getCasillasInstaladas3Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas4 = seguimientoRepository.getCasillasInstaladas4Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas5 = seguimientoRepository.getCasillasInstaladas5Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas6 = seguimientoRepository.getCasillasInstaladas6Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long totalCasillas = seguimientoRepository.getTotalCasillasInstaladasFederal(idDistritoFederal,
						idDistritoLocal, idMunicipal);

				dto.setIdDistrito(idDistritoFederal);
				dto.setCasillas(countCasillas);
				dto.setInstaladas7a730(instaladas1);
				dto.setInstaladas731a8(instaladas2);
				dto.setInstaladas8a830(instaladas3);
				dto.setInstaladas831a9(instaladas4);
				dto.setInstaladasDespues9(instaladas5);
				dto.setInstaladasDespues10(instaladas6);
				dto.setTotalInstaladas(totalCasillas);
				dto.setNoInstaladas(countCasillas - totalCasillas);

				reporteDistDto.add(dto);

			return reporteDistDto;

			}
			
		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

	}

	@Override
	public List<ReporteInstalacionCasillaDTO> getInstalacionCasillaFederal(Long idUsuario, Long idDistritoFederal,
			Long idDistritoLocal, Long idMunicipal)
			throws SeguimientoVotoException, IOException {

		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
//		Long idEstado = usuario.getEntidad();
		Long df = usuario.getFederal();
		
		logger.debug(df);
		logger.debug(idUsuario);

		if (perfil == PERFIL_FEDERAL) {

			List<ReporteInstalacionCasillaDTO> reporteDistDto = new ArrayList<ReporteInstalacionCasillaDTO>();
			ReporteInstalacionCasillaDTO dto = null;
			
			if(idDistritoFederal == null && idDistritoLocal == null && idMunicipal == null) {

				dto = new ReporteInstalacionCasillaDTO();
				
				Long countCasillas = seguimientoRepository.getCasillasByDistrito(df);
				Long instaladas1 = seguimientoRepository.getCasillasInstaladas1(df);
				Long instaladas2 = seguimientoRepository.getCasillasInstaladas2(df);
				Long instaladas3 = seguimientoRepository.getCasillasInstaladas3(df);
				Long instaladas4 = seguimientoRepository.getCasillasInstaladas4(df);
				Long instaladas5 = seguimientoRepository.getCasillasInstaladas5(df);
				Long instaladas6 = seguimientoRepository.getCasillasInstaladas6(df);
				Long totalCasillas = seguimientoRepository.getTotalCasillasInstaladas(df);

				dto.setIdDistrito(df);
				dto.setCasillas(countCasillas);
				dto.setInstaladas7a730(instaladas1);
				dto.setInstaladas731a8(instaladas2);
				dto.setInstaladas8a830(instaladas3);
				dto.setInstaladas831a9(instaladas4);
				dto.setInstaladasDespues9(instaladas5);
				dto.setInstaladasDespues10(instaladas6);
				dto.setTotalInstaladas(totalCasillas);
				dto.setNoInstaladas(countCasillas - totalCasillas);
		
				reporteDistDto.add(dto);

				return reporteDistDto;
			}else {
				
				dto = new ReporteInstalacionCasillaDTO();
				
				Long countCasillas = seguimientoRepository.getCasillasByDistritoFederal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas1 = seguimientoRepository.getCasillasInstaladas1Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas2 = seguimientoRepository.getCasillasInstaladas2Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas3 = seguimientoRepository.getCasillasInstaladas3Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas4 = seguimientoRepository.getCasillasInstaladas4Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas5 = seguimientoRepository.getCasillasInstaladas5Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas6 = seguimientoRepository.getCasillasInstaladas6Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long totalCasillas = seguimientoRepository.getTotalCasillasInstaladasFederal(idDistritoFederal,
						idDistritoLocal, idMunicipal);

				dto.setIdDistrito(idDistritoFederal);
				dto.setCasillas(countCasillas);
				dto.setInstaladas7a730(instaladas1);
				dto.setInstaladas731a8(instaladas2);
				dto.setInstaladas8a830(instaladas3);
				dto.setInstaladas831a9(instaladas4);
				dto.setInstaladasDespues9(instaladas5);
				dto.setInstaladasDespues10(instaladas6);
				dto.setTotalInstaladas(totalCasillas);
				dto.setNoInstaladas(countCasillas - totalCasillas);

				reporteDistDto.add(dto);

			return reporteDistDto;

				
			}
			
		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	@Override
	public List<ReporteInstalacionCasillaDTO> getInstalacionCasillaLocal(Long idUsuario, Long idDistritoFederal,
			Long idDistritoLocal, Long idMunicipal)
			throws SeguimientoVotoException, IOException {

		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
//		Long idEstado = usuario.getEntidad();
//		Long df = usuario.getFederal();
		Long dl = usuario.getDistritoLocal();

		if (perfil == PERFIL_LOCAL) {
			
			logger.debug("local "+ dl);
			logger.debug("perfil "+ perfil);
			logger.debug("usuario "+ idUsuario);

			List<ReporteInstalacionCasillaDTO> reporteDistDto = new ArrayList<ReporteInstalacionCasillaDTO>();
			ReporteInstalacionCasillaDTO dto = null;

			if(idDistritoFederal == null && idDistritoLocal == null && idMunicipal == null) {
				
				dto = new ReporteInstalacionCasillaDTO();
	
				Long countCasillas = seguimientoRepository.getCasillasByLocal(dl);
				Long instaladas1 = seguimientoRepository.getCasillasInstaladas1L(dl);
				Long instaladas2 = seguimientoRepository.getCasillasInstaladas2L(dl);
				Long instaladas3 = seguimientoRepository.getCasillasInstaladas3L(dl);
				Long instaladas4 = seguimientoRepository.getCasillasInstaladas4L(dl);
				Long instaladas5 = seguimientoRepository.getCasillasInstaladas5L(dl);
				Long instaladas6 = seguimientoRepository.getCasillasInstaladas6L(dl);
				Long totalCasillas = seguimientoRepository.getTotalCasillasInstaladasL(dl);
	
				dto.setIdDistrito(dl);
				dto.setCasillas(countCasillas);
				dto.setInstaladas7a730(instaladas1);
				dto.setInstaladas731a8(instaladas2);
				dto.setInstaladas8a830(instaladas3);
				dto.setInstaladas831a9(instaladas4);
				dto.setInstaladasDespues9(instaladas5);
				dto.setInstaladasDespues10(instaladas6);
				dto.setTotalInstaladas(totalCasillas);
				dto.setNoInstaladas(countCasillas - totalCasillas);
				
				reporteDistDto.add(dto);
	
			
				return reporteDistDto;			
			}else {
				
//				Long disLocal = seguimientoRepository.getIdLocal(idDistritoFederal, idDistritoLocal, idMunicipal);
				dto = new ReporteInstalacionCasillaDTO();
				
				Long countCasillas = seguimientoRepository.getCasillasByDistritoFederal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas1 = seguimientoRepository.getCasillasInstaladas1Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas2 = seguimientoRepository.getCasillasInstaladas2Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas3 = seguimientoRepository.getCasillasInstaladas3Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas4 = seguimientoRepository.getCasillasInstaladas4Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas5 = seguimientoRepository.getCasillasInstaladas5Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas6 = seguimientoRepository.getCasillasInstaladas6Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long totalCasillas = seguimientoRepository.getTotalCasillasInstaladasFederal(idDistritoFederal,
						idDistritoLocal, idMunicipal);

				dto.setIdDistrito(idDistritoLocal);
				dto.setCasillas(countCasillas);
				dto.setInstaladas7a730(instaladas1);
				dto.setInstaladas731a8(instaladas2);
				dto.setInstaladas8a830(instaladas3);
				dto.setInstaladas831a9(instaladas4);
				dto.setInstaladasDespues9(instaladas5);
				dto.setInstaladasDespues10(instaladas6);
				dto.setTotalInstaladas(totalCasillas);
				dto.setNoInstaladas(countCasillas - totalCasillas);

				reporteDistDto.add(dto);

			return reporteDistDto;

				
			}
		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	@Override
	public List<ReporteInstalacionCasillaMuniDTO> getInstalacionCasillaMunicipal(Long idUsuario, Long idDistritoFederal,
			Long idDistritoLocal, Long idMunicipal)
			throws SeguimientoVotoException, IOException {

		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
//		Long idEstado = usuario.getEntidad();
//		Long df = usuario.getFederal();
		Long mun = usuario.getMunicipio();

		if (perfil == PERFIL_MUNICIPAL) {

			List<ReporteInstalacionCasillaMuniDTO> reporteMuntDto = new ArrayList<ReporteInstalacionCasillaMuniDTO>();
			ReporteInstalacionCasillaMuniDTO dto = null;

			if(idDistritoFederal == null && idDistritoLocal == null && idMunicipal == null) {
				
				dto = new ReporteInstalacionCasillaMuniDTO();
				String muni = seguimientoRepository.getNombreMunicipio(mun);
					
				Long countCasillas = seguimientoRepository.getCasillasByMunicipal(mun);
				Long instaladas1 = seguimientoRepository.getCasillasInstaladas1M(mun);
				Long instaladas2 = seguimientoRepository.getCasillasInstaladas2M(mun);
				Long instaladas3 = seguimientoRepository.getCasillasInstaladas3M(mun);
				Long instaladas4 = seguimientoRepository.getCasillasInstaladas4M(mun);
				Long instaladas5 = seguimientoRepository.getCasillasInstaladas5M(mun);
				Long instaladas6 = seguimientoRepository.getCasillasInstaladas6M(mun);
				Long totalCasillas = seguimientoRepository.getTotalCasillasInstaladasM(mun);
					
				dto.setIdMunicipio(mun);
				dto.setMunicipio(muni);
				dto.setCasillas(countCasillas);
				dto.setInstaladas7a730(instaladas1);
				dto.setInstaladas731a8(instaladas2);
				dto.setInstaladas8a830(instaladas3);
				dto.setInstaladas831a9(instaladas4);
				dto.setInstaladasDespues9(instaladas5);
				dto.setInstaladasDespues10(instaladas6);
				dto.setTotalInstaladas(totalCasillas);
				dto.setNoInstaladas(countCasillas - totalCasillas);
		
				reporteMuntDto.add(dto);
	
				return reporteMuntDto;
				
			}else{
				
				dto = new ReporteInstalacionCasillaMuniDTO();
				String muni = seguimientoRepository.getNombreMunicipio(idMunicipal);
				
				Long countCasillas = seguimientoRepository.getCasillasByDistritoFederal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas1 = seguimientoRepository.getCasillasInstaladas1Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas2 = seguimientoRepository.getCasillasInstaladas2Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas3 = seguimientoRepository.getCasillasInstaladas3Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas4 = seguimientoRepository.getCasillasInstaladas4Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas5 = seguimientoRepository.getCasillasInstaladas5Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long instaladas6 = seguimientoRepository.getCasillasInstaladas6Federal(idDistritoFederal, idDistritoLocal,
						idMunicipal);
				Long totalCasillas = seguimientoRepository.getTotalCasillasInstaladasFederal(idDistritoFederal,
						idDistritoLocal, idMunicipal);

				dto.setIdMunicipio(idMunicipal);
				dto.setMunicipio(muni);
				dto.setCasillas(countCasillas);
				dto.setInstaladas7a730(instaladas1);
				dto.setInstaladas731a8(instaladas2);
				dto.setInstaladas8a830(instaladas3);
				dto.setInstaladas831a9(instaladas4);
				dto.setInstaladasDespues9(instaladas5);
				dto.setInstaladasDespues10(instaladas6);
				dto.setTotalInstaladas(totalCasillas);
				dto.setNoInstaladas(countCasillas - totalCasillas);

				reporteMuntDto.add(dto);

			return reporteMuntDto;

			}

		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	@Override
	public List<ReporteInstalacionCasillaCrgDTO> getInstalacionCasillaCrg(Long idUsuario, Long idDistritoFederal,
			Long idDistritoLocal, Long idMunicipal)
			throws SeguimientoVotoException, IOException {

		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
//		Long idEstado = usuario.getEntidad();
		Long df = usuario.getFederal();

		if (perfil == PERFIL_CRG) {

			List<ReporteInstalacionCasillaCrgDTO> reporteCrgDto = new ArrayList<ReporteInstalacionCasillaCrgDTO>();
			ReporteInstalacionCasillaCrgDTO dto = null;

			dto = new ReporteInstalacionCasillaCrgDTO();

			
			
			if(idDistritoFederal == null && idDistritoLocal == null && idMunicipal == null) {
				
				List<Rutas> rutas = seguimientoRepository.findByFederal(df);

				for (Rutas ruts : rutas) {
					dto = new ReporteInstalacionCasillaCrgDTO();
					
					Long casillas = seguimientoRepository.getCasillasByRuta(ruts.getRuta());
					
					Long instaladas1 = seguimientoRepository.getCasillasInstaladas1Crg(ruts.getRuta());
					Long instaladas2 = seguimientoRepository.getCasillasInstaladas2Crg(ruts.getRuta());
					Long instaladas3 = seguimientoRepository.getCasillasInstaladas3Crg(ruts.getRuta());
					Long instaladas4 = seguimientoRepository.getCasillasInstaladas4Crg(ruts.getRuta());
					Long instaladas5 = seguimientoRepository.getCasillasInstaladas5Crg(ruts.getRuta());
					Long instaladas6 = seguimientoRepository.getCasillasInstaladas6Crg(ruts.getRuta());
					Long totalCasillas = seguimientoRepository.getTotalCasillasInstaladasCrg(ruts.getRuta());
	
					dto.setIdDistrito(df);
					dto.setRutas(ruts.getRuta());
					dto.setCasillas(casillas);
					dto.setInstaladas7a730(instaladas1);
					dto.setInstaladas731a8(instaladas2);
					dto.setInstaladas8a830(instaladas3);
					dto.setInstaladas831a9(instaladas4);
					dto.setInstaladasDespues9(instaladas5);
					dto.setInstaladasDespues10(instaladas6);
					dto.setTotalInstaladas(totalCasillas);
					dto.setNoInstaladas(casillas - totalCasillas);
	
					reporteCrgDto.add(dto);
	
				}
	
				return reporteCrgDto;
			}else {
				
				List<Rutas> rutas = seguimientoRepository.getCasillasInstaladas(idDistritoFederal, idDistritoLocal, idMunicipal);//findByFederal(idDistritoFederal);
				
				if(rutas == null) {
					throw new SeguimientoVotoException("No Se Encontraron Rutas", 401);
				}
				
				for (Rutas ruts : rutas) {
					dto = new ReporteInstalacionCasillaCrgDTO();
					
					Long casillas = seguimientoRepository.getCasillasByRuta(ruts.getRuta());
					
					Long instaladas1 = seguimientoRepository.getCasillasInstaladas1Crg(ruts.getRuta());
					Long instaladas2 = seguimientoRepository.getCasillasInstaladas2Crg(ruts.getRuta());
					Long instaladas3 = seguimientoRepository.getCasillasInstaladas3Crg(ruts.getRuta());
					Long instaladas4 = seguimientoRepository.getCasillasInstaladas4Crg(ruts.getRuta());
					Long instaladas5 = seguimientoRepository.getCasillasInstaladas5Crg(ruts.getRuta());
					Long instaladas6 = seguimientoRepository.getCasillasInstaladas6Crg(ruts.getRuta());
					Long totalCasillas = seguimientoRepository.getTotalCasillasInstaladasCrg(ruts.getRuta());
	
					dto.setIdDistrito(idDistritoFederal);
					dto.setRutas(ruts.getRuta());
					dto.setCasillas(casillas);
					dto.setInstaladas7a730(instaladas1);
					dto.setInstaladas731a8(instaladas2);
					dto.setInstaladas8a830(instaladas3);
					dto.setInstaladas831a9(instaladas4);
					dto.setInstaladasDespues9(instaladas5);
					dto.setInstaladasDespues10(instaladas6);
					dto.setTotalInstaladas(totalCasillas);
					dto.setNoInstaladas(casillas - totalCasillas);
	
					reporteCrgDto.add(dto);
	
				}
	
				return reporteCrgDto;
			}

		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	@Override
	public List<ReporteInstalacionCasillaRgDTO> getInstalacionCasillaRg(Long idUsuario, Long idDistritoFederal,
			Long idDistritoLocal, Long idMunicipal)
			throws SeguimientoVotoException, IOException {

		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
//		Long idEstado = usuario.getEntidad();
		Long df = usuario.getFederal();

		if (perfil == PERFIL_RG) {

			List<ReporteInstalacionCasillaRgDTO> reporteRgDto = new ArrayList<ReporteInstalacionCasillaRgDTO>();
			ReporteInstalacionCasillaRgDTO dto = null;
			
			ReporteInstalacionCasillaRgDTO totales = new ReporteInstalacionCasillaRgDTO();
			
			totales.setIdDistrito(0L);
			totales.setSeccion("");
			totales.setCasillas("Suma");
			totales.setInstaladas7a730(0L);
			totales.setInstaladas731a8(0L);
			totales.setInstaladas8a830(0L);
			totales.setInstaladas831a9(0L);
			totales.setInstaladasDespues9(0L);
			totales.setInstaladasDespues10(0L);
			totales.setTotalInstaladas(0L);
			totales.setNoInstaladas(0L);

			dto = new ReporteInstalacionCasillaRgDTO();

			
			
			if(idDistritoFederal == null && idDistritoLocal == null && idMunicipal == null) {
				
				List<SeccionElectoral> seccion = seguimientoRepository.getSeccionByDistrito(df);
	
				for (SeccionElectoral df1 : seccion) {
					dto = new ReporteInstalacionCasillaRgDTO();
					
					Long descripcion = Long.parseLong(df1.getDescripcion());
					
					String tipoCasilla = seguimientoRepository.getTipoCasilla(descripcion);
					
					Long casillas = seguimientoRepository.getCasillasBySeccion(descripcion);
					
					Long instaladas1 = seguimientoRepository.getCasillasInstaladas1Rg(descripcion);
					Long instaladas2 = seguimientoRepository.getCasillasInstaladas2Rg(descripcion);
					Long instaladas3 = seguimientoRepository.getCasillasInstaladas3Rg(descripcion);
					Long instaladas4 = seguimientoRepository.getCasillasInstaladas4Rg(descripcion);
					Long instaladas5 = seguimientoRepository.getCasillasInstaladas5Rg(descripcion);
					Long instaladas6 = seguimientoRepository.getCasillasInstaladas6Rg(descripcion);
					Long totalCasillas = seguimientoRepository.getTotalCasillasInstaladasRg(descripcion);
	
					dto.setIdDistrito(df);
					dto.setSeccion(df1.getDescripcion());
					dto.setCasillas(tipoCasilla);
					dto.setInstaladas7a730(instaladas1);
					dto.setInstaladas731a8(instaladas2);
					dto.setInstaladas8a830(instaladas3);
					dto.setInstaladas831a9(instaladas4);
					dto.setInstaladasDespues9(instaladas5);
					dto.setInstaladasDespues10(instaladas6);
					dto.setTotalInstaladas(totalCasillas);
					dto.setNoInstaladas(casillas - totalCasillas);
					
					totales.setInstaladas7a730(totales.getInstaladas7a730() + dto.getInstaladas7a730());
					totales.setInstaladas731a8(totales.getInstaladas731a8() + dto.getInstaladas731a8());
					totales.setInstaladas8a830(totales.getInstaladas8a830() + dto.getInstaladas8a830());
					totales.setInstaladas831a9(totales.getInstaladas831a9() + dto.getInstaladas831a9());
					totales.setInstaladasDespues9(totales.getInstaladasDespues9() + dto.getInstaladasDespues9());
					totales.setInstaladasDespues10(totales.getInstaladasDespues10() + dto.getInstaladasDespues10());
					totales.setTotalInstaladas(totales.getTotalInstaladas() + dto.getTotalInstaladas());
					totales.setNoInstaladas(totales.getNoInstaladas() + dto.getNoInstaladas());
					
					reporteRgDto.add(dto);
				}
				reporteRgDto.add(totales);
	
				return reporteRgDto;
			}else {
				
				List<SeccionElectoral> seccion = seguimientoRepository.getSeccionByDistritosLocal(idDistritoFederal, idDistritoLocal, idMunicipal);
				
				for (SeccionElectoral df1 : seccion) {
					dto = new ReporteInstalacionCasillaRgDTO();
					
					Long descripcion = Long.parseLong(df1.getDescripcion());
					
					String tipoCasilla = seguimientoRepository.getTipoCasilla(descripcion);
					
					Long casillas = seguimientoRepository.getCasillasBySeccion(descripcion);
					
					Long instaladas1 = seguimientoRepository.getCasillasInstaladas1Rg(descripcion);
					Long instaladas2 = seguimientoRepository.getCasillasInstaladas2Rg(descripcion);
					Long instaladas3 = seguimientoRepository.getCasillasInstaladas3Rg(descripcion);
					Long instaladas4 = seguimientoRepository.getCasillasInstaladas4Rg(descripcion);
					Long instaladas5 = seguimientoRepository.getCasillasInstaladas5Rg(descripcion);
					Long instaladas6 = seguimientoRepository.getCasillasInstaladas6Rg(descripcion);
					Long totalCasillas = seguimientoRepository.getTotalCasillasInstaladasRg(descripcion);
	
					dto.setIdDistrito(df);
					dto.setSeccion(df1.getDescripcion());
					dto.setCasillas(tipoCasilla);
					dto.setInstaladas7a730(instaladas1);
					dto.setInstaladas731a8(instaladas2);
					dto.setInstaladas8a830(instaladas3);
					dto.setInstaladas831a9(instaladas4);
					dto.setInstaladasDespues9(instaladas5);
					dto.setInstaladasDespues10(instaladas6);
					dto.setTotalInstaladas(totalCasillas);
					dto.setNoInstaladas(casillas - totalCasillas);
					
					totales.setInstaladas7a730(totales.getInstaladas7a730() + dto.getInstaladas7a730());
					totales.setInstaladas731a8(totales.getInstaladas731a8() + dto.getInstaladas731a8());
					totales.setInstaladas8a830(totales.getInstaladas8a830() + dto.getInstaladas8a830());
					totales.setInstaladas831a9(totales.getInstaladas831a9() + dto.getInstaladas831a9());
					totales.setInstaladasDespues9(totales.getInstaladasDespues9() + dto.getInstaladasDespues9());
					totales.setInstaladasDespues10(totales.getInstaladasDespues10() + dto.getInstaladasDespues10());
					totales.setTotalInstaladas(totales.getTotalInstaladas() + dto.getTotalInstaladas());
					totales.setNoInstaladas(totales.getNoInstaladas() + dto.getNoInstaladas());
					
					reporteRgDto.add(dto);
				}
				reporteRgDto.add(totales);
	
				return reporteRgDto;
			}
		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	// -------------------------Descarga--------------

	@Override
	public void getReporteInstalacionCasillaEstatalDownload(HttpServletResponse response, Long idUsuario,
			Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)
			throws SeguimientoVotoException, IOException {

		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();

		if (perfil == PERFIL_ESTATAL) {

			setNameFile(response, CSV_INST_ESTATAL);

			List<ReporteInstalacionCasillaDTO> estatalDTOs = getInstalacionCasillaEstatal(idUsuario, idDistritoFederal,
					idDistritoLocal, idMunicipal);

			String[] header = { "idDistrito", "casillas", "instaladas7a730", "instaladas731a8", "instaladas8a830",
					"instaladas831a9", "instaladasDespues9", "instaladasDespues10", "totalInstaladas", "noInstaladas" };
			
			String[] header2 = { "Distrito", "Casillas", "Instaladas 7:00 a 7:30", "Instaladas 7:31 a 8:00", "Instaladas 8:01 a 8:30",
					"Instaladas 8:31 a 9:00", "Instaladas Despues de las 9", "Instaladas Despues de las 10", "Total Instaladas", "No Instaladas" };

			setWriterFile(response, estatalDTOs, header, header2);

		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para descargar el reporte", 401);
		}

	}

	@Override
	public void getReporteInstalacionCasillaFederalDownload(HttpServletResponse response, Long idUsuario,
			Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)
			throws SeguimientoVotoException, IOException {

		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();

		if (perfil == PERFIL_FEDERAL) {

			setNameFile(response, CSV_INST_FEDERAL);

			List<ReporteInstalacionCasillaDTO> federalDTOs = getInstalacionCasillaFederal(idUsuario, idDistritoFederal,
					idDistritoLocal, idMunicipal);

			String[] header = { "idDistrito", "casillas", "instaladas7a730", "instaladas731a8", "instaladas8a830",
					"instaladas831a9", "instaladasDespues9", "instaladasDespues10", "totalInstaladas", "noInstaladas" };
			
			String[] header2 = { "Distrito", "Casillas", "Instaladas 7:00 a 7:30", "Instaladas 7:31 a 8:00", "Instaladas 8:01 a 8:30",
					"Instaladas 8:31 a 9:00", "Instaladas Despues de las 9:00", "Instaladas Despues de las 10", "Total Instaladas", "No Instaladas" };

			setWriterFile(response, federalDTOs, header, header2);

		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para descargar el reporte", 401);
		}

	}

	@Override
	public void getReporteInstalacionCasillaLocalDownload(HttpServletResponse response, Long idUsuario,
			Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)
			throws SeguimientoVotoException, IOException {

		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();

		if (perfil == PERFIL_LOCAL) {

			setNameFile(response, CSV_INST_LOCAL);

			List<ReporteInstalacionCasillaDTO> localDTOs = getInstalacionCasillaLocal(idUsuario, idDistritoFederal,
					idDistritoLocal, idMunicipal);

			String[] header = { "idDistrito", "casillas", "instaladas7a730", "instaladas731a8", "instaladas8a830",
					"instaladas831a9", "instaladasDespues9", "instaladasDespues10", "totalInstaladas", "noInstaladas" };
			
			String[] header2 = { "Distrito", "Casillas", "Instaladas 7:00 a 7:30", "Instaladas 7:31 a 8:00", "Instaladas 8:01 a 8:30",
					"Instaladas 8:31 a 9:00", "Instaladas Despues de las 9", "Instaladas Despues de las 10", "Total Instaladas", "No Instaladas" };

			setWriterFile(response, localDTOs, header, header2);

		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para descargar el reporte", 401);
		}

	}

	@Override
	public void getReporteInstalacionCasillaMunicipalDownload(HttpServletResponse response, Long idUsuario,
			Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)
			throws SeguimientoVotoException, IOException {

		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();

		if (perfil == PERFIL_MUNICIPAL) {

			setNameFile(response, CSV_INST_MUNICIPAL);

			List<ReporteInstalacionCasillaMuniDTO> municipalDTOs = getInstalacionCasillaMunicipal(idUsuario,
					idDistritoFederal, idDistritoLocal, idMunicipal);

			String[] header = { "idMunicipio", "municipio", "casillas", "instaladas7a730", "instaladas731a8",
					"instaladas8a830", "instaladas831a9", "instaladasDespues9", "instaladasDespues10",
					"totalInstaladas", "noInstaladas" };
			
			String[] header2 = { "IdMunicipio", "Municipio", "Casillas", "Instaladas 7:00 a 7:30", "Instaladas 7:31 a 8:00",
					"Instaladas 8:01 a 8:30", "Instaladas 8:31 a 9:00", "Instaladas Despues de las 9", "Instaladas Despues de las 10",
					"Total Instaladas", "No Instaladas" };

			setWriterFile(response, municipalDTOs, header, header2);

		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para descargar el reporte", 401);
		}

	}

	@Override
	public void getReporteInstalacionCasillaCrgDownload(HttpServletResponse response, Long idUsuario,
			Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)
			throws SeguimientoVotoException, IOException {

		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();

		if (perfil == PERFIL_CRG) {

			setNameFile(response, CSV_INST_CRG);

			List<ReporteInstalacionCasillaCrgDTO> crgDTOs = getInstalacionCasillaCrg(idUsuario, idDistritoFederal,
					idDistritoLocal, idMunicipal);

			String[] header = { "idDistrito", "rutas", "casillas", "instaladas7a730", "instaladas731a8",
					"instaladas8a830", "instaladas831a9", "instaladasDespues9", "instaladasDespues10",
					"totalInstaladas", "noInstaladas" };
			
			String[] header2 = { "Distrito", "Rutas", "Casillas", "Instaladas 7:00 a 7:30", "Instaladas 7:31 a 8:00",
					"Instaladas 8:01 a 8:30", "Instaladas 8:31 a 9:00", "Instaladas Despues de las 9", "Instaladas Despues de las 10",
					"Total Instaladas", "No Instaladas" };

			setWriterFile(response, crgDTOs, header, header2);

		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para descargar el reporte", 401);
		}

	}

	@Override
	public void getReporteInstalacionCasillaRgDownload(HttpServletResponse response, Long idUsuario,
			Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal)
			throws SeguimientoVotoException, IOException {

		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();

		if (perfil == PERFIL_RG) {

			setNameFile(response, CSV_INST_RG);

			List<ReporteInstalacionCasillaRgDTO> rgDTOs = getInstalacionCasillaRg(idUsuario, idDistritoFederal,
					idDistritoLocal, idMunicipal);

			String[] header = { "idDistrito", "seccion", "casillas", "instaladas7a730", "instaladas731a8",
					"instaladas8a830", "instaladas831a9", "instaladasDespues9", "instaladasDespues10",
					"totalInstaladas", "noInstaladas" };
			
			String[] header2 = { "Distrito", "Seccion", "Casillas", "Instaladas 7:00 a 7:30", " Instaladas 7:31 a 8:00",
					"Instaladas 8:01 a 8:30", "Instaladas 8:31 a 9:00", "Instaladas Despues de las 9", "Instaladas Despues de las 10",
					"Total Instaladas", "No Instaladas" };

			setWriterFile(response, rgDTOs, header, header2);

		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para descargar el reporte", 401);
		}

	}
}
