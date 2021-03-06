package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.MotivosTerminoVotacion;
import mx.morena.persistencia.repository.IMotivosTerminoVotacionRepository;
import mx.morena.persistencia.rowmapper.MotivoTerminoVotacionRowMapper;
import mx.morena.persistencia.rowmapper.MotivosTerminoVotacionRowMapper;

@Repository
public class MotivosTerminoVotacionRepository implements IMotivosTerminoVotacionRepository {
	

	@Autowired
	private JdbcTemplate template;

	@Override
	public List<MotivosTerminoVotacion> findAll() {
		String sql = "select * from app_motivos_termino_votacion";
		try {
			return template.queryForObject(sql, new MotivosTerminoVotacionRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public MotivosTerminoVotacion getById(Long id) {
		String sql = "select * from app_motivos_termino_votacion where id = ? ";
		
		try {
			return template.queryForObject(sql, new Object[] { id }, new int[] { Types.NUMERIC}, new MotivoTerminoVotacionRowMapper());
		} catch (EmptyResultDataAccessException e) {
			
			return null;
		}
	}
}
