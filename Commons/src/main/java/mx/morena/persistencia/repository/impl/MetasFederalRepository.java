package mx.morena.persistencia.repository.impl;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Metas;
import mx.morena.persistencia.repository.IMetasFederalRepository;
import mx.morena.persistencia.rowmapper.MetasRowMapper;

@Repository
public class MetasFederalRepository implements IMetasFederalRepository{
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public Metas getMetasByFederal(Long idFederal) {
		String sql = "select listado_nominal as listado_nominal, meta_crg as meta_crg, "
				+ "meta_rg as meta_rg, meta_rc as meta_rc, meta_convencidos as meta_convencidos "
				+ "from app_metas_federales amf "
				+ "where distrito_federal_id = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idFederal }, 
					new int[] { Types.NUMERIC }, new MetasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	
	}

	@Override
	public Metas getMetasByLocal(Long idFederal) {
		String sql = "select listado_nominal as listado_nominal, meta_crg as meta_crg, "
				+ "meta_rg as meta_rg, meta_rc as meta_rc, meta_convencidos as meta_convencidos "
				+ "from app_metas_locales aml "
				+ "where distrito_local_id = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idFederal }, 
					new int[] { Types.NUMERIC }, new MetasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Metas getMetasByMunicipio(Long idMunicipio) {
		String sql = "select listado_nominal as listado_nominal, suma_crg_ps as meta_crg, suma_rg as meta_rg, suma_rc as meta_rc, "
				+ "convencidos as meta_convencidos from app_metas_municipios where clave_municipio = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idMunicipio }, 
					new int[] { Types.NUMERIC }, new MetasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
