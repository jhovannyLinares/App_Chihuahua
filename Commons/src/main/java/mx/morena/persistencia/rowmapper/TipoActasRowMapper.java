package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.TipoActas;

public class TipoActasRowMapper implements RowMapper<List<TipoActas>>{

	@Override
	public List<TipoActas> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<TipoActas> actas = new ArrayList<TipoActas>();

		TipoActas acta = null;
		do {
			acta = new TipoActas();

			acta.setId(rs.getLong("id"));
			acta.setDescripcion(rs.getString("descripcion"));

			actas.add(acta);

		} while (rs.next());

		return actas;
	}

}
