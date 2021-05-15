package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import mx.morena.persistencia.entidad.RepresentacionPartidos;

public class RepresentacionPartidoRowMapper implements RowMapper<List<RepresentacionPartidos>> {

	@Override
	public List<RepresentacionPartidos> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<RepresentacionPartidos> partidos = new ArrayList<RepresentacionPartidos>();

		RepresentacionPartidos partido = null;
		
		do {
			
			partido = new RepresentacionPartidos();

			partido.setId(rs.getLong("id"));
			partido.setPartido(rs.getString("partido"));

			partidos.add(partido);
			
		} while (rs.next());

		return partidos;
	}

}
