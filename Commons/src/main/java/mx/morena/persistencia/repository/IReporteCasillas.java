package mx.morena.persistencia.repository;

import mx.morena.persistencia.entidad.ReporteCasilla;

public interface IReporteCasillas {

	int save(ReporteCasilla rc);

	int updateHoraCierre(ReporteCasilla rc);

	Long getReporteByCAsilla(Long idCasilla);

}
