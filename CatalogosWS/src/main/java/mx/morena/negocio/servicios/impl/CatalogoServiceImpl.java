package mx.morena.negocio.servicios.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import mx.morena.persistencia.repository.ISeccionElectoralRepository;
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

	@Autowired
	private ISeccionElectoralRepository seccionRepository;

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

		}else {
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
		}

		
		return dtos;
	}

	@Override
	public List<MunicipioDTO> getMunicipioByLocal(long idUsuario, long idPerfil, Long idLocal) {

		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

		List<MunicipioDTO> dtos = null;

		if (idPerfil == PERFIL_ESTATAL || idPerfil == PERFIL_FEDERAL ||idPerfil == PERFIL_LOCAL) {

			Optional<DistritoLocal> local = localRepository.findById(idLocal);
			List<Municipio> municipios = local.get().getMunicipios();

			dtos = new ArrayList<MunicipioDTO>();

			dtos = MapperUtil.mapAll(municipios, MunicipioDTO.class);
		}else {
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
		}

		
		return dtos;

		
	}

	@Override
	public List<LocalidadDTO> getLocalidadByMunicipio(long idUsuario, long idPerfil, Long idMunicipio) {

		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

		List<LocalidadDTO> dtos = null;

		if (idPerfil == PERFIL_ESTATAL || idPerfil == PERFIL_FEDERAL || idPerfil == PERFIL_LOCAL || idPerfil == PERFIL_MUNICIPAL) {

			Optional<Municipio> municipio  = municipioRepository.findById(idMunicipio);
			List<Localidad> localidades = municipio.get().getLocalidades();

			dtos = new ArrayList<LocalidadDTO>();

			dtos = MapperUtil.mapAll(localidades, LocalidadDTO.class);

		} else {
			
			Optional<Municipio> municipio  = municipioRepository.findById(idMunicipio);
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
		}

		
		return dtos;

	}

	@Override
	public List<SeccionDTO> getSeccionByLocalidad(long idUsuario, long idPerfil, Long idLocalidad) {

		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

		List<SeccionDTO> dtos = null;

		if (idPerfil == PERFIL_ESTATAL || idPerfil == PERFIL_FEDERAL || idPerfil == PERFIL_LOCAL || idPerfil == PERFIL_MUNICIPAL) {

			Optional<Localidad> localidad  = localidadRepository.findById(idLocalidad);
			List<SeccionElectoral> secciones = localidad.get().getSeccionElectorales();

			dtos = new ArrayList<SeccionDTO>();

			dtos = MapperUtil.mapAll(secciones, SeccionDTO.class);

		} else {
			
			Optional<Localidad> localidad  = localidadRepository.findById(idLocalidad);
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
		}
		
		return dtos;
	}
}
