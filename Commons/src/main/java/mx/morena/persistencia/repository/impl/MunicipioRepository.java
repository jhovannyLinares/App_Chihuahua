package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.repository.IMunicipioRepository;
import mx.morena.persistencia.rowmapper.LongRowMapper;
import mx.morena.persistencia.rowmapper.MunicipioRowMapper;
import mx.morena.persistencia.rowmapper.MunicipiosRowMapper;
import mx.morena.persistencia.rowmapper.StringRowMapper;

@Repository
public class MunicipioRepository implements IMunicipioRepository {
	@Autowired
	private JdbcTemplate template;

	@Override
	public Municipio findById(Long municipio, Long federal) {

		String sql = "SELECT * FROM app_municipio WHERE id = ? and federal_id = ?";
		try {
			return template.queryForObject(sql, new Object[] { municipio, federal }, new int[] { Types.NUMERIC, Types.NUMERIC },
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

	@Override
	public String getNombreByIdAndDf(Long municipio, Long federal) {
		String select ="select nombre from app_municipio am  where id = ? and federal_id = ?";
		
		String sql = select;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		
//		if (federal != null) {
//			where = " where federal_id = ? ";
//			para.add(federal);
//			type.add(Types.NUMERIC);
//		}
//		
//		if (municipio != null) {
//
//			if (para.size() > 0) {
//				where = where.concat(" and id = ? ");
//			} else {
//				where = " where id = ? ";
//			}
//			para.add(municipio);
//			type.add(Types.NUMERIC);
//		}
		
		
		
		Object[] parametros = new Object[para.size()];
		int[] types = new int[para.size()];

		for (int i = 0; i < para.size(); i++) {
			parametros[i] = para.get(i);
			types[i] = type.get(i);
		}

//		try {
//			sql = select.concat(where);
//
//			return template.queryForObject(sql, parametros, types ,new StringRowMapper());
//		} catch (EmptyResultDataAccessException e) {
//			return null;
//		}
		
		
		try {
			return template.queryForObject(sql, new Object[] { municipio, federal }, new int[] { Types.NUMERIC, Types.NUMERIC },
					new StringRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	
	}

}
