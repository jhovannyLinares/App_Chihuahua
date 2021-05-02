package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.ReporteAsignacionRg;

public class ReporteAsignacionRgRowMapper implements RowMapper<List<ReporteAsignacionRg>>{

	@Override
	public List<ReporteAsignacionRg> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<ReporteAsignacionRg> lstRg = new ArrayList<ReporteAsignacionRg>();
		ReporteAsignacionRg rep = null;
		
		do {
			rep = new ReporteAsignacionRg();
			
			rep.setIdDistrito(rs.getLong("dFederal"));
			rep.setIdLocal(rs.getLong("dLocal"));
			rep.setMunicipio(rs.getString("municipio"));
			rep.setIdRuta(rs.getLong("ruta"));
			rep.setIdCasilla(rs.getLong("casillas"));
			
			lstRg.add(rep);
			
		} while (rs.next());
		
		return lstRg;
	}

}
