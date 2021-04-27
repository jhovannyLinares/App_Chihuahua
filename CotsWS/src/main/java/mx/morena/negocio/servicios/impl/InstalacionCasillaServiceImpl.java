package mx.morena.negocio.servicios.impl;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.morena.negocio.dto.CasillasDTO;
import mx.morena.negocio.dto.CierreCasillaDTO;
import mx.morena.negocio.dto.IncidenciasCasillasDTO;
import mx.morena.negocio.dto.InstalacionCasillasDTO;
import mx.morena.negocio.dto.ReporteCasillaDTO;
import mx.morena.negocio.dto.VotacionesDTO;
import mx.morena.negocio.dto.listIncidenciasDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.IInstalacionCasillaService;
import mx.morena.persistencia.entidad.AsignacionCasillas;
import mx.morena.persistencia.entidad.Casilla;
import mx.morena.persistencia.entidad.IncidenciasCasillas;
import mx.morena.persistencia.entidad.InstalacionCasilla;
import mx.morena.persistencia.entidad.ReporteCasilla;
import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.RepresentantesAsignados;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IIncidenciasCasillasRepository;
import mx.morena.persistencia.repository.IInstalacionCasillasRepository;
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

	@Override
	@Transactional(rollbackFor = { CotException.class })
	public Long saveInstalacionCasilla(InstalacionCasillasDTO dto, long perfil, long usuario)
			throws CotException, IOException {

		if (perfil == PERFIL_RG || perfil == PERFIL_RC) {

			List<InstalacionCasilla> casillas = casillasRepository.getById(dto.getIdCasilla());

			if (casillas != null) {

				InstalacionCasilla ic = new InstalacionCasilla();

				ic.setIdCasilla(dto.getIdCasilla());
				ic.setHoraInstalacion(dto.getHoraInstalacion());
				ic.setHoraInstalacion(dto.getHoraInstalacion());
				ic.setLlegaronFuncionarios(dto.getLlegaronFuncionarios());
				ic.setFuncionariosFila(dto.getFuncionariosFila());
				ic.setPaqueteCompleto(dto.getPaqueteCompleto());
				ic.setLlegoRg(dto.getLlegoRg());
				ic.setDesayuno(dto.getDesayuno());
				ic.setInicioVotacion(dto.getInicioVotacion());
				ic.setInicioVotacion(dto.getInicioVotacion());
				ic.setLlegoRc(dto.getLlegoRc());

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

		if (perfil == PERFIL_RG) {

			if (dto.getNumero() <= 750) {

				List<listIncidenciasDTO> lstIn = dto.getIncidencia();
				System.out.println(" numero incidencias " + lstIn.size());
				IncidenciasCasillas ic = null;

				Usuario usr = usuarioRepository.findById(usuario);

				System.out.println("SSSS " + dto.getPresentaIncidencias());

				if (dto.getPresentaIncidencias().equals("si")) {
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
//				rc.setHoraReporte(dto.getHoraReporte());
				rc.setIdRg(usr.getId());
				rc.setNumeroVotos(dto.getNumero());
				rc.setTipoReporte(dto.getTipoReporte());

				if (reporteRepository.save(rc) == 0) {
					throw new CotException("No se guardo el reporte con éxito.", 409);
				}

			} else {
				throw new CotException("El numero de votos excede el numero permitido", 400);
			}
		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}
		return dto.getIdCasilla();
	}

	@Override
	public List<CasillasDTO> getCasillasAsignadas(Long idUsuario) throws CotException {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
		Long idRepresentante = usuario.getRepresentante();
		Long idEntidad = usuario.getEntidad();

		if (perfil == PERFIL_RC) {
			Representantes representante = representanteRepository.getRepresentanteByIdAndTipo(idRepresentante,
					PERFIL_RC);
			if (representante != null) {

				List<RepresentantesAsignados> representantesAs = representanteAsignadoRepository
						.getByEntidadAndIdRepresentante(idEntidad, representante.getId());
				List<CasillasDTO> casillas = new ArrayList<CasillasDTO>();
				List<AsignacionCasillas> casillasAsignadas = new ArrayList<AsignacionCasillas>();

				if (representantesAs != null && !representantesAs.isEmpty()) {
					for (RepresentantesAsignados representanteAsignado : representantesAs) {
						if (representanteAsignado.getCasillaId() != null) {

							casillasAsignadas = casillaRepository
									.getCasillasAsignadasById(representanteAsignado.getCasillaId());

							if (casillasAsignadas != null) {

								casillas = casillasAsignadas.stream().map(this::convertirADto)
										.collect(Collectors.toList());

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
			} else {
				throw new CotException("No se encontro el representante", 404);
			}

		} else if (perfil == PERFIL_RG) {
			Representantes representante = representanteRepository.getRepresentanteByIdAndTipo(idRepresentante,
					PERFIL_RG);
			if (representante != null) {

				List<RepresentantesAsignados> rutasRepresentantes = representanteAsignadoRepository
						.getByEntidadAndIdRepresentante(idEntidad, representante.getId());
				List<CasillasDTO> casillas = new ArrayList<CasillasDTO>();
				List<AsignacionCasillas> casillasAsignadas = new ArrayList<AsignacionCasillas>();

				if (rutasRepresentantes != null && !rutasRepresentantes.isEmpty()) {
					for (RepresentantesAsignados rep : rutasRepresentantes) {
						if (rep != null && rep.getRutaId() != null) {
							casillasAsignadas = casillaRepository.getCasillasAsignadasByRuta(idEntidad,
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

					return casillas;

				} else {
					throw new CotException("No se encontraron rutas", 404);
				}

			} else {
				throw new CotException("No se encontro el representante", 404);
			}
		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}
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
//				rc.setHoraCierre(dto.getHoraCierre());

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

	private List<VotacionesDTO> getVotaciones(Long idCasilla) throws CotException {

		List<Casilla> casillas = casillaRepository.getCasillasById(idCasilla);

		List<VotacionesDTO> votacionesDTOs = new ArrayList<VotacionesDTO>();

		VotacionesDTO dto = null;

		for (Casilla casilla : casillas) {

			if (partidosRepository.getGobernador(casilla.getEntidad()).size() > 0) {
				dto = new VotacionesDTO();
				dto.setId(1L);
				dto.setDescripcion("GOBERNADOR");
				votacionesDTOs.add(dto);
			}

			if (partidosRepository.getMunicipal(casilla.getMunicipio()).size() > 0) {
				dto = new VotacionesDTO();
				dto.setId(2L);
				dto.setDescripcion("PRESIDENTE MUNICIPAL");
				votacionesDTOs.add(dto);
			}

			if (partidosRepository.getSindico(casilla.getMunicipio()).size() > 0) {
				dto = new VotacionesDTO();
				dto.setId(3L);
				dto.setDescripcion("SINDICO");
				votacionesDTOs.add(dto);
			}

			if (partidosRepository.getDiputadoLocal(casilla.getFederal()).size() > 0) {
				dto = new VotacionesDTO();
				dto.setId(4L);
				dto.setDescripcion("DIPUTADO LOCAL");
				votacionesDTOs.add(dto);
			}

			if (partidosRepository.getDiputadoFederal(casilla.getFederal()).size() > 0) {
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

		if (reporteCasillas != null) {

			ReporteCasillaDTO dto;

			for (ReporteCasilla reporteCasilla : reporteCasillas) {
				dto = new ReporteCasillaDTO();

				dto.setCapturado(true);

				dto.setHoraReporte(reporteCasilla.getHoraReporte().toString());

				dto.setTipoReporte(reporteCasilla.getTipoReporte());

				reporteCasillaDTOs.add(dto);
			}
		}

		return reporteCasillaDTOs;

	}

}
