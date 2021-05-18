package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.ReporteCasilla;

public class ReporteCasillaResponseRowMapper implements RowMapper<List<ReporteCasilla>> {

	@Override
	public List<ReporteCasilla> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<ReporteCasilla> reporteCasillas = new ArrayList<ReporteCasilla>();

		ReporteCasilla reporteCasilla = null;
		do {
			
			reporteCasilla = new ReporteCasilla();

			reporteCasilla.setId(rs.getLong("id"));
			reporteCasilla.setIdCasilla(rs.getLong("id_casilla"));
			reporteCasilla.setHoraReporte(rs.getTimestamp("hora_reporte"));
			reporteCasilla.setHoraCierre(rs.getTimestamp("hora_cierre"));
			reporteCasilla.setTipoReporte(rs.getInt("tipo_reporte"));
			reporteCasilla.setPersonasHanVotado(rs.getLong("cantidad_personas_han_votado"));
			reporteCasilla.setBoletasUtilizadas(rs.getLong("boletas_utilizadas"));
			reporteCasilla.setRecibioVisitaRg(rs.getBoolean("recibio_visita_representante"));
//			reporteCasilla.setRc(rs.getBoolean("is_rc"));
			reporteCasilla.setIdRc(rs.getLong("id_rc"));
			reporteCasilla.setIdMotivoCierre(rs.getLong("id_motivo_cierre"));
			reporteCasilla.setCerrada(rs.getBoolean("is_cerrada"));
			
			reporteCasillas.add(reporteCasilla);

		} while (rs.next());

		return reporteCasillas;
	}

}
