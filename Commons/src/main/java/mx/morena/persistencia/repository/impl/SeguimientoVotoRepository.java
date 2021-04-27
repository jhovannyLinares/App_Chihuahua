package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.ISeguimientoVotoRepository;
import mx.morena.persistencia.rowmapper.ConvencidosRowMapper;
import mx.morena.persistencia.rowmapper.IdMaxConvencidos;
import mx.morena.persistencia.rowmapper.LongRowMapper;
import mx.morena.persistencia.rowmapper.SeccionCotsRowMapper;
import mx.morena.persistencia.rowmapper.SeguimientosVotoRowMapper;

@Repository
public class SeguimientoVotoRepository implements ISeguimientoVotoRepository {

	@Autowired
	private JdbcTemplate template;
	
	@Override
	public Long countByDistAndTipo(Long distritoId, Long tipo) {
		String sql = "select count(*) from app_convencidos ac where distrito_federal_id = ? and tipo = ?  ";
		try {
			return template.queryForObject(sql, new Object[] { distritoId, tipo },
					new int[] { Types.NUMERIC, Types.NUMERIC }, new IdMaxConvencidos());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long countByLocalAndTipo(Long distrito, Long tipo) {
		String sql = "select count(*) from app_convencidos ac where distrito_federal_id = ? and tipo = ?  ";
		try {
			return template.queryForObject(sql, new Object[] { distrito, tipo },
					new int[] { Types.NUMERIC, Types.NUMERIC }, new IdMaxConvencidos());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<SeccionElectoral> getDistritos() {
		String sql = "select  distrito_id, federal from app_secciones as2 group by distrito_id, federal order by distrito_id asc";
		try {
			return template.queryForObject(sql, new SeccionCotsRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long countByNotificados(Long distrito, Long tipo) {
		String sql = "select count(*) from app_convencidos ac where distrito_federal_id = ? and tipo = ?  ";
		try {
			return template.queryForObject(sql, new Object[] { distrito, tipo },
					new int[] { Types.NUMERIC, Types.NUMERIC }, new IdMaxConvencidos());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long countNotificados(Long distritoId) {
		String sql = "select COUNT(*) from app_convencidos ac where distrito_federal_id = ? and is_notificado = true";
		try {
			return template.queryForObject(sql, new Object[] { distritoId }, new int[] { Types.NUMERIC },
					new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public List<Convencidos> getConvencidos(Long idSeccion) {
		String sql = "select ac.nombre , ac.apellido_paterno , ac.apellido_materno, ac2.colonia, ac2.referencia, ac.is_notificado "
				+ "from public.app_convencidos ac "
				+ "inner join app_secciones as2 "
				+ "on ac.seccion_id = as2.id inner join app_casilla ac2 on as2.id = ac2.id "
				+ "where as2.id = ? "
				+ "and ac.tipo = 1 ";
		try {
			return template.queryForObject(sql, new Object[] { idSeccion },
					new int[] { Types.NUMERIC }, new SeguimientosVotoRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}
}