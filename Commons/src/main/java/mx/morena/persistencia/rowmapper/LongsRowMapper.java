package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public class LongsRowMapper implements RowMapper<List<Long>>  {

	@Override
	public List<Long> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<Long> datos = new ArrayList<Long>();
		Long dato = null;
		
		do {
			dato = new Long(0);
			dato = rs.getLong(1);
			datos.add(dato);
		} while (rs.next());

		return datos;
	}

}
