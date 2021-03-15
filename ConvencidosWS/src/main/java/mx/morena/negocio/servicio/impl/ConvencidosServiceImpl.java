package mx.morena.negocio.servicio.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ConvencidosDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IConvencidosService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.IConvencidosRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IMunicipioRepository;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;

@Service
public class ConvencidosServiceImpl implements IConvencidosService {

	@Autowired
	private IConvencidosRepository convencidosRepository;

	@Autowired
	private IDistritoFederalRepository dFederalRepository;

//	@Autowired
//	private IEntidadRepository entidadRepository;

	@Autowired
	private IMunicipioRepository municipioRepository;

	@Autowired
	private ISeccionElectoralRepository seccionRepository;

//	@Autowired
//	private IUsuarioRepository usuarioRepository;

	private static final char ESTATUS_ALTA = 'A';
	private static String sinClave = "No Cuenta con ClaveElector";

	@Override
	public List<ConvencidosDTO> getConvencidos(Long distritoFederalId, Long idMunicipio, Long idSeccion,
			String claveElector) throws ConvencidosException {
		List<Convencidos> lstConv = null;
		List<ConvencidosDTO> lstDto = new ArrayList<ConvencidosDTO>();

		if (distritoFederalId != null) {
			DistritoFederal dFederal = dFederalRepository.findById(distritoFederalId);
			lstConv = convencidosRepository.getByDistritoFederal(dFederal.getId());
		}
		if (idMunicipio != null) {
			Municipio municipio = municipioRepository.findById(idMunicipio);
			lstConv = convencidosRepository.getByMunicipio(municipio.getId());
		}

		if (idSeccion != null) {
			List<SeccionElectoral> seccion = seccionRepository.findByCotId(idSeccion);
			if (seccion != null) {
				lstConv = convencidosRepository.getBySeccionesElectoralesIn(seccion);
			}
		}

		if (claveElector != null) {
			lstConv = convencidosRepository.findByClaveElector(claveElector);
		}

		if (!lstDto.isEmpty()) {
			lstDto = MapperUtil.mapAll(lstConv, ConvencidosDTO.class);
			return lstDto;
		} else {
			throw new ConvencidosException("No se encontro ningun usuario con el parametro ingresado", 204);
		}

	}

	@Override
	public Long save(long idUsuario, ConvencidosDTO dto) throws ConvencidosException {

		List<Convencidos> convencidoEx = convencidosRepository.findByClaveElector(dto.getClaveElector());

		if (convencidoEx != null) {
			throw new ConvencidosException("La clave de elector ya se encuentra registrada", 204);
		} else {

			Convencidos convencido = new Convencidos();

			dto.setEstatus(ESTATUS_ALTA);
			dto.setFechaRegistro(new Date());

			MapperUtil.map(dto, convencido);
			if (!dto.getIsClaveElector()) {
				convencido.setClaveElector(sinClave);
			}
			convencido.setEstado(dto.getIdEstado());
			convencido.setDistritoFederal(dto.getIdFederal());
			convencido.setMunicipio(dto.getIdMunicipio());
			convencido.setUsuario(idUsuario);
			convencido.setFechaSistema(new Timestamp(new Date().getTime()));

			convencidosRepository.save(convencido);

			return convencido.getId();

		}

	}

	@Override
	public boolean findByClaveElector(String claveElector) throws ConvencidosException {
		List<Convencidos> convencidos = convencidosRepository.findByClaveElector(claveElector);
		if (convencidos == null) {
			return false;
		} else {
			return true;
		}
	}

}