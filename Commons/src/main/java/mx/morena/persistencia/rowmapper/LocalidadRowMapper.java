//package mx.morena.persistencia.rowmapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import org.springframework.jdbc.core.RowMapper;
//
//import mx.morena.persistencia.entidad.Localidad;
//
//public class LocalidadRowMapper implements RowMapper<Localidad> {
//
//	@Override
//	public Localidad mapRow(ResultSet rs, int rowNum) throws SQLException {
//		
//		
//		Localidad localidad = new Localidad();
//
//		localidad.setId(rs.getLong("id"));
//		localidad.setDescripcion(rs.getLong("seccion_id") +" - "+ rs.getString("nombre") +" - "+ rs.getString("tipo"));
//		localidad.setTipo(rs.getString("tipo"));
//
//		return localidad;
//	}
//
//}
