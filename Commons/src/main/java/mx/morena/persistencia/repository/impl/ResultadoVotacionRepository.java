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
				+ " (id_casilla, id_ambito, se_instalo_casilla, hora_instalacion_casillas, instalo_lugar_distinto, causa_instalacion_distinto, boletas_recibidas, "
				+ " boletas_sobrantes, folio_inicial, folio_final, sellaron_boletas, partido_sella_boletas, hora_inicio_votacion, tomaron_funcionarios_fila, nombre_completo_presidente, "
				+ " nombre_completo_secretario, nombre_completo_escrutador_1, nombre_completo_escrutador_2, nombre_completo_escrutador_3, se_presento_incidente, incidente, "
				+ " numero_personas_votaron, numero_representantes_votaron, suma_votantes, votos_sacados_urna, votos_x_partido_y_coalicion, es_igual_votos_persona_x_votos_urna,"
				+ "  es_igual_votos_urna_x_total_votacion) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		logger.debug(sql);

		template.update(sql, new Object[] { preguntas.getIdCasilla(), preguntas.getTipoVotacion(),
				preguntas.getSeInstaloCasilla(), preguntas.getHoraInstalacionCasillas(),
				preguntas.getInstaloLugarDistinto(), preguntas.getCausaInstalacionDistinto(),
				preguntas.getBoletasRecibidas(), preguntas.getBoletasSobrantes(), preguntas.getFolioInicial(),
				preguntas.getFolioFinal(), preguntas.getSellaronBoletas(), preguntas.getPartidoSellaBoletas(),
				preguntas.getHoraInicioVotacion(), preguntas.getTomaronFuncionariosFila(),
				preguntas.getNombreCompletoPresidente(), preguntas.getNombreCompletoSecretario(),
				preguntas.getNombreCompletoEscrutador1(), preguntas.getNombreCompletoEscrutador2(),
				preguntas.getNombreCompletoEscrutador3(), preguntas.getSePresentoIncidente(), preguntas.getIncidente(),
				preguntas.getNumeroPersonasVotaron(), preguntas.getNumeroRepresentantesVotaron(),
				preguntas.getSumaVotantes(), preguntas.getVotosSacadosUrna(), preguntas.getVotosXPartidoYCoalicion(),
				preguntas.getEsIgualVotosPersonaXVotosUrna(), preguntas.getEsIgualVotosUrnaXTotalVotacion() });
		
	}

}
