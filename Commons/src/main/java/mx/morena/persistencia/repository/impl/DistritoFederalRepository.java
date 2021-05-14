package mx.morena.persistencia.repository.impl;

import java.util.List;import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.rowmapper.DistritoFederalRowMapper;
import mx.morena.persistencia.rowmapper.DistritosFederalesRowMapper;
import mx.morena.persistencia.rowmapper.LongRowMapper;
import mx.morena.persistencia.rowmapper.StringRowMapper;

@Repository
public class DistritoFederalRepository implements IDistritoFederalRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public DistritoFederal findById(Long federalId) {

		String sql = "SELECT * FROM app_distrito_federal WHERE id = ?";

		return template.queryForObject(sql, new Object[] { federalId }, new int[] { Types.NUMERIC },
				new DistritoFederalRowMapper());

	}

	@Override
	public List<DistritoFederal> findAll() {
		
		String sql = "SELECT * FROM app_distrito_federal";

		return template.queryForObject(sql, new DistritosFederalesRowMapper());
	}

	@Override
	public List<DistritoFederal> findByEntidad(Long idEntidad) {
		String sql = "SELECT * FROM app_distrito_federal where entidad_id = ?";

		return template.queryForObject(sql, new Object[] { idEntidad }, new int[] { Types.NUMERIC }, new DistritosFederalesRowMapper());
	}

	@Override
	public Long findDstFederal(Long idUsuario) {
		String sql = "select federal_id from app_usuario au where id = ?";

		return template.queryForObject(sql, new Object[] { idUsuario }, new int[] { Types.NUMERIC }, new LongRowMapper());
	}

	@Override
	public String findDstritoFederal(Long idDistrito) {
		String sql = "select cabecera from app_distrito_federal adl where id = ?";

		return template.queryForObject(sql, new Object[] { idDistrito }, new int[] { Types.NUMERIC }, new StringRowMapper());
	}

	@Override
	public Long findDstMunicipio(Long municipal) {
		
		String sql = "select am.federal_id from app_representantes ar "
				+ "inner join app_municipio am "
				+ "on ar.municipio_id = am.id "
				+ "where  ar.municipio_id = ? group by am.federal_id ";

		return template.queryForObject(sql, new Object[] { municipal }, new int[] { Types.NUMERIC }, new LongRowMapper());
	}

}
