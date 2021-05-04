package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.ReporteAsignacionRg;

public class ReporteAsignacionCrgRowMapper implements RowMapper<List<ReporteAsignacionRg>> {

	@Override
	public List<ReporteAsignacionRg> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<ReporteAsignacionRg> datos = new ArrayList<ReporteAsignacionRg>();
		ReporteAsignacionRg dato = null;

		do {
			dato = new ReporteAsignacionRg();

			dato.setIdDistrito(rs.getLong("federal_id"));
			dato.setIdLocal(rs.getLong("local_id"));
			dato.setIdRuta(rs.getLong("municpio_id"));
			dato.setMunicipio(rs.getString("nombre"));
			dato.setIdCasilla(rs.getLong("casilla"));

			datos.add(dato);
		} while (rs.next());

		return datos;
	}
	
}
