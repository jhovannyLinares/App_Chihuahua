package mx.morena.persistencia.repository.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Preguntas;
import mx.morena.persistencia.entidad.Votacion;
import mx.morena.persistencia.repository.IResultadoVotacionRepository;

@Repository
public class ResultadoVotacionRepository implements IResultadoVotacionRepository {
	
	Logger logger = LoggerFactory.getLogger(ResultadoVotacionRepository.class);

	@Autowired
	private JdbcTemplate template;

	@Override
	public void save(List<Votacion> votaciones) {

		for (Votacion votacion : votaciones) {
			save(votacion);
		}

	}

	public void save(Votacion votacion) {

		String sql = "INSERT INTO public.app_votos_partido_ambito "
				+ " (id_casilla, id_ambito, id_partido, votos, id_coalision, is_coalision) " + " VALUES(?,?,?,?,?,?) ";

		template.update(sql, new Object[] { votacion.getIdCasilla(), votacion.getTipoVotacion(),
				votacion.getIdPartido(), votacion.getVotos(), votacion.getIdCoalicion(), votacion.isCoalicion() });

	}

	@Override
	public void save(Preguntas preguntas) {
		
		String sql = "INSERT INTO public.app_cuestionario_resultados "
				+ " (id_casilla, id_ambito, "
				+ " numero_personas_votaron, numero_representantes_votaron, suma_votantes, votos_sacados_urna, votos_x_partido_y_coalicion, es_igual_votos_persona_x_votos_urna,"
				+ "  es_igual_votos_urna_x_total_votacion) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		logger.debug(sql);

		template.update(sql, new Object[] { preguntas.getIdCasilla(), preguntas.getTipoVotacion(),
				preguntas.getNumeroPersonasVotaron(), preguntas.getNumeroRepresentantesVotaron(),
				preguntas.getSumaVotantes(), preguntas.getVotosSacadosUrna(), preguntas.getVotosXPartidoYCoalicion(),
				preguntas.getEsIgualVotosPersonaXVotosUrna(), preguntas.getEsIgualVotosUrnaXTotalVotacion() });
		
	}

}
