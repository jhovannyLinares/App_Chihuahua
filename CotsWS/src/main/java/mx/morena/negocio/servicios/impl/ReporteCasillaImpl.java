package mx.morena.negocio.servicios.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ReporteVotacionDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.IReporteCasillaService;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IReporteCasillasRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ReporteCasillaImpl extends MasterService implements IReporteCasillaService {
	
	@Autowired
	private IReporteCasillasRepository reporteCasillaRepository ;
		
	@Autowired
	private IDistritoFederalRepository distritoFederalRepository;
	
	private String once = "11:00:00";
	
	private String quince = "15:00:00";
	
	private String dieciocho = "18:00:00";

	@Override
	public List<ReporteVotacionDTO> getReporteVotacion(Long usuario, Long perfil, Long idReporte)
			throws CotException, IOException {

		if (perfil == PERFIL_RG) {

			List<ReporteVotacionDTO> lstDto = new ArrayList<>();
			List<DistritoFederal> lstSeccion = null;
			ReporteVotacionDTO dto = null;
			ReporteVotacionDTO total = new ReporteVotacionDTO();
			

			lstSeccion = distritoFederalRepository.findAll();
			System.out.println("**** " + lstSeccion.size());
			
			total.setIdFederal(null);
			total.setListaNominal(0L);
			total.setVotacion11hrs(0L);
			total.setPorcentajeVotacion11hrs(0.0);
			total.setVotacion15hrs(0L);
			total.setPorcentajeVotacion15hrs(0.0);
			total.setVotacion18hrs(0L);
			total.setPorcentajeVotacion18hrs(0.0);

			for (DistritoFederal se : lstSeccion) {
				dto = new ReporteVotacionDTO();
				
				Long votos11 = reporteCasillaRepository.getCountByDistritoAndTipoVotacion(se.getId(), idReporte, once);
				Long votos15 = reporteCasillaRepository.getCountByDistritoAndTipoVotacion(se.getId(), idReporte, quince);
				Long votos18 = reporteCasillaRepository.getCountByDistritoAndTipoVotacion(se.getId(), idReporte, dieciocho);

				dto.setIdFederal(se.getId());
				dto.setListaNominal(20L);

				dto.setVotacion11hrs(votos11);
				double porcentaje11 = dosDecimales((dto.getVotacion11hrs() * 100.0)/dto.getListaNominal()).doubleValue();
				dto.setPorcentajeVotacion11hrs(porcentaje11);

				dto.setVotacion15hrs(votos15);
				double porcentaje15 = dosDecimales((dto.getVotacion15hrs() * 100.0)/dto.getListaNominal()).doubleValue();
				dto.setPorcentajeVotacion15hrs(porcentaje15);

				dto.setVotacion18hrs(votos18);
				double porcentaje18 = dosDecimales((dto.getVotacion18hrs() * 100.0)/dto.getListaNominal()).doubleValue();
				dto.setPorcentajeVotacion18hrs(porcentaje18);
				
				lstDto.add(dto);
				
				total.setListaNominal(total.getListaNominal() + dto.getListaNominal());
				total.setVotacion11hrs(total.getVotacion11hrs() + dto.getVotacion11hrs());
				total.setVotacion15hrs(total.getVotacion15hrs() + dto.getVotacion15hrs());
				total.setVotacion18hrs(total.getVotacion18hrs() + dto.getVotacion18hrs());

			}
			
			total.setPorcentajeVotacion11hrs(dosDecimales((total.getVotacion11hrs() * 100.0)/total.getListaNominal()).doubleValue());
			total.setPorcentajeVotacion15hrs(dosDecimales((total.getVotacion15hrs() * 100.0)/total.getListaNominal()).doubleValue());
			total.setPorcentajeVotacion18hrs(dosDecimales((total.getVotacion18hrs() * 100.0)/total.getListaNominal()).doubleValue());
			
			lstDto.add(total);
			
			return lstDto;
		} else {
			throw new CotException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}

	}
	
	public BigDecimal dosDecimales(double numero) {

		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;

	}

	@Override
	public void getReporteVotacionDownload(HttpServletResponse response, Long usuario, long perfil, Long idReporte) throws CotException, IOException {
		setNameFile(response, CSV_REPORTE_VOTACION);
		
				List<ReporteVotacionDTO> reporteDTOs = getReporteVotacion(usuario, perfil, idReporte);
		
				String[] header = {"idFederal", "ListaNominal", "votacion11hrs", "porcentajeVotacion11hrs", "votacion15hrs", "porcentajeVotacion15hrs",
						"votacion18hrs", "porcentajeVotacion18hrs"};
		
				setWriterFile(response, reporteDTOs, header);
		
	}

}
