package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;
import mx.morena.persistencia.rowmapper.CountSeccionesRowMapper;
import mx.morena.persistencia.rowmapper.SeccionByUserRowMapper;
import mx.morena.persistencia.rowmapper.SeccionConvencidosRowMapper;
import mx.morena.persistencia.rowmapper.SeccionCotsRowMapper;
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
	public Long getSecciones(Long id) {
		String sql = "select count(*) from app_secciones as2 where distrito_id = ? ";
		return template.queryForObject(sql, new Object[] { id }, new int[] { Types.NUMERIC }, new CountSeccionesRowMapper());
	}



	@Override
	public Long getSeccionesLocal(Long id) {
		String sql = "select count(*) from app_secciones as2 where local_id = ? ";
		return template.queryForObject(sql, new Object[] { id }, new int[] { Types.NUMERIC }, new CountSeccionesRowMapper());
	}



	@Override
	public List<SeccionElectoral> getLocal() {
		String sql = "select  local_id, local from app_secciones as2 group by local_id, local order by local_id asc";
		try {
			return template.queryForObject(sql,	new SeccionConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long getSeccionesByMunicipio(Long id) {
		String sql = "select count(*) from app_secciones ase where ase.municipio_id = ? ";
		return template.queryForObject(sql, new Object[] { id }, new int[] { Types.NUMERIC }, new CountSeccionesRowMapper());
	}



	@Override
	public List<SeccionElectoral> getSeccionByUser(Long idUsuario) {
		String sql = "select ara.seccion_electoral_id as secciones from app_usuario au "
				+ "inner join app_representantes_asignados ara on ara.representante_id = au.id_representante "
				+ "where au.id = ? ";

		return template.queryForObject(sql, new Object[] { idUsuario }, new int[] { Types.NUMERIC },  new SeccionByUserRowMapper());
	}
	
}
