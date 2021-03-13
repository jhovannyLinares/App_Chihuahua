package mx.morena.persistencia.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Entidad;
import mx.morena.persistencia.repository.IEntidadRepository;
import mx.morena.persistencia.rowmapper.EntidadRowMapper;
import mx.morena.persistencia.rowmapper.EntidadesRowMapper;

@Repository
public class EntidadRepository implements IEntidadRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public List<Entidad> findAll() {

		String sql = "SELECT * FROM app_entidad";

		return template.queryForObject(sql, new EntidadesRowMapper());
	}

	@Override
	public Entidad findById(String idEntidad) {
		
		String sql = "SELECT * FROM app_entidad where id = ?";

		return template.queryForObject(sql, new Object[] { idEntidad }, new int[] { 1 }, new EntidadRowMapper());
	}


}
