package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Convencidos;

public class ConvencidoRowMapper implements RowMapper<Convencidos> {

	@Override
	public Convencidos mapRow(ResultSet rs, int rowNum) throws SQLException {

		Convencidos convencido = new Convencidos();

		convencido.setId(rs.getLong("id"));
		convencido.setApellidoMaterno(rs.getString("apellido_materno"));
		convencido.setApellidoPaterno(rs.getString("apellido_paterno"));
		convencido.setBanco(rs.getString("banco"));
		convencido.setCalle(rs.getString("calle"));
		convencido.setClabeInterbancaria(rs.getString("clabe_interbancaria"));
		convencido.setClaveElector(rs.getString("clave_elector"));
		convencido.setColonia(rs.getString("colonia"));
		convencido.setCorreo(rs.getString("correo"));
		convencido.setCp(rs.getString("codigo_postal"));
		convencido.setCurp(rs.getString("curp"));
		convencido.setDv(rs.getBoolean("dv"));
		convencido.setEstatus(rs.getString("estatus").charAt(0));
		convencido.setFechaBaja(rs.getDate("fecha_baja"));
		convencido.setFechaReactivacion(rs.getDate("fecha_reactivacion"));
		convencido.setFechaRegistro(rs.getDate("fecha_registro"));
		convencido.setFechaSistema(rs.getTimestamp("fecha_sistema"));
		convencido.setMov(rs.getBoolean("mov"));
		convencido.setNombre(rs.getString("nombre"));
		convencido.setNumExterior(rs.getString("numero_exterior"));
		convencido.setNumInterior(rs.getString("numero_interior"));
		convencido.setTelCasa(rs.getString("telefono_casa"));
		convencido.setTelCelular(rs.getString("telefono_celular"));
		convencido.setIdFederal(rs.getLong("distrito_federal_id"));
		convencido.setIdEstado(rs.getLong("estado_id"));
		convencido.setIdMunicipio(rs.getLong("municipio_id"));
		convencido.setUsuario(rs.getLong("usuario_id_usuario"));
		convencido.setIdSeccion(rs.getLong("seccion_id")); 

		return convencido;
	}

}
