package mx.morena.negocio.servicios.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.EntidadDTO;
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
	public List<DistritoFederal> getFederalByEntidad(HttpServletResponse response, Long id) { //

//		TODO: Falta filtrar la entidad
		// TODO: Falta la extrasccuion de nivel de Perfil
		// Falta la aplicaicon de catalogos
		// Falta la convercion de entiodades a DTO's
		List<DistritoFederal> federal = federalRepocitory.findAll();

		return federal;
	}

	@Override
	public List<DistritoLocal> getLocalByFederal(HttpServletResponse response, Long id) {

		// TODO: Falta la extrasccuion de nivel de Perfil
		// Falta la aplicaicon de catalogos
		// Falta la convercion de entiodades a DTO's

		List<DistritoLocal> local = localRepository.findAll();

		return local;
	}

	@Override
	public List<Municipio> getMunicipioByLocal(HttpServletResponse response, Long id) {
		// TODO: Falta la extrasccuion de nivel de Perfil
		// Falta la aplicaicon de catalogos
		// Falta la convercion de entiodades a DTO's

//		List<Municipio> muncipio = localRepository.findById(id).get().getMunicipios();

		List<Municipio> muncipio = municipioRepository.findAll();

//		List<Municipio> muncipio = municipioRepository.findByLocal(id);

		return muncipio;
	}

	@Override
	public List<Localidad> getLocalidadByMunicipio(HttpServletResponse response, Long id) {
		// TODO: Falta la extrasccuion de nivel de Perfil
		// Falta la aplicaicon de catalogos
		// Falta la convercion de entiodades a DTO's

//		List<Localidad> localidad = municipioRepository.findById(id).get().getLocalidades();

		List<Localidad> localidad = localidadRepository.findAll();

//		List<Localidad> localidad = localidadRepository.findByMunicipio(id);

		return localidad;
	}

	@Override
	public List<SeccionElectoral> getSeccionByLocalidad(HttpServletResponse response, Long id) {
		// TODO: Falta la extrasccuion de nivel de Perfil
		// Falta la aplicaicon de catalogos
		// Falta la convercion de entiodades a DTO's

//		List<SeccionElectoral> seccion = localidadRepository.findById(id).get().getSeccionElectorales();

		List<SeccionElectoral> seccion = seccionRepository.findAll();

//		List<SeccionElectoral> seccion = seccionRepository.findByLocalidad(id);

		return seccion;
	}

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

}
