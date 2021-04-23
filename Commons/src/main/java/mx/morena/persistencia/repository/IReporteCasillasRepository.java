package mx.morena.persistencia.repository;

import mx.morena.persistencia.entidad.ReporteCasilla;

public interface IReporteCasillasRepository {

	int save(ReporteCasilla rc);

	int updateHoraCierre(ReporteCasilla rc);

	Long getReporteByCAsilla(Long idCasilla);

}
