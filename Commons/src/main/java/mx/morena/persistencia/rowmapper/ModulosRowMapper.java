package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Modulo;

public class ModulosRowMapper implements RowMapper<List<Modulo>> {

	@Override
	public List<Modulo> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Modulo> modulos = new ArrayList<Modulo>();
		
		Modulo modulo =null;
		do {
			modulo = new Modulo();
			
			modulo.setId(rs.getLong("id"));
			modulo.setDescripcion(rs.getString("descripcion"));
			modulo.setUrl(rs.getString("url"));
			modulo.setModuloPadre(rs.getLong("modulo_padre_id_modulo"));
			
			modulos.add(modulo);
			
		}while(rs.next());
		
		return modulos;
		
		
	}

}
