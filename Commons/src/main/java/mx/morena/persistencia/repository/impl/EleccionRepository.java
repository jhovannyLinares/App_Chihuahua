package mx.morena.persistencia.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Eleccion;
import mx.morena.persistencia.repository.IEleccionRepository;
import mx.morena.persistencia.rowmapper.EleccionRowMapper;

@Repository
public class EleccionRepository implements IEleccionRepository{
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public List<Eleccion> findAll() {
		
		String sql = "select * from app_tipo_eleccion";

		return template.queryForObject(sql, new EleccionRowMapper());

	}

}
