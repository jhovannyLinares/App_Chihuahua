package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Convencidos;

public class ConvencidosValRowMapper implements RowMapper<List<Convencidos>> {

	@Override
	public List<Convencidos> mapRow(ResultSet rs, int rowNum) throws SQLException {

		List<Convencidos> convencidos = new ArrayList<Convencidos>();
		Convencidos convencido = null;

		do {
			convencido = new Convencidos();

			convencido.setId(rs.getLong("id"));
			
			
			convencidos.add(convencido);
		} while (rs.next());


		return convencidos;
	}

}
