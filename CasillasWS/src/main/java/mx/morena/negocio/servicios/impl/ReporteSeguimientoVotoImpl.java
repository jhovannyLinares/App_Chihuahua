package mx.morena.negocio.servicios.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ReporteSeguimintoVotoDTO;
import mx.morena.negocio.dto.SeguimientoVotoDTO;
import mx.morena.negocio.exception.SeguimientoVotoException;
import mx.morena.negocio.servicios.IReporteSeguimientoVotoService;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;
import mx.morena.persistencia.repository.ISeguimientoVotoRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ReporteSeguimientoVotoImpl extends MasterService implements IReporteSeguimientoVotoService {
	
	@Autowired
	private ISeguimientoVotoRepository seguimientoRepository;
	
	@Autowired
	private ISeccionElectoralRepository seccionRepository;
	
	@Autowired
	private ICasillaRepository casillasRepository;

	@Override
	public List<ReporteSeguimintoVotoDTO> getSeguimeitoVoto(Long perfil, Long usuario) throws SeguimientoVotoException{
		if(perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_LOCAL) {
			
			List<ReporteSeguimintoVotoDTO> lstvoto = new ArrayList<ReporteSeguimintoVotoDTO>();
			List <SeccionElectoral> lstSeccion = null;
			ReporteSeguimintoVotoDTO dto = null;
			ReporteSeguimintoVotoDTO totales = new ReporteSeguimintoVotoDTO();
			totales.setIdDistrito(0L);
			totales.setDistrito("Totales");
			totales.setSecciones(0L);
			totales.setUrbanas(0L);
			totales.setNoUrbanas(0L);
			totales.setMetaConvencidos(0L);
			totales.setTotalConvencidos(0L);
			totales.setPorcentajeAvanceConvencidos(0.00);
			totales.setNotificado(0L);
			totales.setPorcentajeAvanceNotificado(0.00);
			
			lstSeccion = seccionRepository.getDistritos();
			
			for(SeccionElectoral seccion : lstSeccion) {
				Long countSecciones = seccionRepository.getSecciones(seccion.getDistritoId());
				Long urbanas = casillasRepository.countByDistritoAndTipologia(seccion.getDistritoId(), URBANAS);
				Long noUrbanas = casillasRepository.countByDistritoAndTipologia(seccion.getDistritoId(), NO_URBANAS);
				Long convencidos = seguimientoRepository.countByLocalAndTipo(seccion.getDistritoId(), CONVENCIDO);
//				Long notificados = seguimientoRepository.countByNotificados(seccion.getDistritoId(), CONVENCIDO);
				dto = new ReporteSeguimintoVotoDTO();
				
				dto.setIdDistrito(seccion.getDistritoId());
				dto.setDistrito(seccion.getDistritoId() + "-" +seccion.getDistritoId());
				dto.setSecciones(countSecciones);
				dto.setUrbanas(urbanas);
				dto.setNoUrbanas(noUrbanas);
				dto.setMetaConvencidos(45L);
				dto.setTotalConvencidos(convencidos);
				double sub2 = (convencidos * 100.00)/ dto.getMetaConvencidos();
				dto.setPorcentajeAvanceConvencidos(dosDecimales(sub2).doubleValue());
				dto.setNotificado(50L);
				dto.setPorcentajeAvanceNotificado(dosDecimales((dto.getNotificado() * 100) / dto.getTotalConvencidos()).doubleValue());
				
				
				totales.setSecciones(totales.getSecciones() + countSecciones);
				totales.setUrbanas(totales.getUrbanas() + urbanas);
				totales.setNoUrbanas(totales.getNoUrbanas() + noUrbanas);
				totales.setMetaConvencidos(totales.getMetaConvencidos() + dto.getMetaConvencidos());
				totales.setTotalConvencidos(totales.getTotalConvencidos() + convencidos);
				totales.setNotificado(totales.getNotificado() + dto.getNotificado());
				
				lstvoto.add(dto);
			}
			
			totales.setPorcentajeAvanceConvencidos(dosDecimales((totales.getTotalConvencidos() * 100.00)/totales.getMetaConvencidos()).doubleValue());
			totales.setPorcentajeAvanceNotificado(dosDecimales((totales.getTotalConvencidos() * 100.00)/totales.getMetaConvencidos()).doubleValue());
			
			lstvoto.add(totales);
			
			return lstvoto;
		}else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}
	
	public BigDecimal dosDecimales(double numero) {
		
		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;
		
	}

	@Override
	public List<SeguimientoVotoDTO> getCasillaBySeccion(long idPerfil, Long idSeccion) throws SeguimientoVotoException {
		// TODO Auto-generated method stub
		return null;
	}
}
