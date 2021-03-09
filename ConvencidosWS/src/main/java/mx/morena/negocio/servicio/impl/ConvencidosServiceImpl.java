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
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.IConvencidosRepository;

@Service
public class ConvencidosServiceImpl implements IConvencidosService {

	@Autowired
	private IConvencidosRepository convencidosRepository;

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
	public Long save(long usuario, ConvencidosDTO dto) throws ConvencidosException {
		
		Convencidos existeClave = convencidosRepository.findByClaveElector(dto.getClaveElector());

		if (existeClave == null) {
			Convencidos convencidos = new Convencidos();
			MapperUtil.map(dto, convencidos);
			convencidosRepository.save(convencidos);
			
			return convencidos.getId();
		}else {
			throw new ConvencidosException("La clave de elector ya se encuentra registrada", 204);
		}
		
	}



}