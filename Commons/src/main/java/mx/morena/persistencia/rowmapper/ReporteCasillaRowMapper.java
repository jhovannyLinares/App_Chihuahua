package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.ReporteCasilla;

public class ReporteCasillaRowMapper implements RowMapper<List<ReporteCasilla>> {

	@Override
	public List<ReporteCasilla> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<ReporteCasilla> reporteCasillas = new ArrayList<ReporteCasilla>();

		ReporteCasilla reporteCasilla = null;
		do {
			
			reporteCasilla = new ReporteCasilla();

			reporteCasilla.setHoraCierre(rs.getTimestamp("hora_cierre"));
			reporteCasilla.setHoraReporte(rs.getTimestamp("hora_reporte"));
			reporteCasilla.setId(rs.getLong("id"));
			reporteCasilla.setIdCasilla(rs.getLong("id_casilla"));
			reporteCasilla.setIdRg(rs.getLong("id_rg"));
			reporteCasilla.setNumeroVotos(rs.getLong("numero_votos"));
			reporteCasilla.setTipoReporte(rs.getLong("tipo_reporte"));
//			reporteCasilla.setTipoReporte(rs.getLong("tipo_votacion"));

			reporteCasillas.add(reporteCasilla);

		} while (rs.next());

		return reporteCasillas;
	}

}
