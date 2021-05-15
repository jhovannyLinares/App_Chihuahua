package mx.morena.persistencia.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.RepresentacionPartidos;
import mx.morena.persistencia.repository.IPartidoVotacionRepository;
import mx.morena.persistencia.rowmapper.RepresentacionPartidoRowMapper;

@Repository
public class PartidoVotacionRepository implements IPartidoVotacionRepository {
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public List<RepresentacionPartidos> getPartidos() {
		String sql = "select * from app_representacion_partidos";
		try {
			return template.queryForObject(sql, new RepresentacionPartidoRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
