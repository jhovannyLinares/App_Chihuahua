package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Rutas;

public class RutasRowMapper implements RowMapper<List<Rutas>>{

	@Override
	public List<Rutas> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Rutas> lstRutas = new ArrayList<Rutas>();
		Rutas rutas = null;
		
		do {
			rutas = new Rutas();
			
			rutas.setId(rs.getLong("id"));
			rutas.setIdDistrito(rs.getLong("distrito_federal_id"));
			rutas.setNombreDistrito(rs.getString("nombre_distrito"));
			rutas.setZonaCrg(rs.getLong("zona_crg"));
			rutas.setIdZonaCrg(rs.getString("id_zona_crg"));
			rutas.setRuta(rs.getLong("ruta"));
			rutas.setIdCasilla(rs.getLong("id_casilla"));
			rutas.setTipoCasilla(rs.getString("tipo_casilla"));
			rutas.setSeccionId(rs.getLong("seccion_id"));
			rutas.setIdRutaRg(rs.getString("id_ruta_rg"));
			rutas.setStatus(rs.getLong("status"));
			rutas.setIdCrg(rs.getLong("id_crg"));
			rutas.setZona(rs.getString("zona_crg")+"-"+ rs.getString("id_zona_crg"));
			
			lstRutas.add(rutas);
			
		} while (rs.next());
		
		return lstRutas;
	}

}
