package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Entidad;

public class EntidadRowMapper implements RowMapper<Entidad> {

	@Override
	public Entidad mapRow(ResultSet rs, int rowNum) throws SQLException {

		Entidad entidad = new Entidad();

		entidad.setId(rs.getString("id"));
		entidad.setNombre(rs.getString("nombre"));

		return entidad;
	}

}
