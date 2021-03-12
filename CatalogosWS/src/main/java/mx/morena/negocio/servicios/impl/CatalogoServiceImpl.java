package mx.morena.negocio.servicios.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.CatalogoDTO;
import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.DistritoLocalDTO;
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.dto.LocalidadDTO;
import mx.morena.negocio.dto.MunicipioDTO;
import mx.morena.negocio.dto.SeccionDTO;
import mx.morena.negocio.servicios.ICatalogoService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.DistritoLocal;
import mx.morena.persistencia.entidad.Entidad;
import mx.morena.persistencia.entidad.Localidad;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IDistritoLocalRepository;
import mx.morena.persistencia.repository.IEntidadRepository;
import mx.morena.persistencia.repository.ILocalidadRepository;
import mx.morena.persistencia.repository.IMunicipioRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;

@Service
public class CatalogoServiceImpl implements ICatalogoService {

	@Autowired
	private IDistritoFederalRepository federalRepocitory;

	@Autowired
	private IDistritoLocalRepository localRepository;

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

	private static final Integer PERFIL_ESTATAL = 1;
	private static final Integer PERFIL_FEDERAL = 2;
	private static final Integer PERFIL_LOCAL = 3;
	private static final Integer PERFIL_MUNICIPAL = 4;

	@Override
	public List<EntidadDTO> getEntidades() {

		List<Entidad> entidades = entidadRepository.findAll();

		List<EntidadDTO> dtos = new ArrayList<EntidadDTO>();

		dtos = MapperUtil.mapAll(entidades, EntidadDTO.class);

		return dtos;

	}

	@Override
	public List<DistritoFederalDTO> getFederalByEntidad(long idUsuario, long idPerfil, Long idEntidad) {

		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

		List<DistritoFederalDTO> dtos = null;

		if (idPerfil == PERFIL_ESTATAL) {

			Optional<Entidad> entidad = entidadRepository.findById(idEntidad);
			List<DistritoFederal> distritoFederals = entidad.get().getDistritosFederales();

			dtos = new ArrayList<DistritoFederalDTO>();

			dtos = MapperUtil.mapAll(distritoFederals, DistritoFederalDTO.class);

//			for ( DistritoFederalDTO df : dtos ) {
//				
//				df.setEntidadId(idEntidad);
//			}

		} else {

			Optional<Entidad> entidad = entidadRepository.findById(idEntidad);
			List<DistritoFederal> distritoFederals = entidad.get().getDistritosFederales();

			dtos = new ArrayList<DistritoFederalDTO>();

			for (DistritoFederal distritoFederal : distritoFederals) {
				if (distritoFederal.getId() == usuarioOptional.get().getFederal().getId()) {

					DistritoFederalDTO distritoFederalDTO = new DistritoFederalDTO();
					MapperUtil.map(distritoFederal, distritoFederalDTO);

					dtos.add(distritoFederalDTO);

				}
			}
//			for ( DistritoFederalDTO df : dtos ) {
//				
//				df.setEntidadId(idEntidad);
//			}

		}

		return dtos;
	}

	@Override
	public List<DistritoLocalDTO> getLocalByFederal(long idUsuario, long idPerfil, Long idFederal) {

		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

		List<DistritoLocalDTO> dtos = null;

		if (idPerfil == PERFIL_ESTATAL || idPerfil == PERFIL_FEDERAL) {

			Optional<DistritoFederal> federal = federalRepocitory.findById(idFederal);
			List<DistritoLocal> distritoLocals = federal.get().getDistritosLocales();

			dtos = new ArrayList<DistritoLocalDTO>();

			dtos = MapperUtil.mapAll(distritoLocals, DistritoLocalDTO.class);

//			for ( DistritoLocalDTO dl : dtos ) {
//				
//				dl.setFederalId(idFederal);
//			}

		} else {
			Optional<DistritoFederal> federal = federalRepocitory.findById(idFederal);
			List<DistritoLocal> distritoLocals = federal.get().getDistritosLocales();

			dtos = new ArrayList<DistritoLocalDTO>();

			for (DistritoLocal distritoLocal : distritoLocals) {
				if (distritoLocal.getId() == usuarioOptional.get().getLocal().getId()) {

					DistritoLocalDTO distritoLocalDTO = new DistritoLocalDTO();
					MapperUtil.map(distritoLocal, distritoLocalDTO);

					dtos.add(distritoLocalDTO);
				}
			}
//			for ( DistritoLocalDTO dl : dtos ) {
//				
//				dl.setFederalId(idFederal);
//			}
		}

		return dtos;
	}

	@Override
	public List<MunicipioDTO> getMunicipioByLocal(long idUsuario, long idPerfil, Long idLocal) {

		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

		List<MunicipioDTO> dtos = null;

		if (idPerfil == PERFIL_ESTATAL || idPerfil == PERFIL_FEDERAL || idPerfil == PERFIL_LOCAL) {

			Optional<DistritoLocal> local = localRepository.findById(idLocal);
			List<Municipio> municipios = local.get().getMunicipios();

			dtos = new ArrayList<MunicipioDTO>();

			dtos = MapperUtil.mapAll(municipios, MunicipioDTO.class);

//			for ( MunicipioDTO mn : dtos ) {
//				
//				mn.setLocalId(idLocal);
//			}

		} else {
			Optional<DistritoLocal> local = localRepository.findById(idLocal);
			List<Municipio> municipios = local.get().getMunicipios();

			dtos = new ArrayList<MunicipioDTO>();

			dtos = MapperUtil.mapAll(municipios, MunicipioDTO.class);

			for (Municipio municipio : municipios) {
				if (municipio.getId() == usuarioOptional.get().getMunicipio().getId()) {

					MunicipioDTO municipioDTO = new MunicipioDTO();
					MapperUtil.map(municipio, municipioDTO);

					dtos.add(municipioDTO);
				}
			}
//			for ( MunicipioDTO mn : dtos ) {
//				
//				mn.setLocalId(idLocal);
//			}
		}

		return dtos;

	}

	@Override
	public List<LocalidadDTO> getLocalidadByMunicipio(long idUsuario, long idPerfil, Long idMunicipio) {

		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

		List<LocalidadDTO> dtos = null;

		if (idPerfil == PERFIL_ESTATAL || idPerfil == PERFIL_FEDERAL || idPerfil == PERFIL_LOCAL
				|| idPerfil == PERFIL_MUNICIPAL) {

			Optional<Municipio> municipio = municipioRepository.findById(idMunicipio);
			List<Localidad> localidades = municipio.get().getLocalidades();

			dtos = new ArrayList<LocalidadDTO>();

			dtos = MapperUtil.mapAll(localidades, LocalidadDTO.class);

//			for ( LocalidadDTO lc : dtos ) {
//				
//				lc.setMunicipioId(idMunicipio);
//			}

		} else {

			Optional<Municipio> municipio = municipioRepository.findById(idMunicipio);
			List<Localidad> localidades = municipio.get().getLocalidades();

			dtos = new ArrayList<LocalidadDTO>();

			dtos = MapperUtil.mapAll(localidades, LocalidadDTO.class);

			for (Localidad localidad : localidades) {
				if (localidad.getId() == usuarioOptional.get().getLocalidad().getId()) {

					LocalidadDTO localidadDTO = new LocalidadDTO();
					MapperUtil.map(localidad, localidadDTO);

					dtos.add(localidadDTO);
				}
			}

//			for ( LocalidadDTO lc : dtos ) {
//				
//				lc.setMunicipioId(idMunicipio);
//			}
		}

		return dtos;

	}

	@Override
	public List<SeccionDTO> getSeccionByLocalidad(long idUsuario, long idPerfil, Long idLocalidad) {

		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

		List<SeccionDTO> dtos = null;

		if (idPerfil == PERFIL_ESTATAL || idPerfil == PERFIL_FEDERAL || idPerfil == PERFIL_LOCAL
				|| idPerfil == PERFIL_MUNICIPAL) {

			Optional<Localidad> localidad = localidadRepository.findById(idLocalidad);
			List<SeccionElectoral> secciones = localidad.get().getSeccionElectorales();

			dtos = new ArrayList<SeccionDTO>();

			dtos = MapperUtil.mapAll(secciones, SeccionDTO.class);

			for (SeccionDTO sc : dtos) {

				sc.setLocalidadId(idLocalidad);
			}

		} else {

			Optional<Localidad> localidad = localidadRepository.findById(idLocalidad);
			List<SeccionElectoral> secciones = localidad.get().getSeccionElectorales();

			dtos = new ArrayList<SeccionDTO>();

			dtos = MapperUtil.mapAll(secciones, SeccionDTO.class);

			for (SeccionElectoral seccion : secciones) {
				if (seccion.getId() == usuarioOptional.get().getSeccionElectoral().getId()) {

					SeccionDTO seccionDTO = new SeccionDTO();
					MapperUtil.map(seccion, seccionDTO);

					dtos.add(seccionDTO);
				}
			}
			for (SeccionDTO sc : dtos) {

				sc.setLocalidadId(idLocalidad);
			}
		}

		return dtos;
	}

	@Override
	public CatalogoDTO getCatalogos(long idUsuario, long idPerfil) {

		Usuario usuario = usuarioRepository.findById(idUsuario).get();

		Entidad entidad = entidadRepository.findById(usuario.getEntidad().getId()).get();

		EntidadDTO dto = new EntidadDTO();

		MapperUtil.map(entidad, dto);

		List<DistritoFederal> distritoFederals = entidad.getDistritosFederales();
		distritoFederals = limpiarDistritosFederales(distritoFederals, usuario, idPerfil);
		List<DistritoFederalDTO> distritoFederalDTOs = MapperUtil.mapAll(distritoFederals, DistritoFederalDTO.class);

		for (int i = 0; i < distritoFederals.size(); i++) {

			DistritoFederal distritoFederal = distritoFederals.get(i);
			List<DistritoLocal> distritoLocals = distritoFederal.getDistritosLocales();
			distritoLocals = limpiarDistritosLocales(distritoLocals, usuario, idPerfil);
			List<DistritoLocalDTO> distritoLocalDTOs = MapperUtil.mapAll(distritoLocals, DistritoLocalDTO.class);

			for (int i2 = 0; i2 < distritoLocals.size(); i2++) {

				DistritoLocal distritoLocal = distritoLocals.get(i2);
				List<Municipio> municipios = distritoLocal.getMunicipios();
				municipios = limpiarMunicipios(municipios, usuario, idPerfil);
				List<MunicipioDTO> municipioDTOs = MapperUtil.mapAll(municipios, MunicipioDTO.class);

				for (int i3 = 0; i3 < municipios.size(); i3++) {
					Municipio municipio = municipios.get(i3);
					List<Localidad> localidades = municipio.getLocalidades();
					localidades = limpiarLocalidades(localidades, usuario, idPerfil);
					List<LocalidadDTO> localidadesDtos = MapperUtil.mapAll(localidades, LocalidadDTO.class);
					municipioDTOs.get(i3).setLocalidades(localidadesDtos);
				}
				distritoLocalDTOs.get(i2).setMunicipios(municipioDTOs);
			}
			distritoFederalDTOs.get(i).setDistritosLocales(distritoLocalDTOs);
		}

		dto.setDistritosFederales(distritoFederalDTOs);
		CatalogoDTO catalogo = new CatalogoDTO();
		catalogo.setEntidad(dto);

		return catalogo;
	}

	private List<DistritoFederal> limpiarDistritosFederales(List<DistritoFederal> distritoFederals, Usuario usuario,
			long idPerfil) {

		List<DistritoFederal> distritoFederalNew = new ArrayList<DistritoFederal>();
		if (idPerfil > PERFIL_ESTATAL) {
			for (DistritoFederal distritoFederal : distritoFederals) {
				if (distritoFederal.getId().equals(usuario.getFederal().getId())) {
					distritoFederalNew.add(distritoFederal);
				}
			}
		} else {
			distritoFederalNew = distritoFederals;
		}

		return distritoFederalNew;

	}

	private List<DistritoLocal> limpiarDistritosLocales(List<DistritoLocal> distritoLocals, Usuario usuario,
			long idPerfil) {

		List<DistritoLocal> distritoLocalsNew = new ArrayList<DistritoLocal>();
		if (idPerfil > PERFIL_FEDERAL) {
			for (DistritoLocal distritoLocal : distritoLocals) {
				if (distritoLocal.getId() == usuario.getLocal().getId()) {
					distritoLocalsNew.add(distritoLocal);
				}
			}
		} else {
			distritoLocalsNew = distritoLocals;
		}

		return distritoLocalsNew;
	}

	private List<Municipio> limpiarMunicipios(List<Municipio> municipios, Usuario usuario, long idPerfil) {

		List<Municipio> municipiosNew = new ArrayList<Municipio>();
		if (idPerfil > PERFIL_LOCAL) {
			for (Municipio municipio : municipios) {
				if (municipio.getId() == usuario.getMunicipio().getId()) {
					municipiosNew.add(municipio);
				}
			}
		} else {
			municipiosNew = municipios;
		}

		return municipiosNew;

	}

	private List<Localidad> limpiarLocalidades(List<Localidad> localidades, Usuario usuario, long idPerfil) {

		List<Localidad> localidadNew = new ArrayList<Localidad>();

		if (idPerfil > PERFIL_MUNICIPAL) {
			for (Localidad localidad : localidades) {
				if (localidad.getId() == usuario.getLocalidad().getId()) {
					localidadNew.add(localidad);
				}
			}
		} else {
			localidadNew = localidades;
		}

		return localidadNew;
	}

}
