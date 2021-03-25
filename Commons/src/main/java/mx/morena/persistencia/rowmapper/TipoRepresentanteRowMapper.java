package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Perfil;

public class TipoRepresentanteRowMapper implements RowMapper<List<Perfil>>{

	@Override
	public List<Perfil> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Perfil> perfil = new ArrayList<Perfil>();
		Perfil prf = null;
		
		do {
			prf = new Perfil();
			
			prf.setId(rs.getLong("id"));
			prf.setNombre(rs.getString("nombre"));
			
			perfil.add(prf);
			
		} while (rs.next());
		
		return perfil;
	}

}
