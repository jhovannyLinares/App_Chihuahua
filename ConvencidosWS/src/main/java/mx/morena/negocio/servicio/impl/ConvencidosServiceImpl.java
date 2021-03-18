package mx.morena.negocio.servicio.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ConvencidosDTO;
import mx.morena.negocio.dto.ConvencidosResponseDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IConvencidosService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.repository.IConvencidosRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ConvencidosServiceImpl extends MasterService implements IConvencidosService {

	@Autowired
	private IConvencidosRepository convencidosRepository;

	private static final char ESTATUS_ALTA = 'A';
	private static String sinClave = "No cuenta con Clave Elector";
	private static String sinCalle = "No se cuenta con calle";

	@Override
	public List<ConvencidosResponseDTO> getConvencidos(Long distritoFederalId, Long idMunicipio, Long idSeccion,
			String claveElector) throws ConvencidosException {

		List<Convencidos> lstConv = null;
		List<ConvencidosResponseDTO> lstDto = new ArrayList<ConvencidosResponseDTO>();

		if (distritoFederalId != null) {
			lstConv = convencidosRepository.getByDistritoFederal(distritoFederalId, CONVENCIDO);
		}
		if (idMunicipio != null) {
			lstConv = convencidosRepository.getByMunicipio(idMunicipio, CONVENCIDO);
		}

		if (idSeccion != null) {
			lstConv = convencidosRepository.getBySeccionesElectoralesIn(idSeccion, CONVENCIDO);
		}

		if (claveElector != null) {
			lstConv = convencidosRepository.findByClaveElector(claveElector);
		}

		if (lstConv != null) {
			lstDto = MapperUtil.mapAll(lstConv, ConvencidosResponseDTO.class);
			return lstDto;

		} else {
			throw new ConvencidosException("No se encontro ningun usuario con el parametro ingresado", 404);
		}

	}

	@Override
	public Long save(long idUsuario, ConvencidosDTO dto) throws ConvencidosException {

		if(dto.getIsClaveElector() == true) {
			dto.setClaveElector(null);
		}
		
		if (dto.getIsClaveElector() == true || dto.getClaveElector().length() == 18) {
			List<Convencidos> convencidoEx = convencidosRepository.findByClaveElector(dto.getClaveElector());

			if (convencidoEx != null) {
				throw new ConvencidosException("La clave de elector ya se encuentra registrada", 409);
			} else {

				Convencidos convencido = new Convencidos();

				MapperUtil.map(dto, convencido);
				if (dto.getIsClaveElector()) {
					convencido.setClaveElector(sinClave);
				}
				if(dto.getIsCalle()) {
					convencido.setCalle(sinCalle);
				}
				convencido.setTipo(CONVENCIDO);
				convencido.setFechaRegistro(new Date());
				convencido.setEstatus(ESTATUS_ALTA);
				convencido.setIdSeccion(dto.getIdSeccion());
				convencido.setIdEstado(dto.getIdEstado());
				convencido.setIdFederal(dto.getIdFederal());
				convencido.setIdMunicipio(dto.getIdMunicipio());
				convencido.setUsuario(idUsuario);
				convencido.setFechaSistema(new Timestamp(new Date().getTime()));

				convencidosRepository.save(convencido);

				return convencidosRepository.getIdMax();

			}
		} else {
			throw new ConvencidosException("El numero de caracteres ingresado en la clave de elector es incorrecto",
					400);
		}

	}

	@Override
	public boolean findByClaveElector(String claveElector) throws ConvencidosException {

		if (claveElector.length() == 18) {
			List<Convencidos> convencidos = convencidosRepository.findByClaveElector(claveElector);
			if (convencidos == null) {
				return false;
			} else {
				return true;
			}
		} else {
			throw new ConvencidosException("El numero de caracteres ingresado en la clave de elector es incorrecto",
					400);
		}
	}

}