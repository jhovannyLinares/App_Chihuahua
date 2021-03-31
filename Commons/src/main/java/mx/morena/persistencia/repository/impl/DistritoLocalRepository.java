package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.repository.IDistritoLocalRepository;
import mx.morena.persistencia.rowmapper.DistritoFederalRowMapper;
import mx.morena.persistencia.rowmapper.DistritosFederalesRowMapper;

@Repository
public class DistritoLocalRepository implements IDistritoLocalRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public DistritoFederal findById(Long federalId) {

		String sql = "SELECT * FROM app_distrito_local WHERE id = ?";

		return template.queryForObject(sql, new Object[] { federalId }, new int[] { Types.NUMERIC },
				new DistritoFederalRowMapper());

	}

	@Override
	public List<DistritoFederal> findAll() {
		
		String sql = "SELECT * FROM app_distrito_local";

		return template.queryForObject(sql, new DistritosFederalesRowMapper());
	}

	@Override
	public List<DistritoFederal> findByEntidad(Long idEntidad) {
		String sql = "SELECT * FROM app_distrito_local where entidad_id = ?";

		return template.queryForObject(sql, new Object[] { idEntidad }, new int[] { Types.NUMERIC }, new DistritosFederalesRowMapper());
	}

}
