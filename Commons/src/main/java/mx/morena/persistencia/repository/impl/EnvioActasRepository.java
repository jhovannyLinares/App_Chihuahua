package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.EnvioActas;
import mx.morena.persistencia.repository.IEnvioActasRepository;
import mx.morena.persistencia.rowmapper.ActasByIdRowMapper;
import mx.morena.persistencia.rowmapper.ActasByTipoRowMapper;
import mx.morena.persistencia.rowmapper.ActasRowMapper;
import mx.morena.persistencia.rowmapper.IdMaxEnvioActasRowMapper;
import mx.morena.persistencia.rowmapper.LongRowMapper;

@Repository
public class EnvioActasRepository implements IEnvioActasRepository {
	
	@Autowired
	private JdbcTemplate template;
	
	private String campos = " tipo_votacion,ruta_acta,id_casilla,registro_acta,tipo_acta,copia_resultados_gobernador,copia_resultados_diputado_local,"
							+ "copia_resultados_sindico,copia_resultados_diputado_federal,copia_resultados_presidente_municipal,tipo_mime,extension_acta";

	@Override
	public void save(EnvioActas actas) {
		String sql = " INSERT INTO app_envio_actas ( " + campos +" ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		
		template.update(sql, 
				new Object[] { actas.getTipoVotacion(), actas.getRutaActa(), actas.getIdCasilla(), actas.getRegistroActa(), 
						actas.getTipoActa(), actas.isCopiaRespuestaGobernador(), actas.isCopiaRespuestaDiputadoLocal(),
						actas.isCopiaRespuestaSindico(), actas.isCopiaRespuestaDiputadoFederal(), actas.isCopiaRespuestaPresidenteMunicipal(),
						actas.getTipoMime(), actas.getExtensionActa()});
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
		String sql = "SELECT id_acta, tipo_votacion,tipo_acta, ruta_acta, id_casilla, registro_acta FROM app_envio_actas where id_casilla = ?";
		try {
			return template.queryForObject(sql, new Object[] { idCasilla }, new int[] { Types.NUMERIC },
					new ActasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<EnvioActas>();
		}

	}

	@Override
	public Long validaDuplicidadActa(Long tipo, Long casilla,Long tipoActa) {
		String sql = "select count(*) from app_envio_actas ac "
				+ "where ac.tipo_votacion = ? and ac.id_casilla = ? and ac.tipo_acta = ? ";
		try {
			return template.queryForObject(sql, new Object[] { tipo, casilla, tipoActa }, new int[] { Types.NUMERIC, Types.NUMERIC , Types.NUMERIC },
					new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<EnvioActas> getActaByTipo(Long idTipoActa) {
		String sql = "select id_acta,tipo_votacion,ruta_acta,id_casilla,registro_acta, tipo_acta, copia_resultados_gobernador, copia_resultados_diputado_local, "
				+ "copia_resultados_sindico, copia_resultados_diputado_federal, copia_resultados_presidente_municipal, tipo_mime, extension_acta from app_envio_actas aea "
				+ "where tipo_acta = ?";
		try {
			return template.queryForObject(sql, new Object[] { idTipoActa }, new int[] { Types.NUMERIC },
					new ActasByTipoRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<EnvioActas>();
		}
	}

	@Override
	public List<EnvioActas> getBase64Byid(Long idTipoActa) {
		String sql = "select ruta_acta from app_envio_actas aea "
				+ "where id_acta = ?";
		try {
			return template.queryForObject(sql, new Object[] { idTipoActa }, new int[] { Types.NUMERIC },
					new ActasByIdRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<EnvioActas>();
		}
	}

}
