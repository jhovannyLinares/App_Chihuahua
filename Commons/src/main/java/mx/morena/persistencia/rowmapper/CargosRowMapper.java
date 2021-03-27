package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Cargo;

public class CargosRowMapper implements RowMapper<List<Cargo>> {

	@Override
	public List<Cargo> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Cargo> cargos = new ArrayList<Cargo>();
		Cargo cargo = null;

		do {
			cargo = new Cargo();

			cargo.setId(rs.getLong("id_cargo"));
			cargo.setDescripcion(rs.getString("descripcion"));

			cargos.add(cargo);
		} while (rs.next());


		return cargos;
	}

}
