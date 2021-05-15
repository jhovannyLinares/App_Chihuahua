package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.EstadoVotacion;
import mx.morena.persistencia.entidad.ReporteCasilla;
import mx.morena.persistencia.repository.IReporteCasillasRepository;
import mx.morena.persistencia.rowmapper.EstadoVotacionRowMapper;
import mx.morena.persistencia.rowmapper.LongRowMapper;
import mx.morena.persistencia.rowmapper.ReporteCasillaRowMapper;

@Repository
public class ReporteCasillaRepository implements IReporteCasillasRepository {
	
	Logger logger = LoggerFactory.getLogger(ReporteCasillaRepository.class);
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public int save(ReporteCasilla rc) {
		String sql = "insert into app_reporte_casillas (id, id_casilla, hora_reporte, id_rg, numero_votos,tipo_reporte)" 
				+ "values (COALESCE((SELECT MAX(id) FROM app_reporte_casillas), 0)+1, ?, ?, ?, ?,?)";
//				+ "values ((SELECT MAX(id)+1 FROM app_reporte_casillas), ?, ?, ?, ?);";
		try {
			template.update(sql, new Object[] {rc.getIdCasilla(), rc.getHoraReporte(), rc.getIdRg(), rc.getNumeroVotos(),rc.getTipoReporte()});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateHoraCierre(ReporteCasilla rc) {
		String sql = "update app_reporte_casillas set hora_cierre = ?  where id_casilla = ? ";

		try {
			template.update(sql, new Object[] { rc.getHoraCierre(), rc.getIdCasilla() },
					new int[] { Types.TIMESTAMP, Types.NUMERIC });
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<ReporteCasilla> getReporteByIdCasilla(Long idCasilla) {
		String sql = "select id, id_casilla, hora_reporte, id_rg, numero_votos, hora_cierre, tipo_votacion, tipo_reporte from app_reporte_casillas arc where id_casilla = ? ";

		try {
			logger.debug(sql);
			return template.queryForObject(sql, new Object[] { idCasilla },
					new int[] { Types.NUMERIC}, new ReporteCasillaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			
			return null;
		}
	}

	@Override
	public Long getCountByDistritoAndTipoVotacion(Long idfederal, Long idReporte, String hora) {
		String sql = "select count(*) from app_reporte_casillas arc "
				+ "inner join app_casilla ac "
				+ "on ac.id = arc.id_casilla "
				+ "where  ac.federal_id = ? and arc.tipo_votacion = ? and arc.hora_reporte = ?";
		try {
			return template.queryForObject(sql, new Object[] { idfederal, idReporte, hora },
					new int[] {Types.NUMERIC, Types.NUMERIC, Types.TIME}, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return 0L;
		}
	}

	@Override
	public List<EstadoVotacion> getEstadoByIdCasilla(Long idCasilla) {
		
		String sql = "SELECT id_casilla, se_instalo, llego_rc, comenzo_votacion "
				+ "FROM public.app_instalacion_casilla "
				+ "where id_casilla = ?";

		try {
			return template.queryForObject(sql, new Object[] { idCasilla },
					new int[] { Types.NUMERIC}, new EstadoVotacionRowMapper());
		} catch (EmptyResultDataAccessException e) {
			
			return null;
		}
	}

	@Override
	public int updateEstadoVotacion(EstadoVotacion ev) {
		
		String sql = "update app_instalacion_casilla set se_instalo = ?, llego_rc = ?, comenzo_votacion = ?  where id_casilla = ?";

		try {
			template.update(sql, new Object[] { ev.getSeInstalo(), ev.getLlegoRc(), ev.getComenzoVotacion(), ev.getIdCasilla() },
					new int[] { Types.BOOLEAN, Types.VARCHAR, Types.BOOLEAN, Types.NUMERIC });
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int insertEstadoVotacion(EstadoVotacion ev) {
		String sql = "INSERT INTO app_validacion_instalacion_casilla "
				+ "(id, id_casilla, se_instalo, llego_rc, comenzo_votacion) " 
				+ "values (COALESCE((SELECT MAX(id) FROM app_validacion_instalacion_casilla), 0)+1, ?, ?, ?, ?)";
		try {
			template.update(sql, new Object[] {ev.getIdCasilla(), ev.getSeInstalo(), ev.getLlegoRc(), ev.getComenzoVotacion()});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
