package mx.morena.presentacion.controlador;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.morena.negocio.dto.ReporteCotDTO;
import mx.morena.negocio.dto.ResidentDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.ICotService;
import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "reportes/")
@CrossOrigin
public class ReportesController extends MasterController {

	@Autowired
	private ICotService cotService;

//	@Autowired
//	private CsvService csvService;

	@GetMapping("/cots")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	private List<ReporteCotDTO> getReporte(HttpServletResponse response) throws IOException {
		try {
			return cotService.getReporte();
		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	@GetMapping("/cots/download")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public void downloadCSV(HttpServletResponse response) throws IOException {

		String csvFileName = "ReporteCots.csv";

		response.setContentType("text/csv");

		// creates mock data
		String headerKey = "Content-Disposition";

		String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);

		response.setHeader(headerKey, headerValue);

		try {

			List<ReporteCotDTO> cotDTOs = cotService.getReporte();

			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			String[] header = { "distritoId", "nombreDistrito","secciones", "cubiertas","porcentajeCobertura", "metaCots","cots", "porcetajeAvance" };

			csvWriter.writeHeader(header);

			for (ReporteCotDTO aBook : cotDTOs) {
				csvWriter.write(aBook, header);
			}

			csvWriter.close();

		} catch (CotException e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
