package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Partido;

public class PartidosRowMapper implements RowMapper<List<Partido>> {

	@Override
	public List<Partido> mapRow(ResultSet rs, int rowNum) throws SQLException {

		List<Partido> partidos = new ArrayList<Partido>();

		Partido partido = null;
		do {
			partido = new Partido();
			partido.setId(rs.getLong("id"));
			partido.setPartido(rs.getString("partido"));
			partido.setTipoPartido(rs.getString("tipo_partido"));
			partido.setCandidato(rs.getString("candidato"));

			partidos.add(partido);

		} while (rs.next());

		return partidos;
	}

}
