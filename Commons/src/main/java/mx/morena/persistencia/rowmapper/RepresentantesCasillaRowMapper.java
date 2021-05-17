package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.RepresentanteCargo;

public class RepresentantesCasillaRowMapper implements RowMapper<List<RepresentanteCargo>> {

	@Override
	public List<RepresentanteCargo> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<RepresentanteCargo> representante = new ArrayList<RepresentanteCargo>();
		RepresentanteCargo rep = null;
		
		do {
			rep = new RepresentanteCargo();
			
			rep.setIdRepresentante(rs.getLong("representante_id"));
			rep.setNombreRepresentante(rs.getString("nombre_representante"));
			rep.setCargo(rs.getString("descripcion"));
			
			representante.add(rep);
			
		} while (rs.next());
		
		
		return representante;
	}

}
