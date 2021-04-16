package mx.morena.persistencia.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Incidencias;
import mx.morena.persistencia.repository.IIncidenciasRepository;
import mx.morena.persistencia.rowmapper.IncidenciaRowMapper;

@Repository
public class IncidenciaRepository implements IIncidenciasRepository{
	

	@Autowired
	private JdbcTemplate template;

	@Override
	public List<Incidencias> findAll() {
		String sql = "select * from app_incidencias ";

		return template.queryForObject(sql, new IncidenciaRowMapper());
	}

}
