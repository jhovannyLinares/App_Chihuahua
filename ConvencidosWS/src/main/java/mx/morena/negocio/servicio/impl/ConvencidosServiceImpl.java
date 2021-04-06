package mx.morena.negocio.servicio.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
	public List<ReporteDistritalDTO> getReporteDistrital(Long perfil) throws ConvencidosException {

		if (perfil == PERFIL_ESTATAL) {

			List<ReporteDistritalDTO> lstDistrito = new ArrayList<ReporteDistritalDTO>();
			List<SeccionElectoral> lstSeccion = null;
			ReporteDistritalDTO dto = null;
			ReporteDistritalDTO totales = new ReporteDistritalDTO();
			totales.setDistritoId(0L);
			totales.setNombreDistrito("Totales");
			totales.setSecciones(0l);
			totales.setUrbanas(0L);
			totales.setNoUrbanas(0L);
			totales.setMetaCots(0l);
			totales.setCots(0l);
			totales.setConvencidos(0L);
			totales.setMetaConvencidos(0L);

			lstSeccion = seccionRepository.getDistritos();

			for (SeccionElectoral seccion : lstSeccion) {
				Long countSecciones = seccionRepository.getSecciones(seccion.getDistritoId());
				Long cots = convencidosRepository.countByDistritoAndTipo(seccion.getDistritoId(), COT, ESTATUS_ALTA);
				Long urbanas = casillasRepository.countByDistritoAndTipologia(seccion.getDistritoId(), URBANAS);
				Long noUrbanas = casillasRepository.countByDistritoAndTipologia(seccion.getDistritoId(), NO_URBANAS);
				Long convencidos = convencidosRepository.countByDistAndTipo(seccion.getDistritoId(), CONVENCIDO);
				dto = new ReporteDistritalDTO();

				dto.setDistritoId(seccion.getDistritoId());
				dto.setNombreDistrito(seccion.getDistritoId() + "-" + seccion.getNombreDistrito());
				dto.setSecciones(countSecciones);
				dto.setUrbanas(urbanas);
				dto.setNoUrbanas(noUrbanas);
				dto.setMetaCots(83L);
				dto.setCots(cots);
				
				double avanceCots = (cots*100.0)/dto.getMetaCots();
     			dto.setAvanceCots(dosDecimales(avanceCots).doubleValue());
     			
				dto.setConvencidos(convencidos);
				dto.setMetaConvencidos(83L);
				
				double avanceConvencidos = (convencidos*100.0)/dto.getMetaConvencidos();
				dto.setAvanceConvencidos(dosDecimales(avanceConvencidos).doubleValue());
				
				totales.setSecciones(totales.getSecciones()+countSecciones );
				totales.setUrbanas(totales.getUrbanas()+dto.getUrbanas());
				totales.setNoUrbanas(totales.getNoUrbanas()+dto.getNoUrbanas());
				totales.setMetaCots(totales.getMetaCots()+dto.getMetaCots() );
				totales.setCots(totales.getCots()+dto.getCots() );	
				totales.setMetaConvencidos(totales.getMetaConvencidos()+dto.getMetaConvencidos());
				totales.setConvencidos(totales.getConvencidos()+dto.getConvencidos());				

				lstDistrito.add(dto);
			}

			totales.setAvanceCots(dosDecimales((totales.getCots()*100.0)/ totales.getMetaCots()).doubleValue());
			totales.setAvanceConvencidos(dosDecimales((totales.getConvencidos()*100.0)/totales.getMetaConvencidos()).doubleValue());
			
			lstDistrito.add(totales);
			
			return lstDistrito;
		} else {
			throw new ConvencidosException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

	}

	@Override
	public List<ReporteMunicipalDTO> getReporteMunicipal() throws ConvencidosException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public BigDecimal dosDecimales(double numero) {

		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;

	}


}