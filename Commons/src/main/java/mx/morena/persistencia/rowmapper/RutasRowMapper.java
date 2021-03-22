package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Rutas;

public class RutasRowMapper implements RowMapper<Rutas>{

	@Override
	public Rutas mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
