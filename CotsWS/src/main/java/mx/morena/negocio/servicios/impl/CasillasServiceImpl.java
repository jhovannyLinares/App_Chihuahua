package mx.morena.negocio.servicios.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.EnvioActasDTO;
import mx.morena.negocio.dto.PartidoDTO;
import mx.morena.negocio.dto.PartidosXAmbitoDTO;
import mx.morena.negocio.dto.ResultadoOkDTO;
import mx.morena.negocio.dto.ResultadoVotacionDTO;
import mx.morena.negocio.dto.VotacionesDTO;
import mx.morena.negocio.dto.VotosPartidoDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.ICasillasService;
import mx.morena.negocio.servicios.IInstalacionCasillaService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Casilla;
import mx.morena.persistencia.entidad.EnvioActas;
import mx.morena.persistencia.entidad.Partido;
import mx.morena.persistencia.entidad.Preguntas;
import mx.morena.persistencia.entidad.Votacion;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IEnvioActasRepository;
import mx.morena.persistencia.repository.IPartidosRepository;
import mx.morena.persistencia.repository.IResultadoVotacionRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class CasillasServiceImpl extends MasterService implements ICasillasService {

	@Autowired
	private IEnvioActasRepository envioActasRepository;

	@Autowired
	private ICasillaRepository casillaRepository;

	@Autowired
	private IInstalacionCasillaService instalacionCasillaService;

	@Autowired
	private IResultadoVotacionRepository resultadoVotacionRepository;

	@Autowired
	private IPartidosRepository partidosRepository;

	private List<Partido> gobernador;
	private List<Partido> municipal;
	private List<Partido> sindico;
	private List<Partido> diputadoLocal;
	private List<Partido> diputadoFederal;

	@Override
	public Long save(EnvioActasDTO actaDto, long perfil, long idUsuario) throws CotException {

		if (perfil != PERFIL_RG && perfil != PERFIL_RC) {

			throw new CotException("No cuenta con los servicios suficientes", 401);

		} else {

			EnvioActas actas = new EnvioActas();

			actas.setTipoVotacion(actaDto.getTipoVotacion());
			actas.setRutaActa(actaDto.getRutaActa());
			actas.setIdCasilla(actaDto.getIdCasilla());
			actas.setRegistroActa((new Timestamp(new Date().getTime())));
			actas.setTipoActa(actaDto.getTipoActa());
			actas.setCopiaRespuestaGobernador(actaDto.isCopiaRespuestaGobernador());
			actas.setCopiaRespuestaDiputadoLocal(actaDto.isCopiaRespuestaDiputadoLocal());
			actas.setCopiaRespuestaPresidenteMunicipal(actaDto.isCopiaRespuestaPresidenteMunicipal());
			actas.setCopiaRespuestaSindico(actaDto.isCopiaRespuestaSindico());
			actas.setCopiaRespuestaDiputadoFederal(actaDto.isCopiaRespuestaDiputadoFederal());

			if (actas.getTipoVotacion() != null && actas.getTipoVotacion() > 0) {
			} else {
				throw new CotException("El campo Tipo Votacion es Requerido", 400);
			}
			if (actas.getRutaActa() != null && actas.getRutaActa() != " ") {
			} else {
				throw new CotException("El campo Ruta Acta es Requerido", 400);
			}

			if (actas.getIdCasilla() > 0 && actas.getIdCasilla() != null) {
			} else {
				throw new CotException("El campo Id Casilla es Requerido", 400);
			}
			if (actas.getTipoActa() > 0 && actas.getTipoActa() != null) {

			} else {
				throw new CotException("El campo Tipo Acta es Requerido", 400);
			}
			if (actas.getTipoVotacion() != null && actas.getTipoVotacion() > 0 && actas.getRutaActa() != null
					&& actas.getRutaActa() != " " && actas.getIdCasilla() > 0 && actas.getIdCasilla() != null) {

				Long duplicate = envioActasRepository.validaDuplicidadActa(actas.getTipoVotacion(),
						actas.getIdCasilla());
				if (duplicate < 1) {
					envioActasRepository.save(actas);

					return envioActasRepository.getIdMax();
				} else {
					throw new CotException("No se puede duplicar en acta", 401);
				}

			}
		}
//
		return null;
	}

	@Override
	public ResultadoOkDTO saveResultados(ResultadoVotacionDTO resultadoVotos, long perfil, long usuario)
			throws CotException {

		validacionVotos(resultadoVotos);

		List<Votacion> votaciones = new ArrayList<Votacion>();

		Votacion votacion;
		Preguntas preguntas = new Preguntas();
		
		MapperUtil.map(resultadoVotos.getCuestionario(), preguntas);
		
		preguntas.setIdCasilla(resultadoVotos.getIdCasilla());
		preguntas.setTipoVotacion(resultadoVotos.getTipoVotacion());
		
		for (VotosPartidoDTO voto : resultadoVotos.getVotos()) {

			consultarVotaciones();

			votacion = new Votacion();
			
			MapperUtil.map(voto, votacion);

			votacion.setIdCasilla(resultadoVotos.getIdCasilla());
//			votacion.setIdPartido(voto.getIdPartido());
			votacion.setTipoVotacion(resultadoVotos.getTipoVotacion());
//			votacion.setVotos(voto.getVotos());

			Partido stream = new Partido();

			if (votacion.getTipoVotacion() == 1) {
				stream = gobernador.stream().filter(partido -> voto.getIdPartido() == partido.getId()).findAny()
						.orElse(null);
			}

			if (votacion.getTipoVotacion() == 2) {
				stream = municipal.stream().filter(partido -> voto.getIdPartido() == partido.getId()).findAny()
						.orElse(null);
			}

			if (votacion.getTipoVotacion() == 3) {
				stream = sindico.stream().filter(partido -> voto.getIdPartido() == partido.getId()).findAny()
						.orElse(null);
			}

			if (votacion.getTipoVotacion() == 4) {
				stream = diputadoLocal.stream().filter(partido -> voto.getIdPartido() == partido.getId()).findAny()
						.orElse(null);
			}

			if (votacion.getTipoVotacion() == 5) {
				stream = diputadoFederal.stream().filter(partido -> voto.getIdPartido() == partido.getId()).findAny()
						.orElse(null);
			}

//			Coalicion
			votacion.setCoalicion(false);

			if (stream.getTipoPartido() != null) {
				if (stream.getTipoPartido().length() > 0) {
					votacion.setCoalicion(true);
					votacion.setIdCoalicion(stream.getIdCoalicion());
				}
			}

			try {
				votaciones.add(votacion);
			} catch (Exception e) {
				throw new CotException("Se detecto un problema al guardar los votos", 409);
			}

		}
		
		try {

			resultadoVotacionRepository.save(votaciones);
			resultadoVotacionRepository.save(preguntas);
			
		} catch (Exception e) {
			throw new CotException("Se detecto un problema al guardar en BBDD ", 500);
		}
		

		return new ResultadoOkDTO(1, "OK");

	}

	private void validacionVotos(ResultadoVotacionDTO resultadoVotos) throws CotException {

		if (resultadoVotos.getIdCasilla() == null) {
			throw new CotException("no se encuentra informada la casilla", 409);
		}

		if (resultadoVotos.getTipoVotacion() == null) {
			throw new CotException("no se encuentra informada el tipo de votacion", 409);
		}

		if (resultadoVotos.getVotos() == null) {
			throw new CotException("no se encuentra informadas las votaciones", 409);
		}

		for (VotosPartidoDTO voto : resultadoVotos.getVotos()) {

			if (voto.getIdPartido() == null) {
				throw new CotException("no se encuentra informado el partido", 409);
			}

			if (voto.getVotos() == null) {
				throw new CotException("no se encuentra informado el numero de votos", 409);
			}

		}

	}

	@Override
	public List<VotacionesDTO> getActas(Long idCasilla) throws CotException {

		List<EnvioActas> actas = envioActasRepository.getCasilla(idCasilla);

		List<VotacionesDTO> votacionesDTOs = instalacionCasillaService.getVotaciones(idCasilla);

		for (VotacionesDTO votacionesDTO : votacionesDTOs) {
			for (EnvioActas acta : actas) {

				if (votacionesDTO.getId() == acta.getTipoVotacion()) {

					votacionesDTO.setCapturada(true);
				}

			}

		}

		return votacionesDTOs;
	}

	@Override
	public List<PartidosXAmbitoDTO> getPartidos(Long idCasilla) throws CotException {

		List<PartidosXAmbitoDTO> votacionesDTOs = getVotaciones(idCasilla);

		return votacionesDTOs;
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

	private List<PartidosXAmbitoDTO> getVotaciones(Long idCasilla) throws CotException {

		consultarVotaciones();

		List<Casilla> casillas = casillaRepository.getCasillasById(idCasilla);

		List<PartidosXAmbitoDTO> votacionesDTOs = new ArrayList<PartidosXAmbitoDTO>();

		PartidosXAmbitoDTO dto = null;

		for (Casilla casilla : casillas) {

			if (gobernador.stream()
					.filter(partido -> casilla.getEntidad().longValue() == partido.getUbicacion().longValue()).findAny()
					.orElse(null) != null) {

				dto = new PartidosXAmbitoDTO();
				dto.setId(1L);
				dto.setDescripcion("GOBERNADOR");

				dto.setPartidos(getPartidosXCasillas(gobernador, casilla.getEntidad()));

				votacionesDTOs.add(dto);
			}

			if (municipal.stream()
					.filter(partido -> casilla.getMunicipio().longValue() == partido.getUbicacion().longValue())
					.findAny().orElse(null) != null) {
				dto = new PartidosXAmbitoDTO();
				dto.setId(2L);
				dto.setDescripcion("PRESIDENTE MUNICIPAL");

				dto.setPartidos(getPartidosXCasillas(municipal, casilla.getMunicipio()));

				votacionesDTOs.add(dto);
			}

			if (sindico.stream()
					.filter(partido -> casilla.getMunicipio().longValue() == partido.getUbicacion().longValue())
					.findAny().orElse(null) != null) {
				dto = new PartidosXAmbitoDTO();
				dto.setId(3L);
				dto.setDescripcion("SINDICO");
				dto.setPartidos(getPartidosXCasillas(sindico, casilla.getMunicipio()));
				votacionesDTOs.add(dto);
			}

			if (diputadoLocal.stream()
					.filter(partido -> casilla.getFederal().longValue() == partido.getUbicacion().longValue()).findAny()
					.orElse(null) != null) {
				dto = new PartidosXAmbitoDTO();
				dto.setId(4L);
				dto.setDescripcion("DIPUTADO LOCAL");
				dto.setPartidos(getPartidosXCasillas(diputadoLocal, casilla.getFederal()));
				votacionesDTOs.add(dto);
			}

			if (diputadoFederal.stream()
					.filter(partido -> casilla.getFederal().longValue() == partido.getUbicacion().longValue()).findAny()
					.orElse(null) != null) {
				dto = new PartidosXAmbitoDTO();
				dto.setId(5L);
				dto.setDescripcion("DIPUTADO FEDERAL");
				dto.setPartidos(getPartidosXCasillas(diputadoFederal, casilla.getFederal()));
				votacionesDTOs.add(dto);
			}

		}

		return votacionesDTOs;

	}

	private List<PartidoDTO> getPartidosXCasillas(List<Partido> partidosX, Long ubicacion) {

		List<Partido> partidos = new ArrayList<Partido>();

		for (Partido partido : partidosX) {
			if (partido.getUbicacion() == ubicacion) {
				partidos.add(partido);
			}
		}

		return MapperUtil.mapAll(partidos, PartidoDTO.class);

	}

}
