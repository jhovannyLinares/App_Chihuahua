package mx.morena.negocio.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.dto.LocalidadDTO;
import mx.morena.negocio.dto.MunicipioDTO;
import mx.morena.negocio.servicios.ICatalogoService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Entidad;
import mx.morena.persistencia.entidad.Localidad;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.Usuario;
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

//	@Autowired
//	private IDistritoLocalRepository localRepository;

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

	@Override
	public List<EntidadDTO> getEntidades() {

		List<Entidad> entidades = entidadRepository.findAll();

		List<EntidadDTO> dtos = new ArrayList<EntidadDTO>();

		dtos = MapperUtil.mapAll(entidades, EntidadDTO.class);

		return dtos;

	}

	@Override
	public List<DistritoFederalDTO> getFederalByEntidad(long idUsuario, long idPerfil, String idEntidad) {

		Usuario usuario = usuarioRepository.findById(idUsuario);

		List<DistritoFederalDTO> distritoFederalDTOs = null;

		if (usuario.getPerfil() > PERFIL_ESTATAL) {

			DistritoFederal distritoFederal = federalRepocitory.getById(usuario.getFederal());
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
	public List<MunicipioDTO> getMunicipioByFederal(long idUsuario, long idPerfil, String idFederal) {

		Usuario usuario = usuarioRepository.findById(idUsuario);

		List<MunicipioDTO> municipioDTOs = null;

		if (usuario.getPerfil() > PERFIL_FEDERAL) {

			Municipio municipio = municipioRepository.getById(usuario.getMunicipio());
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
	public List<LocalidadDTO> getLocalidadByMunicipio(long idUsuario, long idPerfil, String municipio) {

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

}
