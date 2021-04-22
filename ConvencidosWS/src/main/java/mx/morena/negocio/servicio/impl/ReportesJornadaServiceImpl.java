package mx.morena.negocio.servicio.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ReporteCapacitacionEstatalDTO;
import mx.morena.negocio.exception.JornadaException;
import mx.morena.negocio.servicio.IReportesJornadaService;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.ICapacitacionRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ReportesJornadaServiceImpl extends MasterService implements IReportesJornadaService {
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private IDistritoFederalRepository distritoRepository;
	
	@Autowired
	private ICapacitacionRepository capacitacionRepository;
	
	@Override
	public List<ReporteCapacitacionEstatalDTO> getReporteCapEstatal(Long idUsuario) throws JornadaException {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
		Long idEstado = usuario.getEntidad();
		
		if (perfil == PERFIL_ESTATAL) {
			List<ReporteCapacitacionEstatalDTO> reporteDto = new ArrayList<ReporteCapacitacionEstatalDTO>();
			ReporteCapacitacionEstatalDTO dto = null;
			ReporteCapacitacionEstatalDTO totales = new ReporteCapacitacionEstatalDTO();

			totales.setNumero(null);
			totales.setDistritoFederal("Total:");
			totales.setMetaRG(0L);
			totales.setAvanceCapacitacionRG(0L);
			totales.setPorcentajeCapacitacionRG(0.0);
			totales.setAvanceEntregaNombramientoRG(0L);
			totales.setPorcentajeAvanceEntregaRG(0.0);
			totales.setMetaRC(0L);
			totales.setAvanceCapacitacionRC(0L);
			totales.setPorcentajeCapacitacionRC(0.0);
			totales.setAvanceEntregaNombramientoRC(0L);
			totales.setPorcentajeAvanceEntregaRC(0.0);

			List<DistritoFederal> distritos = distritoRepository.findByEntidad(idEstado);

			for (DistritoFederal df : distritos) {
				dto = new ReporteCapacitacionEstatalDTO();
				Long capacitacionRg = capacitacionRepository.getCapacitacionByDfAndRepresentante(idEstado, df.getId(), PERFIL_RG, SI_TOMO_CAPACITACION);
				Long nombramientoRg = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, df.getId(), PERFIL_RG, SI_TOMO_CAPACITACION, true);
				Long capacitacionRc = capacitacionRepository.getCapacitacionByDfAndRepresentante(idEstado, df.getId(), PERFIL_RC, SI_TOMO_CAPACITACION);
				Long nombramientoRc = capacitacionRepository.getNombramientoByDfAndRepresentante(idEstado, df.getId(), PERFIL_RC, SI_TOMO_CAPACITACION, true);

				dto.setNumero(df.getId());
				dto.setDistritoFederal(df.getCabeceraFederal());
				dto.setMetaRG(30L);
				dto.setAvanceCapacitacionRG(capacitacionRg);
				dto.setPorcentajeCapacitacionRG(null);
				dto.setAvanceEntregaNombramientoRG(nombramientoRg);
				dto.setPorcentajeAvanceEntregaRG(null);
				dto.setMetaRC(40L);
				dto.setAvanceCapacitacionRC(capacitacionRc);
				dto.setPorcentajeCapacitacionRG(null);
				dto.setAvanceEntregaNombramientoRC(nombramientoRc);
				dto.setPorcentajeAvanceEntregaRC(null);
				//double avanceFederal = (fedAsignado * 100.0) / dto.getMetaRFederal();
				//dto.setPorcentajeAvanceRFederal(dosDecimales(avanceFederal).doubleValue());*/
				reporteDto.add(dto);
			}
			
			return reporteDto;
		} else {
			throw new JornadaException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}
	
	public BigDecimal dosDecimales(double numero) {

		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;

	}
	
}
