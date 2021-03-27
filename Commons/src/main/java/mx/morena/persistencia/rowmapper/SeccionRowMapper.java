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
		seccion.setDescripcion(rs.getString("seccion_id"));
		seccion.setMunicipioId(rs.getLong("municipio_id"));
		seccion.setMunicipio(rs.getString("municipio"));

		return seccion;
	}

}
