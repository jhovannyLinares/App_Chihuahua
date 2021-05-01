package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Rutas;

public class RutaSeguimietoRowMapper implements RowMapper<List<Rutas>>{

	@Override
	public List<Rutas> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Rutas> rutas = new ArrayList<Rutas>();
		Rutas ruta = null;
		
		do {
			ruta = new Rutas();
			
			ruta.setRuta(rs.getLong("ruta"));
			
			rutas.add(ruta);
			
		}while (rs.next());
		
		return rutas;
	}

}
