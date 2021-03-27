package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.DistritoFederal;

public class DistritosFederalesRowMapper implements RowMapper<List<DistritoFederal>> {

	@Override
	public List<DistritoFederal> mapRow(ResultSet rs, int rowNum) throws SQLException {

		List<DistritoFederal> distritoFederals = new ArrayList<DistritoFederal>();

		DistritoFederal distritoFederal = null;
		do {

			distritoFederal = new DistritoFederal();

			distritoFederal.setId(rs.getLong("id"));
			distritoFederal.setCabeceraFederal(rs.getLong("id")   +" - "+rs.getString("cabecera")); 

			distritoFederals.add(distritoFederal);

		} while (rs.next());

		return distritoFederals;

	}

}
