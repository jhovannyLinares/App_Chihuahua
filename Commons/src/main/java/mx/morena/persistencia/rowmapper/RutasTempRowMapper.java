package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Rutas;

public class RutasTempRowMapper implements RowMapper<List<Rutas>> {

	@Override
	public List<Rutas> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Rutas> rutas = new ArrayList<Rutas>();
		
		Rutas ruta = null;
		
		do {
		
			ruta = new Rutas();
			
			ruta.setIdDistrito(rs.getLong("distrito_federal_id"));
			ruta.setNombreDistrito(rs.getString("nombre_distrito"));
			ruta.setZonaCrg(rs.getLong("zona_crg"));
			ruta.setIdZonaCrg(rs.getString("id_zona_crg"));
			ruta.setRuta(rs.getLong("ruta"));
			ruta.setSeccionId(rs.getLong("seccion_id"));
			ruta.setIdRutaRg(rs.getString("id_ruta_rg"));
			ruta.setZona(rs.getString("zona_crg")+"-"+ rs.getString("id_zona_crg"));
			
			rutas.add(ruta);
			
		}while(rs.next());
			
		return rutas;
	}

}
