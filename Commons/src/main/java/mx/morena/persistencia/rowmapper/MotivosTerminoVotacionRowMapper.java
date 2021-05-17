package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.MotivosTerminoVotacion;

public class MotivosTerminoVotacionRowMapper implements RowMapper<List<MotivosTerminoVotacion>> {

	@Override
	public List<MotivosTerminoVotacion> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<MotivosTerminoVotacion> motivos = new ArrayList<MotivosTerminoVotacion>();
		MotivosTerminoVotacion motivo = null;
		
		do {
			motivo = new MotivosTerminoVotacion();

			motivo.setId(rs.getLong("id"));
			motivo.setMotivo(rs.getString("motivo"));
			motivos.add(motivo);
			
		} while (rs.next());

		return motivos;
	}

}
