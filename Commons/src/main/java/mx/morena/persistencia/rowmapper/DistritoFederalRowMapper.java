package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.DistritoFederal;

public class DistritoFederalRowMapper implements RowMapper<DistritoFederal> {

	@Override
	public DistritoFederal mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DistritoFederal distritoFederal = new DistritoFederal();

		distritoFederal.setId(rs.getLong("id"));
		distritoFederal.setCabeceraFederal(rs.getLong("id")   +" - "+rs.getString("NOMBRE MUNICIPIO CABECERA")); 

		return distritoFederal;
	}

}
