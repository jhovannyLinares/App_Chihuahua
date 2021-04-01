package mx.morena.negocio.servicio.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ConvencidosDTO;
import mx.morena.negocio.dto.ConvencidosResponseDTO;
import mx.morena.negocio.dto.ReporteDistritalDTO;
import mx.morena.negocio.dto.ReporteMunicipalDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IConvencidosService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IConvencidosRepository;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ConvencidosServiceImpl extends MasterService implements IConvencidosService {

	@Autowired
	private IConvencidosRepository convencidosRepository;
	
	@Autowired
	private ISeccionElectoralRepository seccionRepository;
	
	@Autowired
	private ICasillaRepository casillasRepository;

	private static final char ESTATUS_ALTA = 'A';
	private static String sinClave = "No cuenta con Clave Elector";
	private static String sinCalle = "No se cuenta con calle";

	@Override
	public List<ConvencidosResponseDTO> getConvencidos(Long distritoFederalId, Long idMunicipio, Long idSeccion,
			String claveElector) throws ConvencidosException {

		List<Convencidos> lstConv = null;
		List<ConvencidosResponseDTO> lstDto = new ArrayList<ConvencidosResponseDTO>();

			lstConv = convencidosRepository.getConvencidos(distritoFederalId,idMunicipio, idSeccion, claveElector, CONVENCIDO);

		if (lstConv != null) {
			System.out.println("**** " + lstConv.size());
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
			List<Convencidos> convencidoEx = convencidosRepository.findByClaveElectorVal(dto.getClaveElector());

			if (convencidoEx != null) {
				throw new ConvencidosException("La clave de elector ya se encuentra registrada", 400);
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
			List<Convencidos> convencidos = convencidosRepository.findByClaveElectorVal(claveElector);
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

	@Override
	public List<ReporteDistritalDTO> getReporteDistrital(Long usuario) throws ConvencidosException {
		
		if(usuario == PERFIL_ESTATAL) {
			
			List<ReporteDistritalDTO> lstDistrito = new ArrayList<ReporteDistritalDTO>();
			List <SeccionElectoral> lstSeccion = null;
			ReporteDistritalDTO dto = null;
			
			lstSeccion = seccionRepository.getDistritos();
			
			for(SeccionElectoral seccion : lstSeccion) {
				Long countSecciones = seccionRepository.getSecciones(seccion.getDistritoId());
				Long cots = convencidosRepository.countByDistAndTipo(seccion.getDistritoId(), COT);
				Long urbanas = casillasRepository.countByDistritoAndTipologia(seccion.getDistritoId(), URBANAS);
				Long noUrbanas = casillasRepository.countByDistritoAndTipologia(seccion.getDistritoId(), NO_URBANAS);
				Long convencidos = convencidosRepository.countByDistAndTipo(seccion.getDistritoId(), CONVENCIDO);
				dto = new ReporteDistritalDTO();
				
				dto.setIdDistrito(seccion.getDistritoId());
				dto.setDistrito(seccion.getDistritoId() + "-" +seccion.getNombreDistrito());
				dto.setSecciones(countSecciones);
				dto.setUrbanas(urbanas);
				dto.setNoUrbanas(noUrbanas);
				dto.setMetaCots(80L);
				dto.setCots(cots);
//				dto.setAvanceConvencidos(avanceConvencidos);
				dto.setConvencidos(convencidos);
//				dto.setMetaConvencidos(metaConvencidos);
//				dto.setAvanceConvencidos(avanceConvencidos);
				
				lstDistrito.add(dto);
			}
			
			return lstDistrito;
		}else {
			throw new ConvencidosException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
		
	}

	@Override
	public List<ReporteMunicipalDTO> getReporteMunicipal() throws ConvencidosException {
		// TODO Auto-generated method stub
		return null;
	}

}