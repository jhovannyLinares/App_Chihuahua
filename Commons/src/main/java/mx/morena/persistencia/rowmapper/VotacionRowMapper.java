package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Votacion;

public class VotacionRowMapper implements RowMapper<List<Votacion>> {

	@Override
	public List<Votacion> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Votacion> votaciones = new ArrayList<Votacion>();
		Votacion voto = null;

		do {
			voto = new Votacion();
			
			voto.setIdCasilla(rs.getLong("id_casilla"));
			voto.setTipoVotacion(rs.getInt("id_ambito"));
			voto.setIdPartido(rs.getLong("id_partido"));
			voto.setVotos(rs.getInt("votos"));
			voto.setIdCoalicion(rs.getLong("id_coalision"));
			voto.setCoalicion(rs.getBoolean("is_coalision"));

			votaciones.add(voto);
		} while (rs.next());

		return votaciones;
	}

}
