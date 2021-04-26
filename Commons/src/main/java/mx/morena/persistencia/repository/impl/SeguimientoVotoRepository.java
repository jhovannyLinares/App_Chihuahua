package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.ISeguimientoVotoRepository;
import mx.morena.persistencia.rowmapper.IdMaxConvencidos;
import mx.morena.persistencia.rowmapper.SeccionCotsRowMapper;

@Repository
public class SeguimientoVotoRepository implements ISeguimientoVotoRepository{
	
	@Autowired
	private JdbcTemplate template;
	
	@Override
	public Long countByDistAndTipo(Long distritoId, Long tipo) {
		String sql = "select count(*) from app_convencidos ac where distrito_federal_id = ? and tipo = ?  ";
		try {
			return template.queryForObject(sql, new Object[] { distritoId, tipo}, new int[] { Types.NUMERIC, Types.NUMERIC },
					new IdMaxConvencidos());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long countByLocalAndTipo(Long distrito, Long tipo) {
		String sql = "select count(*) from app_convencidos ac where distrito_federal_id = ? and tipo = ?  ";
		try {
			return template.queryForObject(sql, new Object[] { distrito, tipo}, new int[] { Types.NUMERIC, Types.NUMERIC },
					new IdMaxConvencidos());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public List<SeccionElectoral> getDistritos() {
		String sql = "select  distrito_id, federal from app_secciones as2 group by distrito_id, federal order by distrito_id asc";
		try {
			return template.queryForObject(sql,	new SeccionCotsRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long countByNotificados(Long distrito, Long tipo) {
		String sql = "select count(*) from app_convencidos ac where distrito_federal_id = ? and tipo = ?  ";
		try {
			return template.queryForObject(sql, new Object[] { distrito, tipo}, new int[] { Types.NUMERIC, Types.NUMERIC },
					new IdMaxConvencidos());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
