package mx.morena.persistencia.repository.impl;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.ReporteCasilla;
import mx.morena.persistencia.repository.IReporteCasillasRepository;
import mx.morena.persistencia.rowmapper.ConvencidosRowMapper;
import mx.morena.persistencia.rowmapper.LongRowMapper;

@Repository
public class ReporteCasillaRepository implements IReporteCasillasRepository {
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public int save(ReporteCasilla rc) {
		String sql = "insert into app_reporte_casillas (id, id_casilla, hora_reporte, id_rg, numero_votos)" 
				+ "values ((SELECT MAX(id)+1 FROM app_reporte_casillas), ?, ?, ?, ?);";
		try {
			template.update(sql, new Object[] {rc.getIdCasilla(), rc.getHoraReporte(), rc.getIdRg(), rc.getNumeroVotos()});
			return 1;
		} catch (Exception e) {
			System.out.println(e);
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
	public Long getReporteByCAsilla(Long idCasilla) {
		String sql = "select id from app_reporte_casillas arc where id_casilla = ? ";

		try {
			return template.queryForObject(sql, new Object[] { idCasilla },
					new int[] { Types.NUMERIC}, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return 0L;
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

}
