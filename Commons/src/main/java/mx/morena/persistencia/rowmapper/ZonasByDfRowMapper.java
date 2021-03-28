package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Zona;

public class ZonasByDfRowMapper implements RowMapper<List<Zona>> {

	@Override
	public List<Zona> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<Zona> lstRutas = new ArrayList<Zona>();
		Zona zona = null;
		
		do {
			zona = new Zona();
			
			zona.setIdDistrito(rs.getLong("distrito_federal_id"));
			zona.setNombreDistrito(rs.getString("nombre_distrito"));
			zona.setZonaCrg(rs.getLong("zona_crg"));
			zona.setIdZonaCrg(rs.getString("id_zona_crg"));
			zona.setId(rs.getLong("id"));
			
			lstRutas.add(zona);
			
		} while (rs.next());
		
		return lstRutas;
	}

}
