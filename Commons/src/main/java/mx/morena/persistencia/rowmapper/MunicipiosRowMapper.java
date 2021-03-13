package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Municipio;

public class MunicipiosRowMapper implements RowMapper<List<Municipio>> {

	@Override
	public List<Municipio> mapRow(ResultSet rs, int rowNum) throws SQLException {

		List<Municipio> municipios = new ArrayList<Municipio>();

		Municipio municipio = null;
		
		do {
			
			municipio = new Municipio();

			municipio.setId(rs.getString("id"));
			municipio.setDescripcion(rs.getString("nombre"));

			municipios.add(municipio);
			
		} while (rs.next());

		return municipios;

	}

}
