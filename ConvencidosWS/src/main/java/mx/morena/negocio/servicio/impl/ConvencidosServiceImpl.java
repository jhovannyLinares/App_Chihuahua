package mx.morena.negocio.servicio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ConvencidosDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IConvencidosService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Entidad;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.IConvencidosRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IEntidadRepository;
import mx.morena.persistencia.repository.IMunicipioRepository;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;

@Service
public class ConvencidosServiceImpl implements IConvencidosService {

	@Autowired
	private IConvencidosRepository convencidosRepository;
	
	@Autowired
	private IDistritoFederalRepository dFederalRepository;
	
	@Autowired
	private IEntidadRepository entidadRepository;
	
	@Autowired
	private IMunicipioRepository municipioRepository;
	
	@Autowired
	private ISeccionElectoralRepository seccionRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public List<ConvencidosDTO> getConvencidos(Long distritoFederalId, Long idMunicipio, Long idSeccion, String claveElector)
			throws ConvencidosException {
		List<Convencidos> lstConv = null;
		List<ConvencidosDTO> lstDto = new ArrayList<ConvencidosDTO>();
		if (distritoFederalId != null) {
			System.out.println("id federal " + distritoFederalId);
			lstConv = convencidosRepository.getByDistritoFederal(distritoFederalId);
		}
		if (idMunicipio != null) {
			lstConv = convencidosRepository.getByMunicipio(idMunicipio);
		}
		if (idSeccion != null) {
			lstConv = convencidosRepository.getBySeccionesElectorales(idSeccion);
		}
		if (claveElector != null) {
			System.out.println("clave elector "+ claveElector);
			lstConv = convencidosRepository.getByClaveElector(claveElector);
		}

		lstDto = MapperUtil.mapAll(lstConv, ConvencidosDTO.class);
		if (!lstDto.isEmpty()) {
			return lstDto;
		} else {
			throw new ConvencidosException("No se encontro ningun usuario con el parametro ingresado", 204);
		}

	}

	@Override
	public Long save(long idUsuario, ConvencidosDTO dto) throws ConvencidosException {
		
		Convencidos existeClave = convencidosRepository.findByClaveElector(dto.getClaveElector());

		if (existeClave == null) {
			Convencidos convencidos = new Convencidos();
			
			Entidad estado = entidadRepository.findById(dto.getIdEstado()).get();
			DistritoFederal dFederal = dFederalRepository.findById(dto.getIdFederal()).get();
			Municipio municipio = municipioRepository.findById(dto.getIdMunicipio()).get();
			SeccionElectoral seccion = seccionRepository.findById(dto.getIdSeccion()).get();
			Usuario usuario = usuarioRepository.findById(idUsuario).get();
			
			MapperUtil.map(dto, convencidos);
			
			convencidos.setEstado(estado);
			convencidos.setDistritoFederal(dFederal);
			convencidos.setMunicipio(municipio);
			//convencidos.setSeccionesElectorales();
			convencidos.setUsuario(usuario);
			
			convencidosRepository.save(convencidos);
			
			return convencidos.getId();
		}else {
			throw new ConvencidosException("La clave de elector ya se encuentra registrada", 204);
		}
		
	}



}