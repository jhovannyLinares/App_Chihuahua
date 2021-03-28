package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Rutas;

public class RutasTempRowMapper implements RowMapper<Rutas> {

	@Override
	public Rutas mapRow(ResultSet rs, int rowNum) throws SQLException {
		Rutas rutas = new Rutas();
			
			rutas.setIdDistrito(rs.getLong("distrito_federal_id"));
			rutas.setNombreDistrito(rs.getString("nombre_distrito"));
			rutas.setZonaCrg(rs.getLong("zona_crg"));
			rutas.setIdZonaCrg(rs.getString("id_zona_crg"));
			rutas.setRuta(rs.getLong("ruta"));
			rutas.setSeccionId(rs.getLong("seccion_id"));
			rutas.setIdRutaRg(rs.getString("id_ruta_rg"));
			rutas.setZona(rs.getString("zona_crg")+"-"+ rs.getString("id_zona_crg"));
	
		
		return rutas;
	}

}
