package mx.morena.persistencia.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Eleccion;
import mx.morena.persistencia.entidad.TipoActas;
import mx.morena.persistencia.repository.IEleccionRepository;
import mx.morena.persistencia.rowmapper.EleccionRowMapper;
import mx.morena.persistencia.rowmapper.TipoActasRowMapper;

@Repository
public class EleccionRepository implements IEleccionRepository{
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public List<Eleccion> findAll() {
		
		String sql = "select * from app_tipo_eleccion";

		return template.queryForObject(sql, new EleccionRowMapper());

	}

	@Override
	public List<TipoActas> findAllActas() {
		
		String sql = "select * from app_tipo_actas ata order by id";

		return template.queryForObject(sql, new TipoActasRowMapper());
	}

}
