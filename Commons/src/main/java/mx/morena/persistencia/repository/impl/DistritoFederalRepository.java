package mx.morena.persistencia.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.rowmapper.DistritoFederalRowMapper;
import mx.morena.persistencia.rowmapper.DistritosFederalesRowMapper;

@Repository
public class DistritoFederalRepository implements IDistritoFederalRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public DistritoFederal findById(String federalId) {

		String sql = "SELECT * FROM app_distrito_federal WHERE id = ?";

		return template.queryForObject(sql, new Object[] { federalId }, new int[] { 1 },
				new DistritoFederalRowMapper());

	}

	@Override
	public List<DistritoFederal> findAll() {
		
		String sql = "SELECT * FROM app_distrito_federal";

		return template.queryForObject(sql, new DistritosFederalesRowMapper());
	}

	@Override
	public List<DistritoFederal> findByEntidad(String idEntidad) {
		String sql = "SELECT * FROM app_distrito_federal where entidad_id = ?";

		return template.queryForObject(sql, new Object[] { idEntidad }, new int[] { 1 }, new DistritosFederalesRowMapper());
	}

}
