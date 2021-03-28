package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Zona;

public class ZonaRowMapper implements RowMapper<Zona> {

	@Override
	public Zona mapRow(ResultSet rs, int rowNum) throws SQLException {
		Zona zona = null;

		zona = new Zona();

		zona.setIdDistrito(rs.getLong("distrito_federal_id"));
		zona.setNombreDistrito(rs.getString("nombre_distrito"));
		zona.setZonaCrg(rs.getLong("zona_crg"));
		zona.setIdZonaCrg(rs.getString("id_zona_crg"));
		zona.setId(rs.getLong("id"));

		return zona;
	}

}
