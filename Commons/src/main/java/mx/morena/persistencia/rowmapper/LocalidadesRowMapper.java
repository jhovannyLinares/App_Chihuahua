package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Localidad;

public class LocalidadesRowMapper implements RowMapper<List<Localidad>> {

	@Override
	public List<Localidad> mapRow(ResultSet rs, int rowNum) throws SQLException {

		List<Localidad> localidades = new ArrayList<Localidad>();

		Localidad localidad = null;
		do {

			localidad = new Localidad();

			localidad.setId(rs.getLong("id"));
			localidad.setDescripcion(rs.getLong("seccion_id") +" - "+ rs.getString("nombre") +" - "+ rs.getString("tipo"));
			localidad.setTipo(rs.getString("tipo"));

			localidades.add(localidad);

		} while (rs.next());

		return localidades;
	}

}
