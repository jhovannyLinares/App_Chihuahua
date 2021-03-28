package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Rutas;

public class RutaByZonaRowMapper implements RowMapper<List<Rutas>> {

	@Override
	public List<Rutas> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Rutas> lstRutas = new ArrayList<Rutas>();
		Rutas rutas = null;
		do {
			rutas = new Rutas();
			
			rutas.setRuta(rs.getLong("id"));
			rutas.setIdRutaRg(rs.getString("id_ruta_rg"));
			rutas.setIdZonaCrg(rs.getString("id_zona"));
			
			lstRutas.add(rutas);
			
		} while (rs.next());
		
		return lstRutas;
	}

}
