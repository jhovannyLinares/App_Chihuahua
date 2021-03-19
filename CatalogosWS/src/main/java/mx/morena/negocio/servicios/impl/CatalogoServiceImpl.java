package mx.morena.negocio.servicios.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.CasillaDTO;
import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.dto.LocalidadDTO;
import mx.morena.negocio.dto.MunicipioDTO;
import mx.morena.negocio.dto.offline.CatalogoDTOOffline;
import mx.morena.negocio.dto.offline.DistritoFederalDTOOffline;
import mx.morena.negocio.dto.offline.EntidadDTOOffline;
import mx.morena.negocio.dto.offline.LocalidadDTOOffline;
import mx.morena.negocio.dto.offline.MunicipioDTOOffline;
import mx.morena.negocio.servicios.ICatalogoService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Casilla;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Entidad;
import mx.morena.persistencia.entidad.Localidad;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IEntidadRepository;
import mx.morena.persistencia.repository.ILocalidadRepository;
import mx.morena.persistencia.repository.IMunicipioRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class CatalogoServiceImpl extends MasterService implements ICatalogoService {

	@Autowired
	private IDistritoFederalRepository federalRepocitory;

	@Autowired
	private IMunicipioRepository municipioRepository;

	@Autowired
	private ILocalidadRepository localidadRepository;

//	@Autowired
//	private ISeccionElectoralRepository seccionRepository;

	@Autowired
	private IEntidadRepository entidadRepository;

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Autowired
	private ICasillaRepository casillaRepository;

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
	public List<MunicipioDTO> getMunicipioByFederal(long idUsuario, long idPerfil, Long idFederal) {

		Usuario usuario = usuarioRepository.findById(idUsuario);

		List<MunicipioDTO> municipioDTOs = null;

		if (usuario.getPerfil() > PERFIL_FEDERAL) {

			Municipio municipio = municipioRepository.findById(usuario.getMunicipio());
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

	@Override
	public List<LocalidadDTO> getLocalidadByMunicipio(long idUsuario, long idPerfil, Long municipio) {

		Usuario usuario = usuarioRepository.findById(idUsuario);

		List<LocalidadDTO> localidadDTOs = null;

		if (usuario.getPerfil() > PERFIL_MUNICIPAL) {

			Localidad localidad = localidadRepository.getById(usuario.getLocalidad());
			LocalidadDTO dto = new LocalidadDTO();
			MapperUtil.map(localidad, dto);

			localidadDTOs = new ArrayList<LocalidadDTO>();
			localidadDTOs.add(dto);

		} else {

			List<Localidad> municipios = localidadRepository.getByMunicipio(municipio);

			localidadDTOs = MapperUtil.mapAll(municipios, LocalidadDTO.class);

		}

		return localidadDTOs;
	}

	@Override
	public CatalogoDTOOffline getCatalogos(long usuarioId, long perfilId) {

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

				List<LocalidadDTO> localidadDTOs = getLocalidadByMunicipio(usuarioId, perfilId, municipioDTO.getId());

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
			Long seccionId) {

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

		dtos = MapperUtil.mapAll(casillas, CasillaDTO.class);

		return dtos;
	}

	@Override
	public Map<Byte, String> getRepresentantes(long perfil) {
		Map<Byte, String> representantes = new HashMap<Byte, String>();

		if (perfil < PERFIL_RC) {
			representantes.put(REP_RC, "Representante RC");
		}

		if (perfil < PERFIL_RG) {
			representantes.put(REP_RG, "Representante RG");
		}

		if (perfil < PERFIL_MUNICIPAL) {
			representantes.put(REP_MUNICIPAL, "Representante Municipal");
		}

		if (perfil < PERFIL_LOCAL) {
			representantes.put(REP_LOCAL, "Representante Local");
			representantes.put(REP_CRG, "Representante CRG");
		}

		if (perfil < PERFIL_FEDERAL) {
			representantes.put(REP_FEDERAL, "Representante Federal");
		}

		return representantes;
	}

}
