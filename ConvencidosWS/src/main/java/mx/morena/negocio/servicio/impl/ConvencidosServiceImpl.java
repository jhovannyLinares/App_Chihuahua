package mx.morena.negocio.servicio.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	
	private static final char ESTATUS_ALTA = 'A';
	private static String sinClave = "No Cuenta con ClaveElector";

	@Override
	public List<ConvencidosDTO> getConvencidos(String distritoFederalId, String idMunicipio, String idSeccion, String claveElector)
			throws ConvencidosException {
		List<Convencidos> lstConv = null;
		List<ConvencidosDTO> lstDto = new ArrayList<ConvencidosDTO>();
		
		if (distritoFederalId != null) {
			DistritoFederal dFederal = dFederalRepository.getById(distritoFederalId);
			lstConv = convencidosRepository.getByDistritoFederal(dFederal);
		}
		if (idMunicipio != null) {
			Municipio municipio = municipioRepository.getById(idMunicipio);
			lstConv = convencidosRepository.getByMunicipio(municipio);
		}
		
//		if (idSeccion != null) {
//			List<SeccionElectoral> seccion = seccionRepository.findByCotId(idSeccion);
//			lstConv = convencidosRepository.getBySeccionesElectoralesIn(seccion);
//		}
		
		if (claveElector != null) {
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

		if (existeClave == null || existeClave.getClaveElector()==sinClave.toUpperCase()) {
			Convencidos convencidos = new Convencidos();
			
			System.out.println("entidad "+ dto.getIdEstado());
			Entidad estado = entidadRepository.findById(dto.getIdEstado());
			DistritoFederal dFederal = dFederalRepository.getById(dto.getIdFederal());
			Municipio municipio = municipioRepository.getById(dto.getIdMunicipio());
			Usuario usuario = usuarioRepository.findById(idUsuario);
			
			dto.setEstatus(ESTATUS_ALTA);
			dto.setFechaRegistro(new Date());
			
			MapperUtil.map(dto, convencidos);
			if(!dto.getIsClaveElector()) {
			convencidos.setClaveElector(sinClave);
			}
			convencidos.setEstado(estado);
			convencidos.setDistritoFederal(dFederal);
			convencidos.setMunicipio(municipio);
			convencidos.setUsuario(usuario);
			convencidos.setFechaSistema(Calendar.getInstance());
			
			convencidosRepository.save(convencidos);
			
			return convencidos.getId();
		}else {
			throw new ConvencidosException("La clave de elector ya se encuentra registrada", 204);
		}
		
	}

	@Override
	public boolean findByClaveElector(String claveElector) throws ConvencidosException {
		Convencidos convencidos = convencidosRepository.findByClaveElector(claveElector);
		if(convencidos==null) {
		return false;
		}else {
			return true;
		}
	}



}