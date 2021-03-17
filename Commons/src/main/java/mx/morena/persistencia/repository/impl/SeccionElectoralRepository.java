package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;
import mx.morena.persistencia.rowmapper.SeccionesRowMapper;

@Repository
public class SeccionElectoralRepository implements ISeccionElectoralRepository {

	@Autowired
	private JdbcTemplate template;
	
	@Override
	public List<SeccionElectoral> findByCotId(Long cotId, Long tipo) {
		String sql = "SELECT * FROM app_localidad l INNER JOIN app_convencidos c ON l.idcot = c.id where l.idcot = ? and c.tipo = ?";
		try {
			return template.queryForObject(sql, new Object[] { cotId, tipo }, new int[] { Types.NUMERIC, Types.NUMERIC },
				new SeccionesRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<SeccionElectoral> findAllById(List<String> idSecciones) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(SeccionElectoral sec) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateIdCot(Long cotId) {
		String sql = "UPDATE app_localidad SET idcot = NULL WHERE idcot = ?";
		
		template.update(sql,
				new Object[] { cotId },
				new int[] { Types.NUMERIC });
	}


}
