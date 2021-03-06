package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Preguntas;

public class PreguntasRowMapper implements RowMapper<Preguntas> {

	@Override
	public Preguntas mapRow(ResultSet rs, int rowNum) throws SQLException {


		Preguntas preguntas = null;
			preguntas = new Preguntas();
			
			preguntas.setIdCasilla(rs.getLong("id_casilla"));
			preguntas.setTipoVotacion(rs.getInt("id_ambito"));
			preguntas.setBoletasSobrantes(rs.getInt("boletas_sobrantes"));
			preguntas.setNumeroPersonasVotaron(rs.getInt("numero_personas_votaron"));
			preguntas.setNumeroRepresentantesVotaron(rs.getInt("numero_representantes_votaron"));
			preguntas.setSumaVotantes(rs.getInt("suma_votantes"));
			preguntas.setVotosSacadosUrna(rs.getInt("votos_sacados_urna"));
			preguntas.setVotosXPartidoYCoalicion(rs.getInt("votos_x_partido_y_coalicion"));
			preguntas.setEsIgualVotosPersonaXVotosUrna(rs.getBoolean("es_igual_votos_persona_x_votos_urna"));
			preguntas.setEsIgualVotosUrnaXTotalVotacion(rs.getBoolean("es_igual_votos_urna_x_total_votacion"));

		

		return preguntas;
	}

}
