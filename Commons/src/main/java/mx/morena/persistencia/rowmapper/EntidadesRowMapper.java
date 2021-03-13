package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Entidad;

public class EntidadesRowMapper implements RowMapper<List<Entidad>> {

	@Override
	public List<Entidad> mapRow(ResultSet rs, int rowNum) throws SQLException {

		List<Entidad> entidades = new ArrayList<Entidad>();

		Entidad entidad = null;
		do {
			entidad = new Entidad();

			entidad.setId(rs.getString("id"));
			entidad.setNombre(rs.getString("nombre"));

			entidades.add(entidad);

		} while (rs.next());

		return entidades;
	}

}
