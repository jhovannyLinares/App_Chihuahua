package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.RepresentanteClaveElectoral;

public class RepresentanteClaveRowMapper implements RowMapper<List<RepresentanteClaveElectoral>>{

	@Override
	public List<RepresentanteClaveElectoral> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<RepresentanteClaveElectoral> lstRepre = new ArrayList<RepresentanteClaveElectoral>();
		RepresentanteClaveElectoral rep = null;
		
		do {
			rep = new RepresentanteClaveElectoral();
			
			rep.setIdRepresentante(rs.getLong("id"));
			rep.setNombre(rs.getString("nombre") + " " + rs.getString("apellido_paterno") + " " + rs.getString("apellido_materno"));
			rep.setIdTipoRepresentante(rs.getLong("idTipoRep"));
			rep.setTipoRepresentante(rs.getString("nombreTipoRep"));
			rep.setIdDistrito(rs.getLong("idDistrito"));
			rep.setDistrito(rs.getString("nombredistrito"));
			rep.setIdDistritoAsignado(rs.getLong("iddistriroasignado"));
			rep.setDistritoAsignado(rs.getString("nombredistritoasignado"));
			rep.setAsignado(rs.getBoolean("is_asignado"));
			
			lstRepre.add(rep);
			
		} while (rs.next());
		
		return lstRepre;
	}

}
