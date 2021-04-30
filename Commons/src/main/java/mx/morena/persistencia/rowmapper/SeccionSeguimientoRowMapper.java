package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.SeccionElectoral;

public class SeccionSeguimientoRowMapper implements RowMapper<List<SeccionElectoral>>{

	@Override
	public List<SeccionElectoral> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<SeccionElectoral> secciones = new ArrayList<SeccionElectoral>();
		SeccionElectoral seccion = null;
		
		do {
			seccion = new SeccionElectoral();
			
			seccion.setDescripcion(rs.getString("seccion_id"));
			
			secciones.add(seccion);
			
		}while (rs.next());
		
		return secciones;
	}

}
