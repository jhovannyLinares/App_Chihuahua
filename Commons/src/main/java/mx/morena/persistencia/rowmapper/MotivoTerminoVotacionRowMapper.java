package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.MotivosTerminoVotacion;

public class MotivoTerminoVotacionRowMapper implements RowMapper<MotivosTerminoVotacion> {

	@Override
	public MotivosTerminoVotacion mapRow(ResultSet rs, int rowNum) throws SQLException {
		MotivosTerminoVotacion motivo = new MotivosTerminoVotacion();

		motivo.setId(rs.getLong("id"));
		motivo.setMotivo(rs.getString("motivo"));

		return motivo;
	}

}
