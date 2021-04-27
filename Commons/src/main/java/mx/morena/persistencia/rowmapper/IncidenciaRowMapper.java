package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Incidencias;

public class IncidenciaRowMapper implements RowMapper<List<Incidencias>>  {

	@Override
	public List<Incidencias> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Incidencias> incidencias = new ArrayList<Incidencias>();

		Incidencias incidencia = null;
		do {
			incidencia = new Incidencias();

			incidencia.setIdIncidencia(rs.getLong("idIncidencia"));
			incidencia.setIncidencia(rs.getString("incidencia"));

			incidencias.add(incidencia);

		} while (rs.next());

		return incidencias;
	
	}

}
