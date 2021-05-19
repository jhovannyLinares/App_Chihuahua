package mx.morena.negocio.servicios.impl;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.morena.negocio.dto.ActasVotacionDTO;
import mx.morena.negocio.dto.ActasVotoDTO;
import mx.morena.negocio.dto.AfluenciaVotosDTO;
import mx.morena.negocio.dto.AfluenciasVotoDTO;
import mx.morena.negocio.dto.CasillasDTO;
import mx.morena.negocio.dto.CierreCasillaDTO;
import mx.morena.negocio.dto.CierreVotacionDTO;
import mx.morena.negocio.dto.CierreVotacionResponseDTO;
import mx.morena.negocio.dto.DatosRcDTO;
import mx.morena.negocio.dto.EstadoVotacionDTO;
import mx.morena.negocio.dto.IncidenciasCasillasDTO;
import mx.morena.negocio.dto.IncidenciasCasillasRespDTO;
import mx.morena.negocio.dto.IncidenciasResponseDTO;
import mx.morena.negocio.dto.InstalacionCasillasDTO;
import mx.morena.negocio.dto.InstalacionCasillasResponseDTO;
import mx.morena.negocio.dto.MotivoCierreDTO;
import mx.morena.negocio.dto.ReporteCasillaDTO;
import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.dto.RepresentantePartidosDTO;
import mx.morena.negocio.dto.RepresentantePartidosRespDTO;
import mx.morena.negocio.dto.ResultadoOkDTO;
import mx.morena.negocio.dto.UbicacionCasillaDTO;
import mx.morena.negocio.dto.VotacionesDTO;
import mx.morena.negocio.dto.listIncidenciasDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.IInstalacionCasillaService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.ActasVotos;
import mx.morena.persistencia.entidad.AfluenciaVotos;
import mx.morena.persistencia.entidad.AsignacionCasillas;
import mx.morena.persistencia.entidad.Casilla;
import mx.morena.persistencia.entidad.DatosRc;
import mx.morena.persistencia.entidad.EstadoVotacion;
import mx.morena.persistencia.entidad.Incidencias;
import mx.morena.persistencia.entidad.IncidenciasCasillas;
import mx.morena.persistencia.entidad.InstalacionCasilla;
import mx.morena.persistencia.entidad.MotivosTerminoVotacion;
import mx.morena.persistencia.entidad.Partido;
import mx.morena.persistencia.entidad.PartidosReporteCasilla;
import mx.morena.persistencia.entidad.ReporteCasilla;
import mx.morena.persistencia.entidad.RepresentacionPartidosReporte;
import mx.morena.persistencia.entidad.RepresentanteCargo;
import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.RepresentantesAsignados;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IIncidenciasCasillasRepository;
import mx.morena.persistencia.repository.IInstalacionCasillasRepository;
import mx.morena.persistencia.repository.IMotivosTerminoVotacionRepository;
import mx.morena.persistencia.repository.IPartidoVotacionRepository;
import mx.morena.persistencia.repository.IPartidosRepository;
import mx.morena.persistencia.repository.IReporteCasillasRepository;
import mx.morena.persistencia.repository.IRepresentanteRepository;
import mx.morena.persistencia.repository.IRepresentantesAsignadosRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class InstalacionCasillaServiceImpl extends MasterService implements IInstalacionCasillaService {

	@Autowired
	private IInstalacionCasillasRepository casillasRepository;

	@Autowired
	private IIncidenciasCasillasRepository incidenciasRepository;

	@Autowired
	private IReporteCasillasRepository reporteRepository;

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Autowired
	private IRepresentanteRepository representanteRepository;

	@Autowired
	private IRepresentantesAsignadosRepository representanteAsignadoRepository;

	@Autowired
	private ICasillaRepository casillaRepository;

	@Autowired
	private IPartidosRepository partidosRepository;
	
	@Autowired
	private IPartidoVotacionRepository partidoVotacionRepository;
	
	@Autowired
	IMotivosTerminoVotacionRepository motivosTerminoRepository;
	
	private List<Partido> gobernador;
	private List<Partido> municipal;
	private List<Partido> sindico;
	private List<Partido> diputadoLocal;
	private List<Partido> diputadoFederal;

	@Override
	@Transactional(rollbackFor = { CotException.class })
	public Long saveInstalacionCasilla(InstalacionCasillasDTO dto, long perfil, long usuario)
			throws CotException, IOException {

		if (perfil == PERFIL_RC) {

			List<InstalacionCasilla> casillas = casillasRepository.getById(dto.getIdCasilla());

			if (casillas == null) {

				InstalacionCasilla ic = new InstalacionCasilla();

				MapperUtil.map(dto, ic);
				
				////////   incideencias
				List<listIncidenciasDTO> lstIn = dto.getIncidencia();
				IncidenciasCasillas incCasilla = null;
				if (dto.getPresentaIncidencias().equalsIgnoreCase("si")) {
					for (listIncidenciasDTO incidencia : lstIn) {
						incCasilla = new IncidenciasCasillas();

						incCasilla.setIdCasilla(dto.getIdCasilla());
						incCasilla.setIdIncidencia(incidencia.getId());
						incCasilla.setTipoReporte(0);

						if (incidenciasRepository.save(incCasilla) == 0) {
							throw new CotException("No se guardo la incidencia con éxito.", 409);
						}
					}

				}
				////////
				if (casillasRepository.save(ic) == 0) {

					throw new CotException("No se guardo la informacion con éxito.", 409);
				}
			} else {
				throw new CotException("Casilla actualmente instalada", 409);
			}
		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}

		return casillasRepository.getMaxId();
	}

	@Override
	@Transactional(rollbackFor = { CotException.class })
	public Long saveIncidenciasCasilla(IncidenciasCasillasDTO dto, long perfil, long usuario) throws CotException {

        if (perfil == PERFIL_RC) {
            List<ReporteCasilla> reportes = reporteRepository.getReporteByIdCasilla(dto.getIdCasilla());
			
			if (reportes != null) {
				for (ReporteCasilla reporteCasilla : reportes) {
					if (reporteCasilla.getTipoReporte().intValue() == dto.getTipoReporte().intValue()) {
						throw new CotException("Reporte ya registrado", 409);
					}

				}
			}
			
			if (dto.getNumero() <= 750) {

				List<listIncidenciasDTO> lstIn = dto.getIncidencia();

				logger.debug(" numero incidencias " + lstIn.size());
				IncidenciasCasillas ic = null;

				Usuario usr = usuarioRepository.findById(usuario);

				if (dto.getPresentaIncidencias().equalsIgnoreCase("si")) {
					for (listIncidenciasDTO incidencia : lstIn) {
						ic = new IncidenciasCasillas();

						ic.setIdCasilla(dto.getIdCasilla());
						ic.setIdIncidencia(incidencia.getId());
						ic.setTipoReporte(dto.getTipoReporte());

						if (incidenciasRepository.save(ic) == 0) {
							throw new CotException("No se guardo la incidencia con éxito.", 409);
						}
					}

				}

				ReporteCasilla rc = new ReporteCasilla();

				rc.setIdCasilla(dto.getIdCasilla());
				rc.setHoraReporte(dto.getHoraReporte());
				rc.setNumeroVotos(dto.getNumero());
				rc.setTipoReporte(dto.getTipoReporte());
				rc.setHoraCierre(null);
					
				/* Se mapean los nuevos campos */
				rc.setRecibioVisitaRg(dto.isRecibioVisitaRg());
				//rc.setRc(true);
				//rc.setIdRg(null);
				rc.setIdRc(usr.getId());
				rc.setCerrada(false);
				rc.setIdMotivoCierre(0L);
				
				if (dto.getPersonasHanVotado() == null || dto.getBoletasUtilizadas() == null) {
					throw new CotException("Todas las preguntas son obligatorias", 400);
				}
					
				if (dto.getPersonasHanVotado() >= 0 && dto.getPersonasHanVotado() < 1000) {
					rc.setPersonasHanVotado(dto.getPersonasHanVotado());
				} else {
					throw new CotException("La cantidad de personas que han votado debe tener maximo 3 numeros", 400);
				}
					
				if (dto.getBoletasUtilizadas() >= 0 && dto.getBoletasUtilizadas() < 1000) {
					rc.setBoletasUtilizadas(dto.getBoletasUtilizadas());
				} else {
					throw new CotException("La cantidad de boletas utilizadas debe tener maximo 3 numeros", 400);
				}
					
				PartidosReporteCasilla partidoCasilla = null;
				List<RepresentantePartidosDTO> partidos = dto.getPartidos();
						
				for (RepresentantePartidosDTO partido : partidos) {
					if (partido.getIdPartido() != null && partido.getIdPartido() != 0) {
						partidoCasilla = new PartidosReporteCasilla();
							
						partidoCasilla.setIdCasilla(dto.getIdCasilla());
						partidoCasilla.setIdPartido(partido.getIdPartido());
						partidoCasilla.setTieneRepresentante(partido.isTieneRepresentante());
						partidoCasilla.setTipoReporte(dto.getTipoReporte());

						if (partidoVotacionRepository.save(partidoCasilla) == 0) {
							throw new CotException("No se guardo el reporte de casilla con éxito.", 409);
						} else {
							logger.debug("Se guardo con exito en la tabla app_partidos_reporte_casillas");
						}
					} else {
						logger.debug("El partido no. " + partido.getIdPartido() + " no tiene representante");
					}
							
				}
					
				if (reporteRepository.save(rc) == 0) {
					throw new CotException("No se guardo el reporte con éxito.", 409);
				} else {
					return dto.getIdCasilla();
				}

			} else {
				throw new CotException("El numero de votos excede el numero permitido", 400);
			}
		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}
		
	}

	@Override
	public List<CasillasDTO> getCasillasAsignadas(Long idUsuario) throws CotException {

		Usuario usuario = usuarioRepository.findById(idUsuario);

		Long perfil = usuario.getPerfil();
		Long idRepresentante = usuario.getRepresentante();
		Long idEntidad = usuario.getEntidad();

		if (perfil != PERFIL_RC && perfil != PERFIL_RG) {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}
		Representantes representante = null;
		
		if (perfil == PERFIL_RC ) {
			representante = representanteRepository.getRepresentanteByIdAndTipo(idRepresentante, PERFIL_RC);
		}
		if ( perfil == PERFIL_RG) {
			representante = representanteRepository.getRepresentanteByIdAndTipo(idRepresentante, PERFIL_RG);
		}

		if (representante == null) {
			throw new CotException("No se encontro el representante", 404);
		}

		List<RepresentantesAsignados> representantesAs = representanteAsignadoRepository
				.getByEntidadAndIdRepresentante(idEntidad, representante.getId());	
		
		List<CasillasDTO> casillas = new ArrayList<CasillasDTO>();
		
		if (perfil == PERFIL_RC ) {
			casillas = getCasillasRC(representantesAs);
		}
		if ( perfil == PERFIL_RG) {
			casillas = getCasillasRG(representantesAs);
		}
		
		

		return casillas;

	}
	
	private List<CasillasDTO> getCasillasRG(List<RepresentantesAsignados> rutasRepresentantes) throws CotException {

		List<CasillasDTO> casillas = new ArrayList<CasillasDTO>();
		List<AsignacionCasillas> casillasAsignadas = new ArrayList<AsignacionCasillas>();

		if (rutasRepresentantes != null && !rutasRepresentantes.isEmpty()) {
			for (RepresentantesAsignados rep : rutasRepresentantes) {
				if (rep != null && rep.getRutaId() != null) {
					casillasAsignadas = casillaRepository.getCasillasAsignadasByRuta(
							rep.getDistritoFederalId(), rep.getRutaId());

					if (casillasAsignadas != null) {
						casillas = casillasAsignadas.stream().map(this::convertirADto)
								.collect(Collectors.toList());
						for (CasillasDTO dto : casillas) {
							dto.setVotaciones(getVotaciones(dto.getIdCasilla()));
						}
					} else {
						throw new CotException("No se encontraron casillas", 404);
					}
				} else {
					throw new CotException("La ruta no puede ser nula", 400);
				}
			}

		} else {
			throw new CotException("No se encontraron rutas", 404);
		}
		return casillas;

	}

	private List<CasillasDTO> getCasillasRC(List<RepresentantesAsignados> representantesAs) throws CotException {

		List<CasillasDTO> casillas = new ArrayList<CasillasDTO>();
		List<AsignacionCasillas> casillasAsignadas = new ArrayList<AsignacionCasillas>();

		if (representantesAs != null && !representantesAs.isEmpty()) {

			for (RepresentantesAsignados representanteAsignado : representantesAs) {

				if (representanteAsignado.getCasillaId() != null) {

					casillasAsignadas = casillaRepository
							.getCasillasAsignadasById(representanteAsignado.getCasillaId());

					if (casillasAsignadas != null) {

						casillas = casillasAsignadas.stream().map(this::convertirADto).collect(Collectors.toList());

						for (CasillasDTO dto : casillas) {
							dto.setVotaciones(getVotaciones(dto.getIdCasilla()));
						}

					} else {
						throw new CotException("No se encontraron casillas asignadas", 404);
					}

				} else {
					throw new CotException("El no. de casilla no puede ser nula", 400);
				}
			}

		} else {
			throw new CotException("El representante no tiene asignaciones", 404);
		}
		return casillas;

	}

	private void consultarVotaciones() {

		if (gobernador == null)
			gobernador = partidosRepository.getGobernador();
		if (municipal == null)
			municipal = partidosRepository.getMunicipal();
		if (sindico == null)
			sindico = partidosRepository.getSindico();
		if (diputadoLocal == null)
			diputadoLocal = partidosRepository.getDiputadoLocal();
		if (diputadoFederal == null)
			diputadoFederal = partidosRepository.getDiputadoFederal();

	}

	private CasillasDTO convertirADto(AsignacionCasillas asignacioncasillas) {

		CasillasDTO casillaDto = new CasillasDTO();
		AfluenciasVotoDTO afluenciaDto = new AfluenciasVotoDTO();
		ActasVotoDTO actasDto = new ActasVotoDTO();

		casillaDto.setIdCasilla(asignacioncasillas.getIdCasilla());
		casillaDto.setTipoCasilla(asignacioncasillas.getTipoCasilla());
		casillaDto.setDistritoFederal(asignacioncasillas.getFederalId() + "-" + asignacioncasillas.getNombreDistrito());
		casillaDto.setRuta(asignacioncasillas.getRuta());
		casillaDto.setZonaCrg(asignacioncasillas.getZonaCrg());
		casillaDto.setIdZonaCrg(asignacioncasillas.getIdZonaCrg());
		casillaDto.setSeccion(asignacioncasillas.getSeccionId());

		casillaDto.setOpen(false);
		if (asignacioncasillas.getOpen() > 0) {
			casillaDto.setOpen(true);
		}
		casillaDto.setSeInstalo(asignacioncasillas.getSeInstalo());
		casillaDto.setLlegoRc(asignacioncasillas.getLlegoRc());
		casillaDto.setComenzoVotacion(asignacioncasillas.getComenzoVotacion());
		
		MapperUtil.map(asignacioncasillas.getAfluenciasVoto(), afluenciaDto);
		
		casillaDto.setAfluencia(afluenciaDto);
		
		MapperUtil.map(asignacioncasillas.getActasVotacion(), actasDto);
		
		casillaDto.setActas(actasDto);
		
		return casillaDto;
	}

	@Override
	public String horaCierre(long usuario, CierreCasillaDTO dto, long perfil) throws CotException, IOException {

		if (perfil == PERFIL_RG) {

			// TODO: ajuste no toma si ya se cerro
			List<ReporteCasilla> reporte = reporteRepository.getReporteByIdCasilla(dto.getIdCasilla());

			if (reporte != null) {
				ReporteCasilla rc = new ReporteCasilla();

				rc.setIdCasilla(dto.getIdCasilla());
				rc.setHoraCierre(dto.getHoraCierre());

				if (reporteRepository.updateHoraCierre(rc) == 0) {
					throw new CotException("No se actualizo la hora de cierre", 409);
				}
			} else {
				throw new CotException("La casilla seleccionada no esta registrada", 404);
			}

		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}
		return "Se guardo la hora de cierre de la casilla " + dto.getIdCasilla();
	}
	
	@Override
	public String horaCierreVotacion(long usuario, CierreVotacionDTO dto, long perfil) throws CotException {
		if (perfil == PERFIL_RC) {

			List<ReporteCasilla> reporte = reporteRepository.getCierreByIdCasilla(dto.getIdCasilla());
			
			if (reporte != null) {
				for (ReporteCasilla casilla : reporte) {
					if (casilla.isCerrada()) {
						throw new CotException("La casilla ya esta cerrada", 400);
					}
				}
				
				ReporteCasilla rc = new ReporteCasilla();
				if (dto.getIdMotivoCierre() > 0 && dto.getIdMotivoCierre() <= 4) {
					rc.setIdMotivoCierre(dto.getIdMotivoCierre());
				} else {
					throw new CotException("El motivo del cierre de votación es obligatorio.", 400);
				}
				
				Usuario usr = usuarioRepository.findById(usuario);
				
				rc.setIdCasilla(dto.getIdCasilla());
				rc.setHoraCierre(dto.getHoraCierre());
				rc.setIdRc(usr.getId());
				rc.setCerrada(true);
				rc.setNumeroVotos(0L);
				
				List<listIncidenciasDTO> incidencias = dto.getIncidencia();
				IncidenciasCasillas ic = null;
				
				if (dto.isPresentaIncidencias()) {
					for (listIncidenciasDTO incidencia : incidencias) {
						ic = new IncidenciasCasillas();
						ic.setIdCasilla(dto.getIdCasilla());
						
						if (incidencia.getId() > 0) {
							ic.setIdIncidencia(incidencia.getId());
							ic.setTipoReporte(null);
						} else {
							throw new CotException("Ingrese una incidencia valida", 400);
						}

						if (incidenciasRepository.save(ic) == 0) {
							throw new CotException("No se guardo la incidencia", 409);
						}
					}
				}

				if (reporteRepository.save(rc) == 0) {
					throw new CotException("No se guardo la hora de cierre", 409);
				} else {
					return "Se guardo la hora de cierre de la votacion " + dto.getIdCasilla();
				}
			} else {
				throw new CotException("La casilla seleccionada no esta registrada", 404);
			}

		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}
	}

	public List<VotacionesDTO> getVotaciones(Long idCasilla) throws CotException {
		
		consultarVotaciones();

		List<Casilla> casillas = casillaRepository.getCasillasById(idCasilla);

		List<VotacionesDTO> votacionesDTOs = new ArrayList<VotacionesDTO>();

		VotacionesDTO dto = null;

		for (Casilla casilla : casillas) {

			if (gobernador.stream()
					.filter(partido -> casilla.getEntidad().longValue() == partido.getUbicacion().longValue()).findAny()
					.orElse(null) != null) {
				dto = new VotacionesDTO();
				dto.setId(1L);
				dto.setDescripcion("GOBERNADOR");
				votacionesDTOs.add(dto);
			}

			if (municipal.stream()
					.filter(partido -> casilla.getMunicipio().longValue() == partido.getUbicacion().longValue())
					.findAny().orElse(null) != null) {
				dto = new VotacionesDTO();
				dto.setId(2L);
				dto.setDescripcion("PRESIDENTE MUNICIPAL");
				votacionesDTOs.add(dto);
			}

			if (sindico.stream()
					.filter(partido -> casilla.getMunicipio().longValue() == partido.getUbicacion().longValue())
					.findAny().orElse(null) != null) {
				dto = new VotacionesDTO();
				dto.setId(3L);
				dto.setDescripcion("SINDICO");
				votacionesDTOs.add(dto);
			}

			if (diputadoLocal.stream()
					.filter(partido -> casilla.getFederal().longValue() == partido.getUbicacion().longValue()).findAny()
					.orElse(null) != null) {
				dto = new VotacionesDTO();
				dto.setId(4L);
				dto.setDescripcion("DIPUTADO LOCAL");
				votacionesDTOs.add(dto);
			}

			if (diputadoFederal.stream()
					.filter(partido -> casilla.getFederal().longValue() == partido.getUbicacion().longValue()).findAny()
					.orElse(null) != null) {
				dto = new VotacionesDTO();
				dto.setId(5L);
				dto.setDescripcion("DIPUTADO FEDERAL");
				votacionesDTOs.add(dto);
			}

		}

		return votacionesDTOs;

	}

	@Override
	public List<ReporteCasillaDTO> getInformeVotacion(Long idCasilla, long idUsuario) throws CotException {

		List<ReporteCasilla> reporteCasillas = reporteRepository.getReporteByIdCasilla(idCasilla);

		List<ReporteCasillaDTO> reporteCasillaDTOs = new ArrayList<ReporteCasillaDTO>();

		ReporteCasillaDTO dto;

		for (int i = 1; i <= 3; i++) {

			dto = new ReporteCasillaDTO();
			dto.setCapturado(false);
			dto.setTipoReporte(i);
			reporteCasillaDTOs.add(dto);
		}

		if (reporteCasillas != null) {

			for (ReporteCasilla reporteCasilla : reporteCasillas) {

				for (ReporteCasillaDTO reporteCasillaDTO : reporteCasillaDTOs) {

					if (reporteCasillaDTO.getTipoReporte() == reporteCasilla.getTipoReporte()) {

						reporteCasillaDTO.setCapturado(true);
						reporteCasillaDTO.setHoraReporte(new Time(reporteCasilla.getHoraReporte().getTime()) .toString());
						reporteCasillaDTO.setTipoReporte(reporteCasilla.getTipoReporte());
					}

				}

//				reporteCasillaDTOs.add(dto);
			}

		}

		return reporteCasillaDTOs;

	}

	@Override
	public InstalacionCasillasResponseDTO getInstalacionCasilla(long perfil, long usuario, Long idCasilla) throws CotException {

		if (perfil == PERFIL_RC) {

			InstalacionCasilla casilla = casillasRepository.getCasillaById(idCasilla);

			if (casilla != null) {

				InstalacionCasillasResponseDTO dto = new InstalacionCasillasResponseDTO();

				MapperUtil.map(casilla, dto);
				
				List<Incidencias> incidencias = new ArrayList<Incidencias>();
				
				List<IncidenciasResponseDTO> lstIncidencias = null;
				
				incidencias = incidenciasRepository.getIncidenciasByIdCasilla(idCasilla);
				
				if (incidencias != null) {
				
				lstIncidencias = MapperUtil.mapAll(incidencias, IncidenciasResponseDTO.class);
				
				dto.setPresentaIncidencias("si");
				dto.setIncidencia(lstIncidencias);
				}else {
					dto.setPresentaIncidencias("no");
				}

				return dto;

			} else {
				throw new CotException("La casilla seleccionada no esta registrada.", 404);
			}
		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}

	}

	/**
	 * metodo de consulta de casilla y datos del rc
	 */
	@Override
	public DatosRcDTO getDatosRc(long perfil, long usuario) throws CotException {

		Usuario usr = usuarioRepository.findById(usuario);

		DatosRc representante = representanteAsignadoRepository.getRepresentanteById(usr.getRepresentante());

		if (representante != null) {

			DatosRcDTO dto = new DatosRcDTO();

			MapperUtil.map(representante, dto);

			List<RepresentanteCargo> lstDto = new ArrayList<RepresentanteCargo>();

			List<RepresentanteDTO> rep = null;

			lstDto = representanteAsignadoRepository.getNombreRepresentanteByCasilla(representante.getIdCasilla());

			rep = MapperUtil.mapAll(lstDto, RepresentanteDTO.class);
			dto.setRepresentante(rep);

			return dto;

		} else {
			throw new CotException("No se encontro informacion del usuario registrado con el id " + usuario, 404);
		}
	}

	@Override
	@Transactional(rollbackFor = { CotException.class, Exception.class })
	public String saveEstadoVotacion(EstadoVotacionDTO dto, long perfil, long usuario) throws CotException, IOException {

		if (perfil == PERFIL_RG) {
			List<EstadoVotacion> votacion = reporteRepository.getEstadoByIdCasilla(dto.getIdCasilla());

			if (votacion != null) {
				EstadoVotacion ev = new EstadoVotacion();
				
				ev.setSeInstalo(dto.getSeInicio());
				ev.setLlegoRc(dto.getLlegoRc());
				ev.setComenzoVotacion(dto.getComenzoVotacion());
				ev.setIdCasilla(dto.getIdCasilla());

				if (reporteRepository.updateEstadoVotacion(ev) == 0) {
					throw new CotException("No se guardo el estado de la casilla", 409);
				}
			} else {
				
				EstadoVotacion ev = new EstadoVotacion();
				
				ev.setSeInstalo(dto.getSeInicio());
				ev.setLlegoRc(dto.getLlegoRc());
				ev.setComenzoVotacion(dto.getComenzoVotacion());
				ev.setIdCasilla(dto.getIdCasilla());

				if (reporteRepository.insertEstadoVotacion(ev) == 0) {
					throw new CotException("No se guardo el estado de la casilla", 409);
				}
			}

		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}
		return "Se guardo es estado de la casilla " + dto.getIdCasilla();

	}

	@Override
	@Transactional(rollbackFor = { CotException.class, Exception.class })
	public ResultadoOkDTO saveEstadoP(List<EstadoVotacionDTO> dtos, Long perfil, Long usuario)
			throws CotException, IOException {
		for (EstadoVotacionDTO estadoVotacionDTO : dtos) {
			//Long idCasilla = estadoVotacionDTO.getIdCasilla();
			
			saveEstadoVotacion(estadoVotacionDTO, perfil, usuario);
			
		}
		return new ResultadoOkDTO(1, "OK");
	}
	
	//metodo encargado de registrar la afluencia 
	@Override
	@Transactional(rollbackFor = { CotException.class, Exception.class })
	public String saveAfluenciaVotos(AfluenciaVotosDTO dto, long perfil, long usuario) throws CotException, IOException {

		if (perfil == PERFIL_RG) {
			List<AfluenciaVotos> afluencia = reporteRepository.getAfluenciaByIdCasilla(dto.getIdCasilla());

			if (afluencia != null) {
				AfluenciaVotos ev = new AfluenciaVotos();
				
				ev.setHrs12(dto.getHrs12());
				ev.setHrs16(dto.getHrs16());
				ev.setHrs18(dto.getHrs18());
				ev.setIdCasilla(dto.getIdCasilla());

				if (reporteRepository.updateAfluenciaVotacion(ev) == 0) {
					throw new CotException("No se guardo la alfuencia de la casilla", 409);
				}
			} else {
				
				throw new CotException("La casilla no fue intalada", 401);
//				
//				AfluenciaVotos ev = new AfluenciaVotos();
//				
//				ev.setHrs12(dto.getHrs12());
//				ev.setHrs16(dto.getHrs16());
//				ev.setHrs18(dto.getHrs18());
//				ev.setIdCasilla(dto.getIdCasilla());
//
//				if (reporteRepository.insertAfluenciaVotacion(ev) == 0) {
//					throw new CotException("No se guardo la afluenncia de la casilla", 409);
//				}
			}

		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}
		return "Se guardo es estado de la casilla " + dto.getIdCasilla();

	}

	@Override
	public ResultadoOkDTO saveAfluencia(List<AfluenciaVotosDTO> dtos, Long perfil, Long usuario)
			throws CotException, IOException {
		for (AfluenciaVotosDTO afluenciaDTO : dtos) {
			//Long idCasilla = estadoVotacionDTO.getIdCasilla();
			
			saveAfluenciaVotos(afluenciaDTO, perfil, usuario);
			
		}
		return new ResultadoOkDTO(1, "OK");
	}

	@Override
	@Transactional(rollbackFor = { CotException.class, Exception.class })
	public String saveActasVotos(ActasVotacionDTO dto, long perfil, long usuario) throws CotException, IOException {

		if (perfil == PERFIL_RG) {
			List<ActasVotos> actas = reporteRepository.getActasByIdCasilla(dto.getIdCasilla());

			if (actas != null) {
				ActasVotos ev = new ActasVotos();
				
				ev.setGobernador(dto.getGobernador());
				ev.setDiputadoFedaral(dto.getDiputadoFedaral());
				ev.setDiputadoLocal(dto.getDiputadoLocal());
				ev.setPresidenteMunicipal(dto.getPresidenteMunicipal());
				ev.setSindico(dto.getSindico());
				ev.setIdCasilla(dto.getIdCasilla());

				if (reporteRepository.updateActasVotacion(ev) == 0) {
					throw new CotException("No se guardo el estado de la casilla", 409);
				}
			} else {
				
				throw new CotException("La casilla no fue instalada", 401);
				
//				ActasVotos ev = new ActasVotos();
//				
//				ev.setGobernador(dto.getGobernador());
//				ev.setDiputadoFedaral(dto.getDiputadoFedaral());
//				ev.setDiputadoLocal(dto.getDiputadoLocal());
//				ev.setPresidenteMunicipal(dto.getPresidenteMunicipal());
//				ev.setSindico(dto.getSindico());
//				ev.setIdCasilla(dto.getIdCasilla());
//
//				if (reporteRepository.insertActasVotacion(ev) == 0) {
//					throw new CotException("No se guardo el estado de la casilla", 409);
//				}
			}

		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}
		return "Se guardo es estado de la casilla " + dto.getIdCasilla();

	}
	
	@Override
	public ResultadoOkDTO saveActas(List<ActasVotacionDTO> dtos, Long perfil, Long usuario)
			throws CotException, IOException {
		
		for (ActasVotacionDTO actasDTO : dtos) {
			//Long idCasilla = estadoVotacionDTO.getIdCasilla();
			
			saveActasVotos(actasDTO, perfil, usuario);
			
		}
		return new ResultadoOkDTO(1, "OK");
	}

	/**
	 * Metodo de consulta de informacion de ubicacion de la casilla
	 */
	@Override
	public UbicacionCasillaDTO getDatosCasilla(long perfil, long usuario, Long idCasilla) throws CotException {
		
		if (perfil == PERFIL_RC) {
			
			Casilla ca = casillaRepository.getUbicacionById(usuario);
			
			if (ca != null) {
				
				UbicacionCasillaDTO dto = new UbicacionCasillaDTO();
				MapperUtil.map(ca, dto);
				return dto;
				
			}else {
				throw new CotException("No se encontro informacion de la casilla. ", 404);
			}
			
		}else {
			throw new CotException("No se cuenta con los permisos suficientes para consultar la informacion. ", 401);
		}
		
		
		
	}

	
	/**
	 * Gets the registros votaciones.
	 *
	 * @param idUsuario
	 * @param perfil
	 * @return datos que registro un rc sobre votaciones
	 * @throws CotException the cot exception
	 */
	@Override
	public List<IncidenciasCasillasRespDTO> getRegistrosVotaciones(Long idUsuario, Long perfil, Long idCasilla, Integer tipoReporte) throws CotException {
		
		if (perfil == PERFIL_RC) {
			List<ReporteCasilla> reporteCasillas = reporteRepository.getRegistrosByIdRc(idUsuario, idCasilla, tipoReporte);
			
			List<IncidenciasCasillasRespDTO> reporteCasillaDTOs = new ArrayList<IncidenciasCasillasRespDTO>();
			List<Incidencias> incidencias = new ArrayList<Incidencias>();
			List<IncidenciasResponseDTO> incidenciasDto = new ArrayList<IncidenciasResponseDTO>();
			
			List<RepresentacionPartidosReporte> partidos = new ArrayList<RepresentacionPartidosReporte>();
			List<RepresentantePartidosRespDTO> partidosDto = new ArrayList<RepresentantePartidosRespDTO>();

			if (reporteCasillas != null) {
				reporteCasillaDTOs = MapperUtil.mapAll(reporteCasillas, IncidenciasCasillasRespDTO.class);
				
				for (IncidenciasCasillasRespDTO reporteCasilla : reporteCasillaDTOs) {
					incidencias = incidenciasRepository.getByIdCasilla(reporteCasilla.getIdCasilla(), reporteCasilla.getTipoReporte());
					if (incidencias != null) {
						incidenciasDto = MapperUtil.mapAll(incidencias, IncidenciasResponseDTO.class);
						reporteCasilla.setIncidencia(incidenciasDto);
					}
					
					partidos = partidoVotacionRepository.getPartidosByIdCasillaAndReporte(reporteCasilla.getIdCasilla(), reporteCasilla.getTipoReporte());
					if (partidos != null) {
						partidosDto = MapperUtil.mapAll(partidos, RepresentantePartidosRespDTO.class);
						reporteCasilla.setPartidos(partidosDto);
					}
					
					for (ReporteCasilla rep : reporteCasillas) {
						if (reporteCasilla.getTipoReporte() == rep.getTipoReporte() && reporteCasilla.getIdCasilla() == rep.getIdCasilla()) {
							reporteCasilla.setHoraReporte(new Time(rep.getHoraReporte().getTime()).toString());
						}
					}
					
				}
				
			} else {
				throw new CotException("El Rc no ha capturado datos", 404);
			}
	
			return reporteCasillaDTOs;
			
		} else {
			throw new CotException("No se cuenta con los permisos suficientes para consultar la informacion. ", 401);
		}
	}

	/**
	 * Gets the existe Reporte.
	 *
	 * @param perfil
	 * @param idCasilla
	 * @param tipoReporte
	 * @return valida si un reporte ya esta registrado para determinada casilla
	 * @throws CotException the cot exception
	 */
	@Override
	public boolean existeReporte(Long perfil, Long idCasilla, Integer tipoReporte) throws CotException {
		if (perfil == PERFIL_RC) {
			if (idCasilla != null && tipoReporte != null) {
				Casilla cas = casillaRepository.getById(idCasilla);
				
				if (cas != null) {
					//Valida que la casilla este en reporte_casillas
					List<ReporteCasilla> reporte = reporteRepository.getCierreByIdCasilla(idCasilla);
					if (reporte != null) {
						List<ReporteCasilla> reportes = reporteRepository.getReporteByIdCasillaAndTipoRep(idCasilla, tipoReporte);
						
						if (reportes != null) {
							return true;
						}
						
						return false;
					} else {
						throw new CotException("La casilla ingresada aun no contiene informacion", 404);
					}
				} else {
					throw new CotException("La casilla ingresada no existe", 404);
				}
				
			} else {
				throw new CotException("Ingrese una casilla y un tipo de reporte", 400);
			}
			
		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}
	}

	/**
	 * Gets the get cierre votacion.
	 *
	 * @param idUsuario
	 * @param perfil
	 * @param idCasilla
	 * @return muestra el cierre de votaciones de una casilla
	 * @throws CotException the cot exception
	 */
	@Override
	public List<CierreVotacionResponseDTO> getCierreVotacion(Long idUsuario, Long perfil, Long idCasilla) throws CotException {
		if (perfil == PERFIL_RC) {
			
			List<ReporteCasilla> reporteCasillas = reporteRepository.getCierreByIdCasillaAndIsCerrada(idCasilla, true);
			
			List<CierreVotacionResponseDTO> reportesCasillasDTO = new ArrayList<CierreVotacionResponseDTO>();
			List<Incidencias> incidencias = new ArrayList<Incidencias>();
			List<IncidenciasResponseDTO> incidenciasDto = new ArrayList<IncidenciasResponseDTO>();
			CierreVotacionResponseDTO reporteDTO = null;
			
			MotivosTerminoVotacion motivo = new MotivosTerminoVotacion();
			MotivoCierreDTO motivoDTO = null;
			if (reporteCasillas != null) {
				
				for (ReporteCasilla reporteCasilla : reporteCasillas) {
					
					reporteDTO = new CierreVotacionResponseDTO();
					reporteDTO.setIdCasilla(reporteCasilla.getIdCasilla());
					
					incidencias = incidenciasRepository.getIncidenciaByIdCasilla(reporteCasilla.getIdCasilla());
					if (incidencias != null) {
						incidenciasDto = MapperUtil.mapAll(incidencias, IncidenciasResponseDTO.class);
						reporteDTO.setIncidencia(incidenciasDto);
					}
					
					motivo = motivosTerminoRepository.getById(reporteCasilla.getIdMotivoCierre());
					if (motivo != null) {
						motivoDTO = new MotivoCierreDTO();
						motivoDTO.setIdMotivo(motivo.getId());
						motivoDTO.setMotivo(motivo.getMotivo());
						reporteDTO.setMotivoCierre(motivoDTO);
					}
					
					reporteDTO.setHoraCierre(new Time(reporteCasilla.getHoraCierre().getTime()).toString());
					reportesCasillasDTO.add(reporteDTO);
				}
				
			} else {
				throw new CotException("No se encontraron datos", 404);
			}
	
			return reportesCasillasDTO;
			
		} else {
			throw new CotException("No se cuenta con los permisos suficientes para consultar la informacion. ", 401);
		}

	}

	/**
	 * Gets the is cerrada votacion.
	 *
	 * @param perfil
	 * @param idCasilla
	 * @return valida si esta cerrada la votación en una casilla
	 * @throws CotException the cot exception
	 */
	@Override
	public boolean isCerradaVotacion(Long perfil, Long idCasilla) throws CotException {
		if (perfil == PERFIL_RC) {
			if (idCasilla != null) {
				//Se valida que exista la casilla
				Casilla cas = casillaRepository.getById(idCasilla);
				
				if (cas != null) {
					//Valida que la casilla este en reporte_casillas
					List<ReporteCasilla> reporte = reporteRepository.getCierreByIdCasilla(idCasilla);
					if (reporte != null) {
						List<ReporteCasilla> reportes = reporteRepository.getCierreByIdCasillaAndIsCerrada(idCasilla, true);
						
						if (reportes != null) {
							return true;
						}
						
						return false;
					} else {
						throw new CotException("La casilla ingresada aun no contiene informacion", 404);
					}
				} else {
					throw new CotException("La casilla ingresada no existe", 404);
				}
				
			} else {
				throw new CotException("Ingrese una casilla", 400);
			}
			
		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}
	}

}
