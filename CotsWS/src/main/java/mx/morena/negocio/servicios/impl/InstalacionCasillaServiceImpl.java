package mx.morena.negocio.servicios.impl;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.morena.negocio.dto.CasillasDTO;
import mx.morena.negocio.dto.CierreCasillaDTO;
import mx.morena.negocio.dto.DatosRcDTO;
import mx.morena.negocio.dto.EstadoVotacionDTO;
import mx.morena.negocio.dto.IncidenciasCasillasDTO;
import mx.morena.negocio.dto.InstalacionCasillasDTO;
import mx.morena.negocio.dto.ReporteCasillaDTO;
import mx.morena.negocio.dto.RepresentantePartidosDTO;
import mx.morena.negocio.dto.ResultadoOkDTO;
import mx.morena.negocio.dto.VotacionesDTO;
import mx.morena.negocio.dto.listIncidenciasDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.IInstalacionCasillaService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.AsignacionCasillas;
import mx.morena.persistencia.entidad.Casilla;
import mx.morena.persistencia.entidad.EstadoVotacion;
import mx.morena.persistencia.entidad.IncidenciasCasillas;
import mx.morena.persistencia.entidad.InstalacionCasilla;
import mx.morena.persistencia.entidad.Partido;
import mx.morena.persistencia.entidad.PartidosReporteCasilla;
import mx.morena.persistencia.entidad.ReporteCasilla;
import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.RepresentantesAsignados;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IIncidenciasCasillasRepository;
import mx.morena.persistencia.repository.IInstalacionCasillasRepository;
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

		if (perfil == PERFIL_RG || perfil == PERFIL_RC) {
			boolean is_rc;
			
			if(perfil == PERFIL_RG) {
				is_rc = false;
			} else {
				is_rc = true;
			}
			
			List<ReporteCasilla> reportes = reporteRepository.getReporteByIdCasillaAndRc(dto.getIdCasilla(), is_rc);
			
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

						if (incidenciasRepository.save(ic) == 0) {
							throw new CotException("No se guardo la incidencia con éxito.", 409);
						}
					}

				}

				ReporteCasilla rc = new ReporteCasilla();

				rc.setIdCasilla(dto.getIdCasilla());
				rc.setHoraReporte(dto.getHoraReporte());
				rc.setIdRg(usr.getId());
				rc.setNumeroVotos(dto.getNumero());
				rc.setTipoReporte(dto.getTipoReporte());

				rc.setRecibioVisitaRepresentante(false);
				rc.setCantidadPersonasHanVotado(null);
				rc.setBoletasUtilizadas(null);
				rc.setRc(false);
				
				if (perfil == PERFIL_RC) {
					
					/* Se mapean los nuevos campos */
					rc.setRecibioVisitaRepresentante(dto.isRecibioVisitaRepresentante());
					rc.setRc(true);
					rc.setIdRg(null);
					
					if (dto.getCantidadPersonasHanVotado() == null || dto.getBoletasUtilizadas() == null) {
						throw new CotException("Todas las preguntas son obligatorias", 400);
					}
					
					if (dto.getCantidadPersonasHanVotado() >= 0 && dto.getCantidadPersonasHanVotado() < 1000) {
						rc.setCantidadPersonasHanVotado(dto.getCantidadPersonasHanVotado());
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
						if (partido.getIdPartido() != null) {
							partidoCasilla = new PartidosReporteCasilla();
							
							partidoCasilla.setIdCasilla(dto.getIdCasilla());
							partidoCasilla.setIdPartido(partido.getIdPartido());
							partidoCasilla.setTieneRepresentante(partido.isTieneRepresentante());

							if (partidoVotacionRepository.save(partidoCasilla) == 0) {
								throw new CotException("No se guardo el reporte de casilla con éxito.", 409);
							} else {
								logger.debug("Se guardo con exito en la tabla app_partidos_reporte_casillas");
							}
						} else {
							logger.debug("El partido no. " + partido.getIdPartido() + " no tiene representante");
						}
							
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
	public InstalacionCasillasDTO getInstalacionCasilla(long perfil, long usuario, Long idCasilla) throws CotException {
		
		if (perfil == PERFIL_RC) {
			
			InstalacionCasilla casilla = casillasRepository.getCasillaById(idCasilla);
			
			if (casilla != null) {
				
			InstalacionCasillasDTO dto = new InstalacionCasillasDTO();
			
			MapperUtil.map(casilla, dto);
			
			return dto;
			}else {
				throw new CotException("La casilla seleccionada no esta registrada", 404);
			}
		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion", 401);
		}
		
	}

	@Override
	public DatosRcDTO getDatosRc(long perfil, long usuario) throws CotException {

		RepresentantesAsignados representante = representanteAsignadoRepository.getRepresentanteById(usuario);

		if (representante != null) {

			DatosRcDTO dto = new DatosRcDTO();

			dto.setIdUsuario(usuario);
			dto.setEntidad(representante.getEntidadId());
			dto.setDistFederal(representante.getDistritoFederalId());
			dto.setDistLocal(representante.getDistritoLocalId());
			dto.setSeccion(representante.getSeccionElectoralId());
			dto.setCasilla(representante.getCasillaId());

			return dto;

		} else {
			throw new CotException("No se encontro el usuario registrado con el id " + usuario, 404);
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

}
