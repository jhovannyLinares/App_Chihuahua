package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.EnvioActas;

public class ActasByTipoRowMapper  implements RowMapper<List<EnvioActas>>  {

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
			acta.setTipoActa(rs.getLong("tipo_acta"));
			acta.setCopiaRespuestaGobernador(rs.getBoolean("copia_resultados_gobernador"));
			acta.setCopiaRespuestaDiputadoLocal(rs.getBoolean("copia_resultados_diputado_local"));
			acta.setCopiaRespuestaSindico(rs.getBoolean("copia_resultados_sindico"));
			acta.setCopiaRespuestaDiputadoFederal(rs.getBoolean("copia_resultados_diputado_federal"));
			acta.setCopiaRespuestaPresidenteMunicipal(rs.getBoolean("copia_resultados_presidente_municipal"));
			

			actas.add(acta);

		} while (rs.next());

		return actas;
	}

}
