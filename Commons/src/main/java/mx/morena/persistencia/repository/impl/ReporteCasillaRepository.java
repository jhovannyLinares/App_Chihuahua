package mx.morena.persistencia.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.ReporteCasilla;
import mx.morena.persistencia.repository.IReporteCasillas;

@Repository
public class ReporteCasillaRepository implements IReporteCasillas {
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public int save(ReporteCasilla rc) {
		String sql = "insert into app_reporte_casillas (id, id_casilla, hora_reporte, id_rg, numero_votos)" 
				+ "values ((SELECT MAX(id)+1 FROM app_reporte_casillas), ?, ?, ?, ?);";
		try {
			template.update(sql, new Object[] {rc.getIdCasilla(), rc.getHoraReporte(), rc.getIdRg(), rc.getNumeroVotos()});
			return 0;
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}

}
