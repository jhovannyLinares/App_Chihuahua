package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Capacitacion;

public class CapacitacionRowMapper implements RowMapper<List<Capacitacion>>{

	@Override
	public List<Capacitacion> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Capacitacion> lstCap = new ArrayList<Capacitacion>();
		Capacitacion rep = null;
		
		do {
			rep = new Capacitacion();
			
			rep.setIdRepresentante(rs.getLong("id"));
			rep.setClaveElector(rs.getString("clave"));
			rep.setTipoRepresentante(rs.getString("representante"));
			rep.setAsignado(rs.getLong("asignado"));
			rep.setCargo(rs.getString("cargo"));
			
			lstCap.add(rep);
			
		}while (rs.next());
		
		return lstCap;
	}

}