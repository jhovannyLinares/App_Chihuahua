package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Rutas;

public class RutaByFederalRowMapper implements RowMapper<List<Rutas>>{

	@Override
	public List<Rutas> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<Rutas> lstRutas = new ArrayList<Rutas>();
		Rutas rutas = null;
		
		do {
			rutas = new Rutas();
			
			rutas.setIdDistrito(rs.getLong("distrito_federal_id"));
			rutas.setNombreDistrito(rs.getString("nombre_distrito"));
//			rutas.setZonaCrg(rs.getLong("zona_crg"));
//			rutas.setIdZonaCrg(rs.getString("id_zona_crg"));
			rutas.setRuta(rs.getLong("ruta"));
			rutas.setSeccionId(rs.getLong("seccion_id"));
			rutas.setIdRutaRg(rs.getString("id_ruta_rg"));
//			rutas.setZona(rs.getString("zona_crg")+"-"+ rs.getString("id_zona_crg"));
			rutas.setId(rs.getLong("id"));
			lstRutas.add(rutas);
			
		} while (rs.next());
		
		return lstRutas;
	}

}
