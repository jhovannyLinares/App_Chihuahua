package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
	public Municipio findById(Long municipio) {

		String sql = "SELECT * FROM app_municipio WHERE id = ?";
		try {
			return template.queryForObject(sql, new Object[] { municipio }, new int[] { Types.NUMERIC },
					new MunicipioRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Municipio> findAll() {

		String sql = "SELECT * FROM app_municipio";

		return template.queryForObject(sql, new MunicipiosRowMapper());
	}

	@Override
	public List<Municipio> getByFederal(Long idFederal) {
		String sql = "SELECT * FROM app_municipio where federal_id = ? ";

		return template.queryForObject(sql, new Object[] { idFederal }, new int[] { Types.NUMERIC },
				new MunicipiosRowMapper());
	}

	@Override
	public List<Municipio> getAll() {
		String sql = "select id, nombre from app_municipio group by id, nombre order by id";
		try {
			return template.queryForObject(sql,	new MunicipiosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Municipio> getByEntidad(Long idEntidad) {
		String sql = "select am.federal_id as id, concat(am.id,'-', am.nombre) as nombre from app_municipio am "
				+ "where am.entidad_id = ? order by am.federal_id";
		try {
			return template.queryForObject(sql, new Object[] { idEntidad }, new int[] { Types.NUMERIC },
					new MunicipiosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
