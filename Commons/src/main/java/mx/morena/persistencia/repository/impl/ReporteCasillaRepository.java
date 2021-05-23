package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.ActasVotos;
import mx.morena.persistencia.entidad.AfluenciaVotos;
import mx.morena.persistencia.entidad.EstadoVotacion;
import mx.morena.persistencia.entidad.ReporteCasilla;
import mx.morena.persistencia.repository.IReporteCasillasRepository;
import mx.morena.persistencia.rowmapper.ActasVotosRowMapper;
import mx.morena.persistencia.rowmapper.AfluenciaVotoRowMapper;
import mx.morena.persistencia.rowmapper.EstadoVotacionRowMapper;
import mx.morena.persistencia.rowmapper.LongRowMapper;
import mx.morena.persistencia.rowmapper.ReporteCasillaResponseRowMapper;
import mx.morena.persistencia.rowmapper.ReporteCasillaRowMapper;

@Repository
public class ReporteCasillaRepository implements IReporteCasillasRepository {
	
	Logger logger = LoggerFactory.getLogger(ReporteCasillaRepository.class);
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public int save(ReporteCasilla rc) {
		String sql = "insert into app_reporte_casillas (id, id_casilla, hora_reporte, numero_votos,tipo_reporte, cantidad_personas_han_votado, "
				+ "boletas_utilizadas, recibio_visita_representante, id_rc, id_motivo_cierre, is_cerrada, hora_cierre)" 
				+ "values (COALESCE((SELECT MAX(id) FROM app_reporte_casillas), 0)+1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//				+ "values ((SELECT MAX(id)+1 FROM app_reporte_casillas), ?, ?, ?, ?);";
		try {
			template.update(sql, new Object[] { rc.getIdCasilla(), rc.getHoraReporte(), rc.getNumeroVotos(),rc.getTipoReporte(),
			rc.getPersonasHanVotado(), rc.getBoletasUtilizadas(), rc.isRecibioVisitaRg(), rc.getIdRc(), rc.getIdMotivoCierre(),
			rc.isCerrada(), rc.getHoraCierre() });
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
		String sql = "select id, id_casilla, hora_reporte, numero_votos, hora_cierre, tipo_votacion, tipo_reporte from app_reporte_casillas arc where id_casilla = ? ";

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

	@Override
	public List<AfluenciaVotos> getAfluenciaByIdCasilla(Long idCasilla) {
		
		String sql = "SELECT id_casilla, hrs_12 , hrs_16 , hrs_18 "
				+ "FROM public.app_instalacion_casilla "
				+ "where id_casilla = ?";

		try {
			return template.queryForObject(sql, new Object[] { idCasilla },
					new int[] { Types.NUMERIC}, new AfluenciaVotoRowMapper());
		} catch (EmptyResultDataAccessException e) {
			
			return null;
		}
	}

	@Override
	public List<ActasVotos> getActasByIdCasilla(Long idCasilla) {
		
		String sql = "SELECT id_casilla, actas_gobernador , actas_federal , actas_local, actas_municipal, actas_sindico "
				+ "FROM public.app_instalacion_casilla "
				+ "where id_casilla = ?";

		try {
			return template.queryForObject(sql, new Object[] { idCasilla },
					new int[] { Types.NUMERIC}, new ActasVotosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			
			return null;
		}
	}

	@Override
	public int updateAfluenciaVotacion(AfluenciaVotos ev) {
		
		String sql = "update app_instalacion_casilla set hrs_12 = ?, hrs_16 = ?, hrs_18 = ? where id_casilla = ? ";

		try {
			template.update(sql, new Object[] { ev.getHrs12(), ev.getHrs16(), ev.getHrs18(), ev.getIdCasilla() },
					new int[] { Types.BOOLEAN, Types.BOOLEAN, Types.BOOLEAN, Types.NUMERIC });
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int updateActasVotacion(ActasVotos ev) {
		
		String sql = "update app_instalacion_casilla set actas_gobernador = ?, actas_federal = ?, actas_local = ?, actas_municipal = ?, actas_sindico = ?  where id_casilla = ? ";

		try {
			template.update(sql, new Object[] { ev.getGobernador(), ev.getDiputadoFedaral(), ev.getDiputadoLocal(), ev.getPresidenteMunicipal(), ev.getSindico(), ev.getIdCasilla() },
					new int[] { Types.BOOLEAN, Types.BOOLEAN, Types.BOOLEAN, Types.BOOLEAN, Types.BOOLEAN, Types.NUMERIC });
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<ReporteCasilla> getRegistrosByIdRc(Long idRc, Long idCasilla, Integer tipoReporte) {
		String select = "select id, id_casilla, hora_reporte, hora_cierre, tipo_reporte, cantidad_personas_han_votado, boletas_utilizadas, "
				+ "recibio_visita_representante, id_rc, id_motivo_cierre, is_cerrada "
				+ "from app_reporte_casillas ";
		
		String sql = null;
		String where = " where id_rc = ? and id_casilla = ? and is_cerrada = false";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		
		para.add(idRc);
		type.add(Types.NUMERIC);
		para.add(idCasilla);
		type.add(Types.NUMERIC);

		if (tipoReporte != null) {
			where = where.concat(" and tipo_reporte = ? ");
			para.add(tipoReporte);
			type.add(Types.NUMERIC);
		}
		
		Object[] parametros = new Object[para.size()];
		int[] types = new int[para.size()];

		for (int i = 0; i < para.size(); i++) {
			parametros[i] = para.get(i);
			types[i] = type.get(i);
		}

		try {
			sql = select.concat(where);

			return template.queryForObject(sql, parametros, types, new ReporteCasillaResponseRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<ReporteCasilla> getReporteByIdCasillaAndTipoRep(Long idCasilla, Integer tipoReporte) {
		String sql = "select * from app_reporte_casillas where id_casilla = ? and tipo_reporte = ? ";

		try {
			logger.debug(sql);
			return template.queryForObject(sql, new Object[] { idCasilla, tipoReporte },
					new int[] { Types.NUMERIC, Types.NUMERIC }, new ReporteCasillaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<ReporteCasilla> getCierreByIdCasilla(Long idCasilla) {
		String sql = "select id, id_casilla, hora_reporte, hora_cierre, tipo_reporte, cantidad_personas_han_votado, boletas_utilizadas, "
				+ " recibio_visita_representante, id_rc, id_motivo_cierre, is_cerrada from app_reporte_casillas arc where id_casilla = ? ";

		try {
			logger.debug(sql);
			return template.queryForObject(sql, new Object[] { idCasilla },
					new int[] { Types.NUMERIC}, new ReporteCasillaResponseRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<ReporteCasilla> getCierreByIdCasillaAndIsCerrada(Long idCasilla, boolean isCerrada) {
		String sql = "select id, id_casilla, hora_reporte, hora_cierre, tipo_reporte, cantidad_personas_han_votado, "
				+ "boletas_utilizadas, recibio_visita_representante, id_rc, id_motivo_cierre, is_cerrada "
				+ "from app_reporte_casillas arc where id_casilla = ? and is_cerrada = ? ";

		try {
			logger.debug(sql);
			return template.queryForObject(sql, new Object[] { idCasilla, isCerrada },
					new int[] { Types.NUMERIC, Types.BOOLEAN }, new ReporteCasillaResponseRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
}
