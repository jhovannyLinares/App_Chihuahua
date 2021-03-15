package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Convencidos;

public class ConvencidosRowMapper implements RowMapper<List<Convencidos>> {

	@Override
	public List<Convencidos> mapRow(ResultSet rs, int rowNum) throws SQLException {

		List<Convencidos> convencidos = new ArrayList<Convencidos>();

		if (rs.next()) {

			Convencidos convencido = null;
			do {

				convencido = new Convencidos();

				convencido.setId(rs.getLong("id"));
				convencido.setaMaterno(rs.getString("apellido_materno"));
				convencido.setaPaterno(rs.getString("apellido_paterno"));
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
				convencido.setDistritoFederal(rs.getLong("distrito_federal_id"));
				convencido.setEstado(rs.getLong("estado_id"));
				convencido.setMunicipio(rs.getLong("municipio_id"));
				convencido.setUsuario(rs.getLong("usuario_id_usuario"));

				convencidos.add(convencido);

			} while (rs.next());
		}

		return convencidos;
	}

}
