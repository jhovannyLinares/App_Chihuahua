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
import mx.morena.negocio.dto.ReporteCrgDTO;
import mx.morena.negocio.dto.ReporteAsignacionEstatalDTO;
import mx.morena.negocio.exception.RepresentanteException;
import mx.morena.negocio.dto.ReporteRCDTO;
import mx.morena.negocio.servicios.IReportesAsignacionService;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IRepresentanteRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ReportesAsignacionImpl extends MasterService implements IReportesAsignacionService{
	
	@Autowired
	private IRepresentanteRepository representanteRepository;

	@Autowired
	private IDistritoFederalRepository distritoRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	

	@Override
	public List<ReporteAsignacionDistritalDTO> getRepAsignacionDistrital(long perfil) throws RepresentanteException {
		
		List<ReporteAsignacionDistritalDTO> lstDto = new ArrayList<ReporteAsignacionDistritalDTO>();
		ReporteAsignacionDistritalDTO dto = null;
		ReporteAsignacionDistritalDTO total = new ReporteAsignacionDistritalDTO();
		
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

		dto = new ReporteAsignacionDistritalDTO();
		
		Long crgCapturado = representanteRepository.getByTipo(PERFIL_CRG);
		Long rgCapturado = representanteRepository.getByTipo(PERFIL_RG);
		Long rcCapturado = representanteRepository.getByTipo(PERFIL_RC);

		Long crgAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_CRG);
		Long rgAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_RG);
		Long rcAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_RC);

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

		lstDto.add(dto);

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

	@Override
	public List<ReporteRCDTO> getReporteRc(Long perfil) throws RepresentanteException {
if(perfil == PERFIL_ESTATAL) {
			
			List<ReporteRCDTO> lstRc = new ArrayList<ReporteRCDTO>();
			ReporteRCDTO dto = null;
			
			Long countRcCapturados = representanteRepository.getRcCaptura(PERFIL_RC);
			Long countRcAsigndos = representanteRepository.getRcAsignados(PERFIL_RC);
			
				dto = new ReporteRCDTO();
				
				dto.setMetaRc(45L);;
				dto.setAvanceCapturadoRc(countRcCapturados);
				dto.setAvanceAsignadoRc(countRcAsigndos);
				dto.setAvance(100L);
				double sub1 = (dto.getAvanceAsignadoRc() * 100.00)/ dto.getMetaRc();
				dto.setPorcentajeAvanceRc(dosDecimales(sub1).doubleValue());
				
				lstRc.add(dto);

			return lstRc;
		}else {
			throw new RepresentanteException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	@Override
	public void getReporteRcDownload(HttpServletResponse response, Long perfil)
			throws RepresentanteException, IOException {
		if(perfil == PERFIL_ESTATAL ) {
			// Asignacion de nombre al archivo CSV
			setNameFile(response, CSV_ASIGN_RC);

			List<ReporteRCDTO> rcDTOs = getReporteRc(perfil);

			//Nombre y orden de los encabezados en el excel
			String[] header = { "metaRc", "avanceCapturadoRc", "avanceAsignadoRc", "avance", "porcentajeAvanceRc"};

			setWriterFile(response, rcDTOs, header);
			
		} else {
			throw new RepresentanteException("No cuenta con permisos suficientes para descargar el reporte", 401);
		}
		
	}

	@Override
	public List<ReporteCrgDTO> getReporteCrgDv(Long perfil) throws RepresentanteException {
		List<ReporteCrgDTO> lstDto = new ArrayList<ReporteCrgDTO>();
		ReporteCrgDTO dto = new ReporteCrgDTO();

		Long countRepRgCapturado = representanteRepository.getByTipo(PERFIL_RG);
		Long countRepRgAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_RG);
		Long countRepRcCapturado = representanteRepository.getByTipo(PERFIL_RC);
		Long countRepRcAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_RC);

		dto.setMetaRg(63L);
		dto.setAvanceCapturadoRg(countRepRgCapturado);
		dto.setAvanceAsignadoRg(countRepRgAsignado);
		double porAvanceRg = (double) dto.getAvanceAsignadoRg() / dto.getMetaRg() * 100.00;
		dto.setPorcentajeAvanceRg(dosDecimales(porAvanceRg).doubleValue());

		dto.setMetaRc(30L);
		dto.setAvanceCapturadoRc(countRepRcCapturado);
		dto.setAvanceAsignadoRc(countRepRcAsignado);
		double porAvanceRc = (double) dto.getAvanceAsignadoRc() / dto.getMetaRc() * 100.00;
		dto.setPorcentajeAvanceRc(dosDecimales(porAvanceRc).doubleValue());
		dto.setAvance(0L);

		lstDto.add(dto);

		return lstDto;
	}

	@Override
	public void getReporteCrgDownload(HttpServletResponse response, long perfil) throws RepresentanteException, IOException {
		setNameFile(response, CSV_ASIGN_CRG);

		List<ReporteCrgDTO> crgDTOs = getReporteCrgDv(perfil);

		String[] header = { "metaRg", "avanceCapturadoRg", "avanceAsignadoRg", "porcentajeAvanceRg", "metaRc",
							"avanceCapturadoRc", "avanceAsignadoRc", "avance", "porcentajeAvanceRc" };

		setWriterFile(response, crgDTOs, header);
	}

	@Override
	public List<ReporteAsignacionEstatalDTO> getReporteAsignacionEstatal(long idUsuario)
			throws RepresentanteException, IOException {

		List<ReporteAsignacionEstatalDTO> lstDto = new ArrayList<ReporteAsignacionEstatalDTO>();
		List<DistritoFederal> lstSeccion = null;
		ReporteAsignacionEstatalDTO dto = null;
		ReporteAsignacionEstatalDTO total = new ReporteAsignacionEstatalDTO();

		total.setNumero("TOTAL: ");
		total.setIdDistrito(null);
		total.setMetaRFederal(0L);
		total.setAvanceCapturadoRFederal(0L);
		total.setAvanceAsignadoRfederal(0L);
		total.setPorcentajeAvanceRFederal(0.0);
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
		
		Usuario usuario = usuarioRepository.findById(idUsuario);

		lstSeccion = distritoRepository.findByEntidad(usuario.getEntidad());

		for (DistritoFederal df : lstSeccion) {
			dto = new ReporteAsignacionEstatalDTO();
			Long fedCapturado = representanteRepository.getByDistritoAndTipo(df.getId(), PERFIL_FEDERAL);
			Long crgCapturado = representanteRepository.getByDistritoAndTipo(df.getId(), PERFIL_CRG);
			Long rgCapturado = representanteRepository.getByDistritoAndTipo(df.getId(), PERFIL_RG);
			Long rcCapturado = representanteRepository.getByDistritoAndTipo(df.getId(), PERFIL_RC);

			Long fedAsignado = representanteRepository.getRepAsignadoByDistrito(df.getId(), PERFIL_FEDERAL);
			Long crgAsignado = representanteRepository.getRepAsignadoByDistrito(df.getId(), PERFIL_CRG);
			Long rgAsignado = representanteRepository.getRepAsignadoByDistrito(df.getId(), PERFIL_RG);
			Long rcAsignado = representanteRepository.getRepAsignadoByDistrito(df.getId(), PERFIL_RC);

			dto.setNumero(df.getId().toString());
			dto.setIdDistrito(df.getId());

			// FEDERAL
			dto.setMetaRFederal(50L);
			dto.setAvanceCapturadoRFederal(fedCapturado);
			dto.setAvanceAsignadoRfederal(fedAsignado);
			double avanceFederal = (fedAsignado * 100.0) / dto.getMetaRFederal();
			dto.setPorcentajeAvanceRFederal(dosDecimales(avanceFederal).doubleValue());
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

			lstDto.add(dto);

			// TOTALES
			total.setMetaRFederal(total.getMetaRFederal() + dto.getMetaRFederal());
			total.setAvanceCapturadoRFederal(total.getAvanceCapturadoRFederal() + fedCapturado);
			total.setAvanceAsignadoRfederal(total.getAvanceAsignadoRfederal() + fedAsignado);

			total.setMetaCrg(total.getMetaCrg() + dto.getMetaCrg());
			total.setAvanceCapturadoCrg(total.getAvanceCapturadoCrg() + crgCapturado);
			total.setAvanceAsignadoCrg(total.getAvanceAsignadoCrg() + crgAsignado);

			total.setMetaRg(total.getMetaRg() + dto.getMetaRg());
			total.setAvanceCapturadoRg(total.getAvanceCapturadoRg() + rgCapturado);
			total.setAvanceAsignadoRg(total.getAvanceCapturadoRg() + rgAsignado);

			total.setMetaRc(total.getMetaRc() + dto.getMetaRc());
			total.setAvanceCapturadoRc(total.getAvanceCapturadoRc() + rcCapturado);
			total.setAvanceAsignadoRc(total.getAvanceAsignadoRc() + rcAsignado);

		}
		total.setPorcentajeAvanceRFederal(
				dosDecimales((total.getAvanceAsignadoRfederal() * 100.0) / total.getMetaRFederal()).doubleValue());
		total.setPorcentajeAvanceCrg(
				dosDecimales((total.getAvanceAsignadoCrg() * 100.0) / total.getMetaCrg()).doubleValue());
		total.setPorcentajeAvanceRg(
				dosDecimales((total.getAvanceAsignadoRg() * 100.0) / total.getMetaRg()).doubleValue());
		total.setPorcentajeAvanceRc(
				dosDecimales((total.getAvanceAsignadoRc() * 100.0) / total.getMetaRc()).doubleValue());

		lstDto.add(total);

		return lstDto;
	}

	@Override
	public void getReporteEstatalDownload(HttpServletResponse response, long perfil)
			throws RepresentanteException, IOException {
		// Asignacion de nombre al archivo CSV
		setNameFile(response, CSV_ASIGN_ESTATAL);

		List<ReporteAsignacionEstatalDTO> asignacionDTOs = getReporteAsignacionEstatal(perfil);

		// Nombre y orden de los encabezados en el excel
		String[] header = {"numero", "idDistrito", "metaRFederal", "avanceCapturadoRFederal", "avanceAsignadoRfederal", "porcentajeAvanceRFederal", 
				"metaCrg", "avanceCapturadoCrg", "avanceAsignadoCrg", "porcentajeAvanceCrg", 
				"metaRg", "avanceCapturadoRg", "avanceAsignadoRg", "porcentajeAvanceRg",
				"metaRc", "avanceCapturadoRc", "avanceAsignadoRc", "porcentajeAvanceRc", "avance" };

		setWriterFile(response, asignacionDTOs, header);
	}

}
