package mx.morena.negocio.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.CasillaDTO;
import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.EleccionDTO;
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.dto.IncidenciaDTO;
import mx.morena.negocio.dto.MunicipioDTO;
import mx.morena.negocio.dto.RepresentacionPartidosDTO;
import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.dto.SeccionDTO;
import mx.morena.negocio.dto.SeccionUserDTO;
import mx.morena.negocio.dto.TipoActasDTO;
import mx.morena.negocio.dto.offline.CatalogoDTOOffline;
import mx.morena.negocio.dto.offline.DistritoFederalDTOOffline;
import mx.morena.negocio.dto.offline.EntidadDTOOffline;
import mx.morena.negocio.dto.offline.LocalidadDTOOffline;
import mx.morena.negocio.dto.offline.MunicipioDTOOffline;
import mx.morena.negocio.exception.CatalogoException;
import mx.morena.negocio.servicios.ICatalogoService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Cargo;
import mx.morena.persistencia.entidad.Casilla;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Eleccion;
import mx.morena.persistencia.entidad.Entidad;
import mx.morena.persistencia.entidad.Incidencias;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.RepresentacionPartidos;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.entidad.TipoActas;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.ICargosRepository;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IDistritoLocalRepository;
import mx.morena.persistencia.repository.IEleccionRepository;
import mx.morena.persistencia.repository.IEntidadRepository;
import mx.morena.persistencia.repository.IIncidenciasRepository;
import mx.morena.persistencia.repository.IMunicipioRepository;
import mx.morena.persistencia.repository.IPartidoVotacionRepository;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class CatalogoServiceImpl extends MasterService implements ICatalogoService {

	@Autowired
	private IDistritoFederalRepository federalRepocitory;
	
	@Autowired
	private IDistritoLocalRepository localRepository;

	@Autowired
	private IMunicipioRepository municipioRepository;

	@Autowired
	private ICargosRepository cargosRepository;

	@Autowired
	private ISeccionElectoralRepository seccionRepository;

	@Autowired
	private IEntidadRepository entidadRepository;

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Autowired
	private ICasillaRepository casillaRepository;
	
	@Autowired
	private IIncidenciasRepository incidenciaRepository;
	
	@Autowired
	private IEleccionRepository eleccionRepository;
	
	@Autowired
	private IPartidoVotacionRepository partidoVotacionRepository;

	@Override
	public List<EntidadDTO> getEntidades() {

		List<Entidad> entidades = entidadRepository.findAll();

		List<EntidadDTO> dtos = new ArrayList<EntidadDTO>();

		dtos = MapperUtil.mapAll(entidades, EntidadDTO.class);

		return dtos;

	}

	@Override
	public List<DistritoFederalDTO> getFederalByEntidad(long idUsuario, long idPerfil, Long idEntidad) {

		Usuario usuario = usuarioRepository.findById(idUsuario);

		List<DistritoFederalDTO> distritoFederalDTOs = null;

		if (usuario.getPerfil() > PERFIL_ESTATAL) {

			DistritoFederal distritoFederal = federalRepocitory.findById(usuario.getFederal());
			DistritoFederalDTO dto = new DistritoFederalDTO();
			MapperUtil.map(distritoFederal, dto);

			distritoFederalDTOs = new ArrayList<DistritoFederalDTO>();
			distritoFederalDTOs.add(dto);

		} else {

			List<DistritoFederal> distritoFederals = federalRepocitory.findByEntidad(idEntidad);

			distritoFederalDTOs = MapperUtil.mapAll(distritoFederals, DistritoFederalDTO.class);

		}

		return distritoFederalDTOs;
	}

	@Override
	public List<MunicipioDTO> getMunicipioByFederal(long idUsuario, long idPerfil, Long idFederal) throws CatalogoException {

		Usuario usuario = usuarioRepository.findById(idUsuario);

		List<MunicipioDTO> municipioDTOs = null;

		if (usuario.getPerfil() > PERFIL_FEDERAL) {

			Municipio municipio = municipioRepository.findById(usuario.getMunicipio(), usuario.getFederal());
			if(municipio == null) {
				throw new CatalogoException("No se localizaron los Municipios", 401);
			}
			MunicipioDTO dto = new MunicipioDTO();
			MapperUtil.map(municipio, dto);

			municipioDTOs = new ArrayList<MunicipioDTO>();
			municipioDTOs.add(dto);

		} else {

			List<Municipio> municipios = municipioRepository.getByFederal(idFederal);

			municipioDTOs = MapperUtil.mapAll(municipios, MunicipioDTO.class);

		}

		return municipioDTOs;
	}

//	@Override
//	public List<LocalidadDTO> getLocalidadByMunicipio(long idUsuario, long idPerfil, Long municipio) {
//
//		Usuario usuario = usuarioRepository.findById(idUsuario);
//
//		List<LocalidadDTO> localidadDTOs = null;
//
//		if (usuario.getPerfil() > PERFIL_MUNICIPAL) {
//
//			Localidad localidad = localidadRepository.getById(usuario.getLocalidad());
//			LocalidadDTO dto = new LocalidadDTO();
//			MapperUtil.map(localidad, dto);
//
//			localidadDTOs = new ArrayList<LocalidadDTO>();
//			localidadDTOs.add(dto);
//
//		} else {
//
//			List<Localidad> municipios = localidadRepository.getByMunicipio(municipio);
//
//			localidadDTOs = MapperUtil.mapAll(municipios, LocalidadDTO.class);
//
//		}
//
//		return localidadDTOs;
//	}

	@Override
	public CatalogoDTOOffline getCatalogos(long usuarioId, long perfilId) throws CatalogoException {

		logger.debug("getCatalogos");

		Usuario usuario = usuarioRepository.findById(usuarioId);

		Entidad entidad = entidadRepository.findById(usuario.getEntidad());
		logger.debug("entidad " + entidad.getNombre());

		EntidadDTOOffline entidadOffline = new EntidadDTOOffline();

		MapperUtil.map(entidad, entidadOffline);

		List<DistritoFederalDTO> distritoFederals = getFederalByEntidad(usuarioId, perfilId, entidad.getId());

		List<DistritoFederalDTOOffline> dtoOfflines = new ArrayList<DistritoFederalDTOOffline>();

		dtoOfflines = MapperUtil.mapAll(distritoFederals, DistritoFederalDTOOffline.class);

		for (DistritoFederalDTOOffline distritoFederalDTO : dtoOfflines) {

			logger.debug("distritoFederalDTO " + distritoFederalDTO.getId());

			List<MunicipioDTO> municipioDTOs = getMunicipioByFederal(usuarioId, perfilId, distritoFederalDTO.getId());

			List<MunicipioDTOOffline> municipioDTOsOfflines = new ArrayList<MunicipioDTOOffline>();

			municipioDTOsOfflines = MapperUtil.mapAll(municipioDTOs, MunicipioDTOOffline.class);

			for (MunicipioDTOOffline municipioDTO : municipioDTOsOfflines) {

				logger.debug("municipioDTO " + municipioDTO.getId());

				List<SeccionDTO> localidadDTOs = getSeccionesByMunicipio(usuarioId, perfilId, municipioDTO.getId());

				List<LocalidadDTOOffline> localidadDTOOfflines = new ArrayList<LocalidadDTOOffline>();

				localidadDTOOfflines = MapperUtil.mapAll(localidadDTOs, LocalidadDTOOffline.class);

				municipioDTO.setSecciones(localidadDTOOfflines);

			}
			distritoFederalDTO.setMunicipios(municipioDTOsOfflines);
		}
		entidadOffline.setDistritosFederales(dtoOfflines);

		CatalogoDTOOffline offline = new CatalogoDTOOffline();

		offline.setEntidad(entidadOffline);

		return offline;

	}

	@Override
	public List<CasillaDTO> getCasillas(long idUsuario, long perfil, Long distritoFederalId, Long municipioId,
			Long seccionId, Boolean isLibres) {

		Usuario usuario = usuarioRepository.findById(idUsuario);

		List<Casilla> casillas = null;
		List<CasillaDTO> dtos = new ArrayList<CasillaDTO>();

		if (seccionId != null) {
			casillas = casillaRepository.getCasillasSeccion(seccionId);
		} else

		if (municipioId != null) {
			casillas = casillaRepository.getCasillasMunicipio(municipioId);
		} else

		if (distritoFederalId != null) {
			casillas = casillaRepository.getCasillasFederal(distritoFederalId);
		} else {
			casillas = casillaRepository.getCasillas(usuario.getEntidad());
		}
		
		if (isLibres != null) {
			
			CasillaDTO dto = null;
			for (Casilla casilla : casillas) {
				if (casilla.isAsignado() != isLibres) {
					dto = new CasillaDTO();
					MapperUtil.map(casilla, dto);
					dtos.add(dto);
				}
			}

		} else {
			dtos = MapperUtil.mapAll(casillas, CasillaDTO.class);
		}
		

		return dtos;
	}

	@Override
	public List<RepresentanteDTO> getRepresentantes(long perfil) {
		List<RepresentanteDTO> representantes = new ArrayList<RepresentanteDTO>();

		if (perfil < PERFIL_RC) {
			RepresentanteDTO dto = new RepresentanteDTO();
			dto.setId(REP_RC);
			dto.setDescripcion("Representante RC");
			representantes.add(dto);
		}

		if (perfil < PERFIL_RG) {
			RepresentanteDTO dto = new RepresentanteDTO();
			dto.setId(REP_RG);
			dto.setDescripcion("Representante RG");
			representantes.add(dto);
		}

		if (perfil < PERFIL_MUNICIPAL) {
			RepresentanteDTO dto = new RepresentanteDTO();
			dto.setId(REP_MUNICIPAL);
			dto.setDescripcion("Representante Municipal");
			representantes.add(dto);
		}

		if (perfil < PERFIL_LOCAL) {

			RepresentanteDTO dto = new RepresentanteDTO();
			dto.setId(REP_LOCAL);
			dto.setDescripcion("Representante Local");
			representantes.add(dto);

			dto = new RepresentanteDTO();
			dto.setId(REP_CRG);
			dto.setDescripcion("Representante CRG");
			representantes.add(dto);
		}

		if (perfil < PERFIL_FEDERAL) {
			RepresentanteDTO dto = new RepresentanteDTO();
			dto.setId(REP_FEDERAL);
			dto.setDescripcion("Representante Federal");
			representantes.add(dto);
		}

		return representantes;
	}

	@Override
	public List<SeccionDTO> getSeccionesByMunicipio(long idUsuario, long idPerfil, Long idMunicipio) {

		Usuario usuario = usuarioRepository.findById(idUsuario);

		List<SeccionDTO> seccionDTOs = null;

		if (usuario.getPerfil() > PERFIL_MUNICIPAL) {

			List<SeccionElectoral> seccionElectoral = seccionRepository.getByMunicipio(usuario.getMunicipio());
			seccionDTOs = MapperUtil.mapAll(seccionElectoral, SeccionDTO.class);

		} else {

			List<SeccionElectoral> seccionElectoral = seccionRepository.getByMunicipio(idMunicipio);

			seccionDTOs = MapperUtil.mapAll(seccionElectoral, SeccionDTO.class);

		}

		return seccionDTOs;
	}

	@Override
	public List<CargoDTO> getCargos(Long tipoRepresentante) throws CatalogoException {

		List<Cargo> cargos = cargosRepository.getCargos(tipoRepresentante);
		List<CargoDTO> cargoDTOs = null;

		if (cargos != null) {

			cargoDTOs = MapperUtil.mapAll(cargos, CargoDTO.class);
		} else {
			throw new CatalogoException("No se encontraron datos", 401);
		}

		return cargoDTOs;
	}

	@Override
	public List<DistritoFederalDTO> getLocalByEntidad(long idUsuario, long perfil, Long idEntidad) {
		Usuario usuario = usuarioRepository.findById(idUsuario);

		List<DistritoFederalDTO> distritoFederalDTOs = null;

		if (usuario.getPerfil() > PERFIL_ESTATAL) {

			DistritoFederal distritoFederal = localRepository.findById(usuario.getDistritoLocal());
			DistritoFederalDTO dto = new DistritoFederalDTO();
			MapperUtil.map(distritoFederal, dto);

			distritoFederalDTOs = new ArrayList<DistritoFederalDTO>();
			distritoFederalDTOs.add(dto);

		} else {

			List<DistritoFederal> distritoFederals = localRepository.findByEntidad(idEntidad);

			distritoFederalDTOs = MapperUtil.mapAll(distritoFederals, DistritoFederalDTO.class);

		}

		return distritoFederalDTOs;
	}

	@Override
	public List<IncidenciaDTO> getIncidencias() throws CatalogoException {
		
		List<Incidencias> incidencias = incidenciaRepository.findAll();

		List<IncidenciaDTO> dtos = new ArrayList<IncidenciaDTO>();

		dtos = MapperUtil.mapAll(incidencias, IncidenciaDTO.class);

		return dtos;
	}

	@Override
	public List<EleccionDTO> getEleccion() throws CatalogoException {
		List<Eleccion> eleccion = eleccionRepository.findAll();

		List<EleccionDTO> dtos = new ArrayList<EleccionDTO>();

		dtos = MapperUtil.mapAll(eleccion, EleccionDTO.class);

		return dtos;
	}

	@Override
	public List<SeccionUserDTO> getSeccionByBrigadista(Long idUsuario) throws CatalogoException {
		
		
		List<SeccionElectoral> seccion = seccionRepository.getSeccionByUser(idUsuario);

		List<SeccionUserDTO> dtos = new ArrayList<SeccionUserDTO>();

		dtos = MapperUtil.mapAll(seccion, SeccionUserDTO.class);

		return dtos;
	}

	@Override
	public List<TipoActasDTO> getTipoActas(Long idPerfil) throws CatalogoException {
		
		if(idPerfil == PERFIL_RC) {
			List<TipoActas> actas = eleccionRepository.findAllActas();
	
			List<TipoActasDTO> dtos = new ArrayList<TipoActasDTO>();
	
			dtos = MapperUtil.mapAll(actas, TipoActasDTO.class);
	
			return dtos;
		}else {
			throw new CatalogoException("No tiene permisos suficientes para consultar el catalogo", 401);
		}
	}

	@Override
	public List<RepresentacionPartidosDTO> getRepresentacionPartidos() throws CatalogoException {
		List<RepresentacionPartidos> partidos = partidoVotacionRepository.getPartidos();
		
		List<RepresentacionPartidosDTO> dtos = new ArrayList<RepresentacionPartidosDTO>();

		dtos = MapperUtil.mapAll(partidos, RepresentacionPartidosDTO.class);

		return dtos;
	}

//	@Override
//	public List<ZonaDTO> getZonas(long usuario, long idPerfil, Long idFederal) throws CatalogoException {
//		
//		List<Zona> zonas = cargosRepository.getZonas(idFederal);
//		
//		List<ZonaDTO> zonasDTO = null;
//
//		if (zonas != null) {
//
//			zonasDTO = MapperUtil.mapAll(zonas, ZonaDTO.class);
//		} else {
//			throw new CatalogoException("No se encontraron datos", 401);
//		}
//
//		return zonasDTO;
//	}

}
