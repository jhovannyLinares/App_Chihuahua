package mx.morena.negocio.servicios.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ReporteAsignacionDistritalDTO;
import mx.morena.negocio.dto.ReporteAsignacionEstatalDTO;
import mx.morena.negocio.exception.RepresentanteException;
import mx.morena.negocio.servicios.IReportesAsignacionService;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IRepresentanteRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ReportesAsignacionImpl extends MasterService implements IReportesAsignacionService{

	@Autowired
	private IDistritoFederalRepository distritoRepository;
	
	@Autowired
	private IRepresentanteRepository representanteRepository;

	@Override
	public List<ReporteAsignacionDistritalDTO> getRepAsignacionDistrital(long perfil) throws RepresentanteException {
		
		List<ReporteAsignacionDistritalDTO> lstDto = new ArrayList<ReporteAsignacionDistritalDTO>();
//		List<DistritoFederal> lstSeccion = null;
		ReporteAsignacionDistritalDTO dto = null;
		
		ReporteAsignacionDistritalDTO total = new ReporteAsignacionDistritalDTO();
//		total.setNombreDistrito("TOTAL: ");
		total.setMetaCrg(0L);
		total.setAvanceCapturadoCrg(0L);
		total.setAvanceAsignadoCrg(0L);
		total.setPorcentajeAvanceCrg(0.0);
		total.setMetaRg(0L);
		total.setAvanceCapturadoRg(0L);
		total.setAvanceAsignadoRg(0L);
		total.setPorcentajeAvanceRg(0.0);
		total.setMetaRc(0L);
		total.setAvanceCapturadoRc(0L);
		total.setAvanceAsignadoRc(0L);
		total.setPorcentajeAvanceRc(0.0);

//		lstSeccion = distritoRepository.findAll();

		dto = new ReporteAsignacionDistritalDTO();
		Long crgCapturado = representanteRepository.getByTipo(PERFIL_CRG);
		Long rgCapturado = representanteRepository.getByTipo(PERFIL_RG);
		Long rcCapturado = representanteRepository.getByTipo(PERFIL_RC);

		Long crgAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_CRG);
		Long rgAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_RG);
		Long rcAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_RC);

//			dto.setNombreDistrito(df.getCabeceraFederal());

		// CRG
		dto.setMetaCrg(50L);
		dto.setAvanceCapturadoCrg(crgCapturado); // 7
		dto.setAvanceAsignadoCrg(crgAsignado);
		double avanceCrg = (crgAsignado * 100.0) / dto.getMetaCrg();
		dto.setPorcentajeAvanceCrg(dosDecimales(avanceCrg).doubleValue());
		// RG
		dto.setMetaRg(50L);
		dto.setAvanceCapturadoRg(rgCapturado); // 8
		dto.setAvanceAsignadoRg(rgAsignado);
		double avanceRg = (rgAsignado * 100.0) / dto.getMetaRg();
		dto.setPorcentajeAvanceRg(dosDecimales(avanceRg).doubleValue());
		// RC
		dto.setMetaRc(50L);
		dto.setAvanceCapturadoRc(rcCapturado); // 9
		dto.setAvanceAsignadoRc(rcAsignado);
		double avanceRc = (rcAsignado * 100.0) / dto.getMetaRc();
		dto.setPorcentajeAvanceRc(dosDecimales(avanceRc).doubleValue());

		// TOTALES
//		total.setMetaCrg(total.getMetaCrg() + dto.getMetaCrg());
//		total.setAvanceCapturadoCrg(total.getAvanceCapturadoCrg() + crgCapturado);
//		total.setAvanceAsignadoCrg(total.getAvanceAsignadoCrg() + crgAsignado);
//		total.setPorcentajeAvanceCrg(0.0);
//
//		total.setMetaRg(total.getMetaRg() + dto.getMetaRg());
//		total.setAvanceCapturadoRg(total.getAvanceCapturadoRg() + rgCapturado);
//		total.setAvanceAsignadoRg(total.getAvanceCapturadoRg() + rgAsignado);
//		total.setPorcentajeAvanceRg(0.0);
//
//		total.setMetaRc(total.getMetaRc() + dto.getMetaRc());
//		total.setAvanceCapturadoRc(total.getAvanceCapturadoRc() + rcCapturado);
//		total.setAvanceAsignadoRc(total.getAvanceAsignadoRc() + rcAsignado);
//		total.setPorcentajeAvanceRc(0.0);
//
		lstDto.add(dto);
//
//		total.setPorcentajeAvanceCrg(
//				dosDecimales((total.getAvanceAsignadoCrg() * 100.0) / total.getMetaCrg()).doubleValue());
//		total.setPorcentajeAvanceRg(
//				dosDecimales((total.getAvanceAsignadoRg() * 100.0) / total.getMetaRg()).doubleValue());
//		total.setPorcentajeAvanceRc(
//				dosDecimales((total.getAvanceAsignadoRc() * 100.0) / total.getMetaRc()).doubleValue());

//		lstDto.add(total);
		return lstDto;
	}

	@Override
	public void getReporteDistritalDownload(HttpServletResponse response, long perfil)
			throws RepresentanteException, IOException {

		// Asignacion de nombre al archivo CSV
		setNameFile(response, CSV_ASIGN_DISTRITAL);

		List<ReporteAsignacionDistritalDTO> asignacionDTOs = getRepAsignacionDistrital(perfil);

		// Nombre y orden de los encabezados en el excel
		String[] header = {"metaCrg", "avanceCapturadoCrg", "avanceAsignadoCrg", "porcentajeAvanceCrg", "metaRg", "avanceCapturadoRg", "avanceAsignadoRg", "porcentajeAvanceRg", "metaRc", "avanceCapturadoRc",
				"avanceAsignadoRc", "porcentajeAvanceRc", "avance" };

		setWriterFile(response, asignacionDTOs, header);

	}

	public BigDecimal dosDecimales(double numero) {

		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;

	}

}
