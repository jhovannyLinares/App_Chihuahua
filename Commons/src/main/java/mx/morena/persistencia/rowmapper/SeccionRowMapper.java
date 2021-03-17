package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.SeccionElectoral;

public class SeccionRowMapper implements RowMapper<SeccionElectoral> {

	@Override
	public SeccionElectoral mapRow(ResultSet rs, int rowNum) throws SQLException {

		SeccionElectoral seccion = new SeccionElectoral();

		seccion.setId(rs.getLong("seccion_id"));
		seccion.setDescripcion(rs.getString("tipo"));
		seccion.setCot(rs.getLong("idcot"));
		seccion.setLocalidad(rs.getLong("id"));

		return seccion;
	}

}
