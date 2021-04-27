package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import mx.morena.persistencia.entidad.Convencidos;

public class SeguimientosVotoRowMapper implements RowMapper<List<Convencidos>> {
	
	public List<Convencidos> mapRow(ResultSet rs, int rowNum) throws SQLException {

		List<Convencidos> secciones = new ArrayList<Convencidos>();
		Convencidos seccion = null;

		do {
			seccion = new Convencidos();

			seccion.setNombre(rs.getString("nombre"));
			seccion.setApellidoPaterno(rs.getString("apellido_paterno"));
			seccion.setApellidoMaterno(rs.getString("apellido_Materno"));
			seccion.setColonia_casilla(rs.getString("colonia"));
			seccion.setReferencia_casilla(rs.getString("referencia"));
			seccion.setIsNotificado(rs.getBoolean("is_notificado"));
			
			secciones.add(seccion);
		} while (rs.next());

		return secciones;
	}
}
