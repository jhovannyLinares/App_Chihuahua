package mx.morena.persistencia.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Localidad;
import mx.morena.persistencia.repository.ILocalidadRepository;
import mx.morena.persistencia.rowmapper.LocalidadRowMapper;
import mx.morena.persistencia.rowmapper.LocalidadesRowMapper;

@Repository
public class LocalidadRepository implements ILocalidadRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public Localidad getById(String localidad) {
		String sql = "SELECT * FROM app_localidad";

		return template.queryForObject(sql, new LocalidadRowMapper());
	}

	@Override
	public List<Localidad> getByMunicipio(String localidad) {
		String sql = "SELECT * FROM app_localidad where municipio_id = ?";

		return template.queryForObject(sql, new Object[] { localidad }, new int[] { 1 }, new LocalidadesRowMapper());
	}

}
