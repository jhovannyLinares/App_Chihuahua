package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Casilla;

public class CasillasLocalesByFederalRowMapper implements RowMapper<List<Casilla>> {

	@Override
	public List<Casilla> mapRow(ResultSet rs, int rowNum) throws SQLException {

		List<Casilla> casillas = new ArrayList<Casilla>();
		Casilla casilla = null;

		do {

			casilla = new Casilla();

			casilla.setFederal(rs.getLong("federal_id"));
			casilla.setLocal(rs.getLong("local_id"));

			casillas.add(casilla);

		} while (rs.next());

		return casillas;

	}

}
