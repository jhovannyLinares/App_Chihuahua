package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Casilla;

public class UbicacionCasillaRowMapper implements RowMapper<Casilla>{

	@Override
	public Casilla mapRow(ResultSet rs, int rowNum) throws SQLException {

		Casilla casilla = new Casilla();
		
		casilla.setCalle(rs.getString("calle"));
		casilla.setNumero(rs.getString("numero"));
		casilla.setColonia(rs.getString("colonia"));
		casilla.setUbicacion(rs.getString("ubicacion"));
		
		return casilla;
	}

}
