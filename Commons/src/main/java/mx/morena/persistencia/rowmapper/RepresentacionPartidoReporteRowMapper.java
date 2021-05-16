package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import mx.morena.persistencia.entidad.RepresentacionPartidosReporte;

public class RepresentacionPartidoReporteRowMapper implements RowMapper<List<RepresentacionPartidosReporte>> {

	@Override
	public List<RepresentacionPartidosReporte> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<RepresentacionPartidosReporte> partidos = new ArrayList<RepresentacionPartidosReporte>();

		RepresentacionPartidosReporte partido = null;
		
		do {
			
			partido = new RepresentacionPartidosReporte();

			partido.setId(rs.getLong("id"));
			partido.setPartido(rs.getString("partido"));
			partido.setTieneRepresentante(rs.getBoolean("tiene_representante"));

			partidos.add(partido);
			
		} while (rs.next());

		return partidos;
	}

}
