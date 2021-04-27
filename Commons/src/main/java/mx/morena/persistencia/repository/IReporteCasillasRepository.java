package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.ReporteCasilla;

public interface IReporteCasillasRepository {

	int save(ReporteCasilla rc);

	int updateHoraCierre(ReporteCasilla rc);

	List<ReporteCasilla> getReporteByIdCasilla(Long idCasilla);

	Long getCountByDistritoAndTipoVotacion(Long idFederal, Long idReporte, String once);

}
