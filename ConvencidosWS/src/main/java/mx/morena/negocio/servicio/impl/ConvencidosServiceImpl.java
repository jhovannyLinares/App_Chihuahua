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

import mx.morena.negocio.dto.ConvencidosDTO;
import mx.morena.negocio.dto.ConvencidosResponseDTO;
import mx.morena.negocio.dto.ReporteDistritalDTO;
import mx.morena.negocio.dto.ReporteLocalDTO;
import mx.morena.negocio.dto.ReporteMunicipalDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IConvencidosService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IConvencidosRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IMunicipioRepository;
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
	
	@Autowired
	private IMunicipioRepository municipioRepository;
	
	@Autowired
	private IDistritoFederalRepository federalRepository;

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
			logger.debug("**** " + lstConv.size());
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
				throw new ConvencidosException("La clave de elector ya se encuentra registrada. Favor de validar", 400);
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
	public List<ReporteDistritalDTO> getReporteDistrital(Long perfil, Long idFederal) throws ConvencidosException {

		if (perfil == PERFIL_ESTATAL) {

			List<ReporteDistritalDTO> lstDistrito = new ArrayList<ReporteDistritalDTO>();
			List<SeccionElectoral> lstSeccion = null;
			ReporteDistritalDTO dto = null;
			ReporteDistritalDTO totales = new ReporteDistritalDTO();
			totales.setIdDistrito(0L);
			totales.setDistrito("Totales");
			totales.setSecciones(0l);
			totales.setUrbanas(0L);
			totales.setNoUrbanas(0L);
			totales.setMetaCots(0l);
			totales.setCots(0l);
			totales.setTotalConvencidos(0L);
			totales.setMetaConvencidos(0L);
			
			if (idFederal == null) {
				
				lstSeccion = seccionRepository.getDistritos();

				for (SeccionElectoral seccion : lstSeccion) {
					Long countSecciones = seccionRepository.getSecciones(seccion.getDistritoId());
					Long cots = convencidosRepository.countByDistritoAndTipo(seccion.getDistritoId(), COT,
							ESTATUS_ALTA);
					Long urbanas = casillasRepository.countByDistritoAndTipologia(seccion.getDistritoId(), URBANAS);
					Long noUrbanas = casillasRepository.countByDistritoAndTipologia(seccion.getDistritoId(),
							NO_URBANAS);
					Long convencidos = convencidosRepository.countByDistAndTipo(seccion.getDistritoId(), CONVENCIDO);
					dto = new ReporteDistritalDTO();

					dto.setIdDistrito(seccion.getDistritoId());
					dto.setDistrito(seccion.getDistritoId() + "-" + seccion.getNombreDistrito());
					dto.setSecciones(countSecciones);
					dto.setUrbanas(urbanas);
					dto.setNoUrbanas(noUrbanas);
					dto.setMetaCots(83L);
					dto.setCots(cots);

					double avanceCots = (cots * 100.0) / dto.getMetaCots();
					dto.setPorcentajeAvanceCots(dosDecimales(avanceCots).doubleValue());

					dto.setTotalConvencidos(convencidos);
					dto.setMetaConvencidos(83L);

					double avanceConvencidos = (convencidos * 100.0) / dto.getMetaConvencidos();
					dto.setPorcentajeAvanceConvencidos(dosDecimales(avanceConvencidos).doubleValue());

					totales.setSecciones(totales.getSecciones() + countSecciones);
					totales.setUrbanas(totales.getUrbanas() + dto.getUrbanas());
					totales.setNoUrbanas(totales.getNoUrbanas() + dto.getNoUrbanas());
					totales.setMetaCots(totales.getMetaCots() + dto.getMetaCots());
					totales.setCots(totales.getCots() + dto.getCots());
					totales.setMetaConvencidos(totales.getMetaConvencidos() + dto.getMetaConvencidos());
					totales.setTotalConvencidos(totales.getTotalConvencidos() + dto.getTotalConvencidos());

					lstDistrito.add(dto);
				}
				
				totales.setPorcentajeAvanceCots(
						dosDecimales((totales.getCots() * 100.0) / totales.getMetaCots()).doubleValue());
				totales.setPorcentajeAvanceConvencidos(
						dosDecimales((totales.getTotalConvencidos() * 100.0) / totales.getMetaConvencidos()).doubleValue());

				lstDistrito.add(totales);
				
			}else {
				
				Long countSecciones = seccionRepository.getSecciones(idFederal);
				Long cots = convencidosRepository.countByDistritoAndTipo(idFederal, COT,
						ESTATUS_ALTA);
				Long urbanas = casillasRepository.countByDistritoAndTipologia(idFederal, URBANAS);
				Long noUrbanas = casillasRepository.countByDistritoAndTipologia(idFederal,
						NO_URBANAS);
				Long convencidos = convencidosRepository.countByDistAndTipo(idFederal, CONVENCIDO);
				dto = new ReporteDistritalDTO();
				
				DistritoFederal distrito = federalRepository.findById(idFederal);

				dto.setIdDistrito(idFederal);
				dto.setDistrito(distrito.getCabeceraFederal());
				dto.setSecciones(countSecciones);
				dto.setUrbanas(urbanas);
				dto.setNoUrbanas(noUrbanas);
				dto.setMetaCots(83L);
				dto.setCots(cots);

				double avanceCots = (cots * 100.0) / dto.getMetaCots();
				dto.setPorcentajeAvanceCots(dosDecimales(avanceCots).doubleValue());

				dto.setTotalConvencidos(convencidos);
				dto.setMetaConvencidos(83L);

				double avanceConvencidos = (convencidos * 100.0) / dto.getMetaConvencidos();
				dto.setPorcentajeAvanceConvencidos(dosDecimales(avanceConvencidos).doubleValue());

//				totales.setSecciones(totales.getSecciones() + countSecciones);
//				totales.setUrbanas(totales.getUrbanas() + dto.getUrbanas());
//				totales.setNoUrbanas(totales.getNoUrbanas() + dto.getNoUrbanas());
//				totales.setMetaCots(totales.getMetaCots() + dto.getMetaCots());
//				totales.setCots(totales.getCots() + dto.getCots());
//				totales.setMetaConvencidos(totales.getMetaConvencidos() + dto.getMetaConvencidos());
//				totales.setTotalConvencidos(totales.getTotalConvencidos() + dto.getTotalConvencidos());

				lstDistrito.add(dto);
				
			}

			return lstDistrito;
		} else {
			throw new ConvencidosException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

	}

	@Override
	public List<ReporteMunicipalDTO> getReporteMunicipal(long perfil) throws ConvencidosException {
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_LOCAL || perfil == PERFIL_MUNICIPAL) {
			List<ReporteMunicipalDTO> lstDto = new ArrayList<ReporteMunicipalDTO>();
			
			List <Municipio> municipios = municipioRepository.getAll();
			
			ReporteMunicipalDTO reporteDto = null;
			ReporteMunicipalDTO totales = new ReporteMunicipalDTO();
			
			totales.setIdMunicipio(null);
			totales.setMunicipio("Totales");
			totales.setUrbanas(0L);
			totales.setNoUrbanas(0L);
			totales.setMetaCots(0L);
			totales.setCots(0L);
			totales.setPorcentajeAvanceCots(0.00);
			totales.setMetaConvencidos(0L);
			totales.setTotalConvencidos(0L);
			totales.setPorcentajeAvanceConvencidos(0.00);
			totales.setSecciones(0L);
			
			for (Municipio municipio : municipios) {
				logger.debug(municipio.getId() + " " + municipio.getDescripcion());
				
				Long cots = convencidosRepository.countByMunicipioAndTipo(municipio.getId(), COT, ESTATUS_ALTA);
				Long urbanas = casillasRepository.countByMunicipioAndTipologia(municipio.getId(), URBANAS);
				Long noUrbanas = casillasRepository.countByMunicipioAndTipologia(municipio.getId(), NO_URBANAS);
				Long convencidos = convencidosRepository.countByMunicipioAndTipo(municipio.getId(), CONVENCIDO, ESTATUS_ALTA);
				Long countSecciones = seccionRepository.getSeccionesByMunicipio(municipio.getId());
				
				reporteDto = new ReporteMunicipalDTO();
				
				reporteDto.setIdMunicipio(municipio.getId());
				reporteDto.setMunicipio(municipio.getDescripcion());
				reporteDto.setUrbanas(urbanas);
				reporteDto.setNoUrbanas(noUrbanas);
				reporteDto.setMetaCots(54L);
				reporteDto.setCots(cots);
				reporteDto.setPorcentajeAvanceCots(null);
				reporteDto.setMetaConvencidos(90L);
				reporteDto.setTotalConvencidos(convencidos);
				reporteDto.setPorcentajeAvanceConvencidos(null);
				reporteDto.setSecciones(countSecciones);
				
				double porcAvanceCots = (cots * 100.0)/reporteDto.getMetaCots();
				reporteDto.setPorcentajeAvanceCots(dosDecimales(porcAvanceCots).doubleValue());
				
				double porcAvanceConvencidos = (convencidos * 100.0)/reporteDto.getMetaConvencidos();
				reporteDto.setPorcentajeAvanceConvencidos(dosDecimales(porcAvanceConvencidos).doubleValue());
				
				totales.setUrbanas(totales.getUrbanas() + reporteDto.getUrbanas());
				totales.setNoUrbanas(totales.getNoUrbanas() + reporteDto.getNoUrbanas());
				totales.setMetaCots(totales.getMetaCots() + reporteDto.getMetaCots() );
				totales.setCots(totales.getCots() + reporteDto.getCots() );
				totales.setMetaConvencidos(totales.getMetaConvencidos() + reporteDto.getMetaConvencidos());
				totales.setTotalConvencidos(totales.getTotalConvencidos() + reporteDto.getTotalConvencidos());
				totales.setSecciones(totales.getSecciones() + reporteDto.getSecciones());
				
				lstDto.add(reporteDto);
			}
			totales.setPorcentajeAvanceCots(dosDecimales((totales.getCots() * 100.0) / totales.getMetaCots()).doubleValue());
			totales.setPorcentajeAvanceConvencidos(dosDecimales((totales.getTotalConvencidos() * 100.0) / totales.getMetaConvencidos()).doubleValue());
			
			lstDto.add(totales);
			
			return lstDto;
		} else {
			throw new ConvencidosException("No cuenta con permisos suficientes", 401);
		}
	}

	@Override
	public List<ReporteLocalDTO> getReporteLocal(Long perfil) throws ConvencidosException {
		if(perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_LOCAL) {
			
			List<ReporteLocalDTO> lstLocal = new ArrayList<ReporteLocalDTO>();
			List <SeccionElectoral> lstSeccion = null;
			ReporteLocalDTO dto = null;
			ReporteLocalDTO totales = new ReporteLocalDTO();
			totales.setIdDistrito(0L);
			totales.setDistrito("Totales");
			totales.setSecciones(0L);
			totales.setUrbanas(0L);
			totales.setNoUrbanas(0L);
			totales.setMetaCots(0L);
			totales.setCots(0L);
			totales.setPorcentajeAvanceCots(0.00);
			totales.setMetaConvencidos(0L);
			totales.setTotalConvencidos(0L);
			totales.setPorcentajeAvanceConvencidos(0.00);
			
			lstSeccion = seccionRepository.getLocal();
			
			for(SeccionElectoral seccion : lstSeccion) {
				Long countSecciones = seccionRepository.getSeccionesLocal(seccion.getLocalId());
				Long cots = convencidosRepository.countByLocalAndTipo(seccion.getLocalId(), COT);
				Long urbanas = casillasRepository.countByLocalAndTipologia(seccion.getLocalId(), URBANAS);
				Long noUrbanas = casillasRepository.countByLocalAndTipologia(seccion.getLocalId(), NO_URBANAS);
				Long convencidos = convencidosRepository.countByLocalAndTipo(seccion.getLocalId(), CONVENCIDO);
				dto = new ReporteLocalDTO();
				
				dto.setIdDistrito(seccion.getLocalId());
				dto.setDistrito(seccion.getLocalId() + "-" +seccion.getLocal());
				dto.setSecciones(countSecciones);
				dto.setUrbanas(urbanas);
				dto.setNoUrbanas(noUrbanas);
				dto.setMetaCots(55L);
				dto.setCots(cots);
				double sub1 = (cots * 100.00)/ dto.getMetaCots();
				dto.setPorcentajeAvanceCots(dosDecimales(sub1).doubleValue());
				dto.setMetaConvencidos(45L);
				dto.setTotalConvencidos(convencidos);
				double sub2 = (convencidos * 100.00)/ dto.getMetaConvencidos();
				dto.setPorcentajeAvanceConvencidos(dosDecimales(sub2).doubleValue());
				
				totales.setSecciones(totales.getSecciones() + countSecciones);
				totales.setUrbanas(totales.getUrbanas() + urbanas);
				totales.setNoUrbanas(totales.getNoUrbanas() + noUrbanas);
				totales.setMetaCots(totales.getMetaCots() + dto.getMetaCots());
				totales.setCots(totales.getCots() + cots);
				totales.setMetaConvencidos(totales.getMetaConvencidos() + dto.getMetaConvencidos());
				totales.setTotalConvencidos(totales.getTotalConvencidos() + convencidos);
				
				lstLocal.add(dto);
			}
			
			totales.setPorcentajeAvanceCots(dosDecimales((totales.getCots() * 100.00)/ totales.getMetaCots()).doubleValue());
			totales.setPorcentajeAvanceConvencidos(dosDecimales((totales.getTotalConvencidos() * 100.00)/totales.getMetaConvencidos()).doubleValue());
			
			lstLocal.add(totales);
			
			return lstLocal;
		}else {
			throw new ConvencidosException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}
	
	public BigDecimal dosDecimales(double numero) {
		
		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;
		
	}

	@Override
	public void getReporteDownload(HttpServletResponse response, long perfil, Long idFederal) throws ConvencidosException, IOException {

		// Asignacion de nombre al archivo CSV
		setNameFile(response, CSV_CONV_DIST);

		List<ReporteDistritalDTO> convDTOs = getReporteDistrital(perfil, idFederal);

		//Nombre y orden de los encabezados en el excel
		String[] header = { "idDistrito", "Distrito", "secciones", "urbanas", "noUrbanas", "metaCots",
				"cots", "porcentajeAvanceCots", "metaConvencidos", "totalConvencidos", "porcentajeAvanceConvencidos" };

		setWriterFile(response, convDTOs, header);

	}
	
	@Override
	public void getReporteLocalDownload(HttpServletResponse response, Long perfil)
			throws ConvencidosException, IOException {
		if(perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_LOCAL) {
			// Asignacion de nombre al archivo CSV
			setNameFile(response, CSV_CONV_LOCAL);

			List<ReporteLocalDTO> localDTOs = getReporteLocal(perfil);

			//Nombre y orden de los encabezados en el excel
			String[] header = { "idDistrito", "distrito", "secciones", "urbanas", "noUrbanas", "metaCots",
					"cots", "porcentajeAvanceCots", "metaConvencidos", "totalConvencidos", "porcentajeAvanceConvencidos" };

			setWriterFile(response, localDTOs, header);
			
		} else {
			throw new ConvencidosException("No cuenta con permisos suficientes para descargar el reporte", 401);
		}
	}

	@Override
	public void getReporteConvMunicipalDownload(HttpServletResponse response, Long perfil) throws ConvencidosException, IOException {
		// Asignacion de nombre al archivo CSV
		setNameFile(response, CSV_CONV_MUNICIPAL);
		
		List<ReporteMunicipalDTO> convMunicipalDTOs = getReporteMunicipal(perfil);

		//Nombre y orden de los encabezados en el excel
		String[] header = { "idMunicipio", "municipio","secciones", "urbanas","noUrbanas", "metaCots","cots", "porcentajeAvanceCots",
							"metaConvencidos", "totalConvencidos", "porcentajeAvanceConvencidos" };

		setWriterFile(response, convMunicipalDTOs, header);
	}
}