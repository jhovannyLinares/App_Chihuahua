package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Convencidos;

public class ConvencidoNotificadoRowMapper implements RowMapper<Convencidos> {

	@Override
	public Convencidos mapRow(ResultSet rs, int rowNum) throws SQLException {

		Convencidos convencido = new Convencidos();

		convencido.setId(rs.getLong("id"));
		convencido.setNombre(rs.getString("nombre"));
		convencido.setApellidoMaterno(rs.getString("apellido_materno"));
		convencido.setApellidoPaterno(rs.getString("apellido_paterno"));
		convencido.setIsNotificado(rs.getBoolean("is_notificado"));

		return convencido;
	}

}
