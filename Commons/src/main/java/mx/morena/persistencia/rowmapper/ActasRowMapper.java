package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.EnvioActas;

public class ActasRowMapper implements RowMapper<List<EnvioActas>> {

	@Override
	public List<EnvioActas> mapRow(ResultSet rs, int rowNum) throws SQLException {

		List<EnvioActas> actas = new ArrayList<EnvioActas>();

		EnvioActas acta;
		do { 

			acta = new EnvioActas();

			acta.setIdActa(rs.getLong("id_acta"));
			acta.setIdCasilla(rs.getLong("id_casilla"));
			acta.setRegistroActa(rs.getTimestamp("registro_acta"));
			acta.setRutaActa(rs.getString("ruta_acta"));
			acta.setTipoVotacion(rs.getLong("tipo_votacion"));

			actas.add(acta);

		} while (rs.next());

		return actas;
	}
}
