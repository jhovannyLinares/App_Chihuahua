package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Eleccion;

public class EleccionRowMapper implements RowMapper<List<Eleccion>>{

	@Override
	public List<Eleccion> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<Eleccion> elecciones = new ArrayList<Eleccion>();

		Eleccion eleccion = null;
		do {
			eleccion = new Eleccion();

			eleccion.setId(rs.getLong("id"));
			eleccion.setDescripcion(rs.getString("descripcion"));

			elecciones.add(eleccion);

		} while (rs.next());

		return elecciones;
	}

}
