package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Rutas;

public class CasillaRowMapper implements RowMapper<Rutas> {

	@Override
	public Rutas mapRow(ResultSet rs, int rowNum) throws SQLException {

		Rutas ruta = new Rutas();

		ruta.setId(rs.getLong("id"));
		ruta.setIdDistrito(rs.getLong("distrito_federal_id"));
		ruta.setNombreDistrito(rs.getString("nombre_distrito"));
		ruta.setIdZonaCrg(rs.getString("id_zona_crg"));
		ruta.setRuta(rs.getLong("ruta"));
		ruta.setIdCasilla(rs.getLong("id_casilla"));
		ruta.setTipoCasilla(rs.getString("tipo_casilla"));
		ruta.setSeccionId(rs.getLong("seccion_id"));
		ruta.setStatus(rs.getLong("status"));
		ruta.setIdRutaRg(rs.getString("id_ruta_rg"));

		return ruta;
	}

}
