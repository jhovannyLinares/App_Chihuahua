package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.EnvioActas;
import mx.morena.persistencia.repository.IEnvioActasRepository;
import mx.morena.persistencia.rowmapper.ActasRowMapper;
import mx.morena.persistencia.rowmapper.IdMaxEnvioActasRowMapper;
import mx.morena.persistencia.rowmapper.LongRowMapper;

@Repository
public class EnvioActasRepository implements IEnvioActasRepository {
	
	@Autowired
	private JdbcTemplate template;
	
	private String campos = " tipo_votacion,ruta_acta,id_casilla,registro_acta ";

	@Override
	public void save(EnvioActas actas) {
		String sql = " INSERT INTO app_envio_actas ( " + campos +" ) VALUES(?,?,?,?)";
		
		template.update(sql, 
				new Object[] { actas.getTipoVotacion(), actas.getRutaActa(), actas.getIdCasilla(), actas.getRegistroActa() });
	}

	@Override
	public Long getIdMax() {
		String sql = "SELECT MAX(id_acta) FROM app_envio_actas";
		return template.queryForObject(sql, new IdMaxEnvioActasRowMapper());
	}

	@Override
	public Long countByTipoVotacionAndDistrito(Long df, Long tipo) {
		String sql = "select count(*) from app_envio_actas ea inner join app_casilla ac on ac.id = ea.id_casilla "
				+ "where ac.federal_id = ? and ea.tipo_votacion = ? ";
		try {
			return template.queryForObject(sql, new Object[] { df, tipo }, new int[] { Types.NUMERIC, Types.NUMERIC },
					new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<EnvioActas> getCasilla(Long idCasilla) {
		String sql = "SELECT id_acta,tipo_votacion,ruta_acta,id_casilla,registro_acta FROM app_envio_actas where id_casilla = ?";
		return template.queryForObject(sql, new Object[] { idCasilla }, new int[] { Types.NUMERIC}, new ActasRowMapper());
	}

}
