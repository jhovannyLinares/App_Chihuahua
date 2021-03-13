package mx.morena.persistencia.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.repository.IMunicipioRepository;
import mx.morena.persistencia.rowmapper.MunicipioRowMapper;
import mx.morena.persistencia.rowmapper.MunicipiosRowMapper;

@Repository
public class MunicipioRepository implements IMunicipioRepository {
	@Autowired
	private JdbcTemplate template;

	@Override
	public Municipio getById(String municipio) {

		String sql = "SELECT * FROM app_municipio WHERE id = ?";

		return template.queryForObject(sql, new Object[] { municipio }, new int[] { 1 }, new MunicipioRowMapper());
	}

	@Override
	public List<Municipio> findAll() {

		String sql = "SELECT * FROM app_municipio";

		return template.queryForObject(sql, new MunicipiosRowMapper());
	}

	@Override
	public List<Municipio> getByFederal(String idFederal) {
		String sql = "SELECT * FROM app_municipio where federal_id = ? ";

		return template.queryForObject(sql, new Object[] { idFederal }, new int[] { 1 }, new MunicipiosRowMapper());
	}

}
