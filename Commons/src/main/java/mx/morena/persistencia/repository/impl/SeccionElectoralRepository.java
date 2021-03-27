package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;
import mx.morena.persistencia.rowmapper.SeccionRowMapper;
import mx.morena.persistencia.rowmapper.SeccionesRowMapper;

@Repository
public class SeccionElectoralRepository implements ISeccionElectoralRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public List<SeccionElectoral> findByCotId(Long cotId, Long tipo) {
		String sql = "SELECT l.id as seccion_id, l.idcot FROM app_secciones l INNER JOIN app_convencidos c ON l.idcot = c.id where l.idcot = ? and c.tipo = ?";
		try {
			return template.queryForObject(sql, new Object[] { cotId, tipo },
					new int[] { Types.NUMERIC, Types.NUMERIC }, new SeccionesRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}



	@Override
	public void updateIdCot(Long idSeccion, Long idCot) {

		String sql = "UPDATE app_secciones SET idcot = ? WHERE id = ?";

		template.update(sql, new Object[] { idCot, idSeccion }, new int[] { Types.NUMERIC, Types.NUMERIC });

	}

	@Override
	public List<SeccionElectoral> findById(Long idSeccion) {
		String sql = "SELECT id as seccion_id, idcot, id FROM app_secciones where id = ?";
		try {
			return template.queryForObject(sql, new Object[] { idSeccion }, new int[] { Types.NUMERIC },
					new SeccionesRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	

	@Override
	public List<SeccionElectoral> getByMunicipio(Long municipio_id) {
		String sql = "SELECT id as seccion_id, municipio_id, idcot FROM app_secciones  where municipio_id = ?  order by id";

		return template.queryForObject(sql, new Object[] { municipio_id }, new int[] { Types.NUMERIC },
				new SeccionesRowMapper()); 
	}

	@Override
	public SeccionElectoral getById(Long idSeccion) {
		String sql = "SELECT id as seccion_id, municipio_id,  municipio FROM app_secciones where id = ?";
		try {
			return template.queryForObject(sql, new Object[] { idSeccion }, new int[] { Types.NUMERIC },
					new SeccionRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<SeccionElectoral> getSeccionesLibresByMpo(Long idMunicipio) {
		String sql = "SELECT s.id AS seccion_id, s.idcot FROM app_secciones s "
				+ "WHERE s.municipio_id = ? AND (s.idcot IS NULL OR s.idcot = 0)";
		try {
			return template.queryForObject(sql, new Object[] { idMunicipio }, new int[] { Types.NUMERIC },
					new SeccionesRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


}
