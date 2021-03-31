package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CountSeccionesRowMapper implements RowMapper<Long> {

	@Override
	public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return rs.getLong(1);
	}

}
