package mx.morena.persistencia.repository.impl;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.repository.IVotosPartidoAmbitoRepository;
import mx.morena.persistencia.rowmapper.LongRowMapper;

@Repository
public class VotosPartidoAmbitoRepository implements IVotosPartidoAmbitoRepository {
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public Long getVotosByDistritoAndMunicipioAndPartido(Long idEntidad, Long df, Long idMunicipio, Long idEleccion, Long idPartido) {
		String sql = "select avpa.votos from app_votos_partido_ambito avpa inner join app_casilla ac on ac.id = avpa.id_casilla "
				+ "where ac.entidad_id = ? and ac.federal_id = ? and ac.municpio_id = ? "
				+ "and avpa.id_ambito = ? and avpa.id_partido = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idEntidad, df, idMunicipio, idEleccion, idPartido }, new int[] 
					{ Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC }, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return 0L;
		}
	}
	
}
