package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Rutas;

public class RutaRowMapper implements RowMapper<List<Rutas>>{

	@Override
	public List<Rutas> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Rutas> rutas = new ArrayList<Rutas>();
		Rutas ruta = null;
		
		do {
			ruta = new Rutas();
			
			ruta.setId(rs.getLong("ruta_id"));
			ruta.setRuta(rs.getLong("ruta_id"));
			ruta.setIdCrg(rs.getLong("id_crg"));
			ruta.setStatus(rs.getLong("status"));
			
			rutas.add(ruta);
			
		}while (rs.next());
		
		return rutas;
	}

}
