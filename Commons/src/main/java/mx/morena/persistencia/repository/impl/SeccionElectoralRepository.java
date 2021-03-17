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
		String sql = "SELECT * FROM app_localidad l INNER JOIN app_convencidos c ON l.idcot = c.id where l.idcot = ? and c.tipo = ?";
		try {
			return template.queryForObject(sql, new Object[] { cotId, tipo },
					new int[] { Types.NUMERIC, Types.NUMERIC }, new SeccionesRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

//	@Override
//	public List<SeccionElectoral> findAllById(List<String> idSecciones) {
//
//		String sql = "SELECT entidad_id, municipio_id, id, nombre, tipo, seccion_id, idcot FROM app_localidad ";
//		try {
//			return template.queryForObject(sql, new Object[] { idSecciones }, new int[] { Types.NUMERIC },
//					new SeccioneRowMapper());
//		} catch (EmptyResultDataAccessException e) {
//			return null;
//		}
//	}

	@Override
	public void updateIdCot(Long idSeccion, Long idCot) {

		String sql = "UPDATE app_localidad SET idcot = ? WHERE idcot = ?";

		template.update(sql, new Object[] { idSeccion, idCot }, new int[] { Types.NUMERIC, Types.NUMERIC });

	}

	@Override
	public SeccionElectoral findById(Long idSeccion) {
		String sql = "SELECT * FROM app_localidad  where id = ?";
		try {
			return template.queryForObject(sql, new Object[] { idSeccion }, new int[] { Types.NUMERIC },
					new SeccionRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


}
