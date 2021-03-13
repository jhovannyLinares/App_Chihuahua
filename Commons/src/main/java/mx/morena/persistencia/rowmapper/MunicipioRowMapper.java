package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Municipio;

public class MunicipioRowMapper implements RowMapper<Municipio> {

	@Override
	public Municipio mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Municipio municipio = new Municipio();

		municipio.setId(rs.getString("id"));
		municipio.setDescripcion(rs.getString("nombre"));

		return municipio;
	}

}
