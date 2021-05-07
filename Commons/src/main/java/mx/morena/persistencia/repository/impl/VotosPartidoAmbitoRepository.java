package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Votacion;
import mx.morena.persistencia.repository.IVotosPartidoAmbitoRepository;
import mx.morena.persistencia.rowmapper.LongRowMapper;
import mx.morena.persistencia.rowmapper.VotacionRowMapper;

@Repository
public class VotosPartidoAmbitoRepository implements IVotosPartidoAmbitoRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public Long getVotosByDistritoAndMunicipioAndPartido(Long idEntidad, Long df, Long idMunicipio, Long idEleccion) {
		String sql = "select SUM(avpa.votos) from app_votos_partido_ambito avpa inner join app_casilla ac on ac.id = avpa.id_casilla "
				+ "where ac.entidad_id = ? and ac.federal_id = ? and ac.municpio_id = ? "
				+ "and avpa.id_ambito = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idEntidad, df, idMunicipio, idEleccion },
					new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC },
					new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return 0L;
		}
	}

	@Override
	public Long getVotosByEleccionAndPartido(Long idEntidad, Long idEleccion, Long idPartido) {
		String sql = "select avpa.votos from app_votos_partido_ambito avpa inner join app_casilla ac on ac.id = avpa.id_casilla "
				+ "where ac.entidad_id = ? "
				+ "and avpa.id_ambito = ? and avpa.id_partido = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idEntidad, idEleccion, idPartido },
					new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC },
					new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return 0L;
		}
	}

	@Override
	public List<Votacion> getByAmbitoAndPartido(Long idEstado, Long idFederal, Long idMunicipio, Long idEleccion, Long idPartido) {
		String sql = "select avpa.id_casilla , avpa.id_ambito, avpa.id_partido, avpa.votos, avpa.id_coalision, avpa.is_coalision "
				+ "from app_votos_partido_ambito avpa inner join app_casilla ac on ac.id = avpa.id_casilla "
				+ "where ac.entidad_id = ? and ac.federal_id = ? and ac.municpio_id = ? "
				+ "and avpa.id_ambito = ? and avpa.id_partido = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idEstado, idFederal, idMunicipio, idEleccion, idPartido }, new int[] 
					{ Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC }, new VotacionRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
