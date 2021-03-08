package mx.morena.negocio.servicio.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ConvencidosDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IConvencidosService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.repository.IConvencidosRepository;

@Service
public class ConvencidosServiceImpl implements IConvencidosService {

	@Autowired
	private IConvencidosRepository convencidosRepository;

	@Override
	public List<ConvencidosDTO> getConvencidos(Long idFederal, Long idMunicipio, Long idSeccion, String claveElector)
			throws ConvencidosException {
		List<Convencidos> lstConv = null;
		List<ConvencidosDTO> lstDto = new ArrayList<ConvencidosDTO>();
		if (idFederal != null) {
			lstConv = convencidosRepository.getByDistritoFederal(idFederal);
		}
		if (idMunicipio != null) {
			lstConv = convencidosRepository.getByMunicipio(idMunicipio);
		}
		if (idSeccion != null) {
			lstConv = convencidosRepository.getBySeccionesElectorales(idSeccion);
		}
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
	public String save(ConvencidosDTO dto) throws ConvencidosException {
		Convencidos convencidos = new Convencidos();
		Convencidos existeClave = convencidosRepository.findByClaveElector(dto.getClaveElector());

		if (existeClave == null) {
			MapperUtil.map(dto, convencidos);
			convencidosRepository.save(convencidos);
		}else {
			throw new ConvencidosException("La cale de elector ya se encuentra registrada", 200);
		}
		return "" + dto.toString();
	}

//	@Override
//	public ConvencidosDTO getClaveElector(String claveElector) {
//		ConvencidosDTO dto = new ConvencidosDTO();
//		Convencidos conv = convencidosRepository.findByClaveElector(claveElector);
//		MapperUtil.map(conv, dto);
//		
//		return dto;
//	}

}
