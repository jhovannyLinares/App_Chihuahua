package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Perfil;

public class PerfilRowMapper implements RowMapper<Perfil> {

	@Override
	public Perfil mapRow(ResultSet rs, int rowNum) throws SQLException {
		Perfil perfil = new Perfil();

		perfil.setId(rs.getLong("id"));
		perfil.setNombre(rs.getString("nombre"));

		return perfil;
	}

}
