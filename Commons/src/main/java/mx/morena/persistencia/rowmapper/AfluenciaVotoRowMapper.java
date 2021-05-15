package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.AfluenciaVotos;

public class AfluenciaVotoRowMapper implements RowMapper<List<AfluenciaVotos>>{

	@Override
	public List<AfluenciaVotos> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<AfluenciaVotos> afluenciaVotaciones = new ArrayList<AfluenciaVotos>();

		AfluenciaVotos afluenciaVoto = null;
		do {
			
			afluenciaVoto = new AfluenciaVotos();

			afluenciaVoto.setIdCasilla(rs.getLong("id_casilla"));
			afluenciaVoto.setHrs12(rs.getBoolean("hrs_12"));
			afluenciaVoto.setHrs16(rs.getBoolean("hrs_16"));
			afluenciaVoto.setHrs18(rs.getBoolean("hrs_18"));
			
			afluenciaVotaciones.add(afluenciaVoto);

		} while (rs.next());

		return afluenciaVotaciones;
	}

}
