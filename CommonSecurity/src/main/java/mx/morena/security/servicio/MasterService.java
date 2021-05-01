package mx.morena.security.servicio;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class MasterService {
	
	protected final Logger logger = Logger.getLogger(this.getClass());

	protected static final Long PERFIL_ESTATAL = 1L;
	protected static final Long PERFIL_FEDERAL = 2L;
	protected static final Long PERFIL_LOCAL = 3L;
	protected static final Long PERFIL_MUNICIPAL = 4L;
	protected static final Long PERFIL_COTS = 5L;
	protected static final Long PERFIL_SECCION_ELECTORAL = 6L;
	protected static final Long PERFIL_CRG = 7L;
	protected static final Long PERFIL_RG = 8L;
	protected static final Long PERFIL_RC = 9L;
	protected static final Long PERFIL_BRIGADISTA = 10L;
	
	protected static final Long CONVENCIDO = 1L;
	protected static final Long COT = 2L;
	protected static final Long CRG = 7L;
	
	protected static final char ESTATUS_ALTA = 'A';
	protected static final char ESTATUS_SUSPENDIDO = 'S';
	
	protected static final int REP_FEDERAL = 2;
	protected static final int REP_LOCAL = 3;
	protected static final int REP_MUNICIPAL = 4;
	protected static final int REP_CRG = 7;
	protected static final int REP_RG = 8;
	protected static final int REP_RC = 9;
	
	protected static final String SIN_CALLE = "No se cuenta con Calle";
	
	protected static final String RUTA_INE = "/INE/";
	
	protected static final String URBANAS = "Urbana";
	protected static final String NO_URBANAS = "No urbana";
	
	protected static final String SI_TOMO_CAPACITACION = "Si";
	
	protected static final String CSV_COTS = "ReporteCots.csv";
	protected static final String CSV_CONV_DIST = "ReporteConvencidosDistrital.csv";
	protected static final String CSV_CONV_LOCAL = "ReporteConvencidosLocal.csv";
	protected static final String CSV_CONV_MUNICIPAL = "ReporteConvencidosMunicipal.csv";
	protected static final String CSV_CAPACITACION_CRG = "ReporteCapacitacionCRG.csv";
	protected static final String CSV_REPORTE_RESULTADOS = "ReporteResultados.csv";
	protected static final String CSV_REPORTE_MUNICIPAL = "ReporteMunicipal.csv";
	
	
	protected static final String CSV_CAP_DISTRITAL = "ReporteCapacitacionNombramientoDistrial.csv";
	
	//Reportes asignacion
	protected static final String CSV_ASIGN_ESTATAL =  "ReporteAsignacionEstatal.csv";
	protected static final String CSV_ASIGN_DISTRITAL =  "ReporteAsignacionDistrital.csv";
	protected static final String CSV_ASIGN_RC = "ReporteAsignacionRc.csv";
	protected static final String CSV_ASIGN_CRG = "ReporteAsignacionCrg.csv";
	protected static final String CSV_RG = "ReporteRg.csv";
	protected static final String CSV_ASIGN_RG = "ReporteAsignacionRg.csv";
	
	protected static final String CSV_CAPACITACION_NOMB_ESTATAL = "ReporteCapacitacionNombramientoEstatal.csv";
	
	protected static final String CSV_SEGUIMIENTOVOTO = "ReporteSeguimientoVoto.csv";
	
	//Reportes Instalacion Casilla
	protected static final String CSV_INST_ESTATAL =  "ReporteInstalacionCasillaEstatal.csv";
	protected static final String CSV_INST_FEDERAL =  "ReporteInstalacionCasillaFederal.csv";
	protected static final String CSV_INST_LOCAL =  "ReporteInstalacionCasillaLocal.csv";
	protected static final String CSV_INST_MUNICIPAL =  "ReporteInstalacionCasillaMunicipal.csv";
	protected static final String CSV_INST_CRG = "ReporteInstalacionCasillaCrg.csv";
	protected static final String CSV_INST_RG = "ReporteInstalacionCasillaRg.csv";
	
	protected static final String CSV_CAPACITACION_RG = "ReporteCapacitacionRg.csv";
	protected static final String CSV_REPORTE_VOTACION = "ReporteVotacion.csv";
	protected static final String CSV_ASISTENCIA_ESTATAL = "ReporteAsisenciaEstatal.csv";
	protected static final String CSV_ASISTENCIA_DISTRITAL = "ReporteAsisenciaDistrital.csv";
	protected static final String CSV_ASISTENCIA_LOCAL = "ReporteAsisenciaLocal.csv";
	protected static final String CSV_ASISTENCIA_MUNICIPAL = "ReporteAsisenciaMunicipal.csv";
	
	protected static final String CSV_REPORTE_RESULTADOS_ACTAS = "ReporteResultadosActas.csv";
	
	private static final String HEADER_KEY = "Content-Disposition";
	private static final String CONTENT_TYPE = "text/csv";
	private static final String ATTACHMENT = "attachment; filename=\"%s\"";

	protected void setNameFile(HttpServletResponse response, String nameReport) {

		String headerValue = String.format(ATTACHMENT, nameReport);
		response.setHeader(HEADER_KEY, headerValue);
		response.setContentType(CONTENT_TYPE);

	}
	
	
	protected void setWriterFile(HttpServletResponse response, List<?> objects,String[] header) throws IOException {

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

		csvWriter.writeHeader(header);

		for (Object object : objects) {
			csvWriter.write(object, header);
		}

		csvWriter.close();

	}
	
}
