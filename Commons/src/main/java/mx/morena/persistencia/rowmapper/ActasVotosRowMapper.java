package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.ActasVotos;

public class ActasVotosRowMapper implements RowMapper<List<ActasVotos>>{

	@Override
	public List<ActasVotos> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<ActasVotos> actasVotaciones = new ArrayList<ActasVotos>();

		ActasVotos actaVoto = null;
		do {
			
			actaVoto = new ActasVotos();

			actaVoto.setIdCasilla(rs.getLong("id_casilla"));
			actaVoto.setGobernador(rs.getBoolean("actas_gobernador"));
			actaVoto.setDiputadoFedaral(rs.getBoolean("actas_federal"));
			actaVoto.setDiputadoLocal(rs.getBoolean("actas_local"));
			actaVoto.setPresidenteMunicipal(rs.getBoolean("actas_municipal"));
			actaVoto.setSindico(rs.getBoolean("actas_sindico"));
			
			actasVotaciones.add(actaVoto);

		} while (rs.next());

		return actasVotaciones;
	}

}
