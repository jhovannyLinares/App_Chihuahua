package mx.morena.persistencia.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Votacion;
import mx.morena.persistencia.repository.IResultadoVotacionRepository;

@Repository
public class ResultadoVotacionRepository implements IResultadoVotacionRepository {

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

}
