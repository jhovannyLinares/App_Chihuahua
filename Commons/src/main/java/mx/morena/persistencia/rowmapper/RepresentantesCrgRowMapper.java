package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Representantes;

public class RepresentantesCrgRowMapper implements RowMapper<List<Representantes>>{

	@Override
	public List<Representantes> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Representantes> ltsRepresentantes = new ArrayList<Representantes>();
		Representantes rep = null;
		
		do {
			rep = new Representantes();
			
			rep.setId(rs.getLong("id"));
			rep.setNombre(rs.getString("nombre"));
			rep.setApellidoPaterno(rs.getString("apellido_paterno"));
			rep.setApellidoMaterno(rs.getString("apellido_materno"));
			rep.setTipo(rs.getInt("tipo_representante"));
			
			ltsRepresentantes.add(rep);
			
		} while (rs.next());
		return ltsRepresentantes;
	}

	
}
