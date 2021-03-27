package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Zona;

public class ZonasRowMapper implements RowMapper<List<Zona>> {

	@Override
	public List<Zona> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Zona> zonas = new ArrayList<Zona>();
		Zona zona = null;

		do {
			zona = new Zona();

			zona.setId(rs.getLong("id"));
			zona.setDescripcion(rs.getString("zona_crg")+"-"+ rs.getString("id_zona_crg"));

			zonas.add(zona);
			
		} while (rs.next());


		return zonas;
	}
	
	

}
