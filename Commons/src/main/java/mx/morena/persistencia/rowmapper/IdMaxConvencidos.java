package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Convencidos;

public class IdMaxConvencidos implements RowMapper<Long>{

	
	public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Convencidos convencido = new Convencidos();
		
		convencido.setId(rs.getLong(1));
		
		return rs.getLong(1);
	}

}
