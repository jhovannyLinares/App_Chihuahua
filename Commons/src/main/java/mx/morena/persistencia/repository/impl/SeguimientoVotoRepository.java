package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.Rutas;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.ISeguimientoVotoRepository;
import mx.morena.persistencia.rowmapper.IdMaxConvencidos;
import mx.morena.persistencia.rowmapper.LongRowMapper;
import mx.morena.persistencia.rowmapper.RutaSeguimietoRowMapper;
import mx.morena.persistencia.rowmapper.SeccionCotsRowMapper;
import mx.morena.persistencia.rowmapper.SeccionSeguimientoRowMapper;
import mx.morena.persistencia.rowmapper.SeguimientosVotoRowMapper;
import mx.morena.persistencia.rowmapper.StringRowMapper;

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
				+ "from public.app_convencidos ac " + "inner join app_secciones as2 "
				+ "on ac.seccion_id = as2.id inner join app_casilla ac2 on as2.id = ac2.id " + "where as2.id = ? "
				+ "and ac.tipo = 1 ";
		try {
			return template.queryForObject(sql, new Object[] { idSeccion }, new int[] { Types.NUMERIC },
					new SeguimientosVotoRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	// -------------Reporte-----------------------------------------------

	@Override
	public Long getCasillasByDistrito(Long idDistritoFederal) {

		String sql = "select COUNT(*) from app_casilla ac where ac.federal_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoFederal }, new int[] { Types.NUMERIC },
				new LongRowMapper());

	}

	@Override
	public Long getCasillasInstaladas1(Long idDistritoFederal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '07:00:00' And '07:30:00' and ac.federal_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoFederal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas2(Long idDistritoFederal) {

		String sql = "select count(*) from app_instalacion_casilla aic " 
				+ "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '07:31:00' And '08:00:00' and ac.federal_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoFederal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas3(Long idDistritoFederal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '08:01:00' And '08:30:00' and ac.federal_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoFederal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas4(Long idDistritoFederal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '08:31:00' And '09:00:00' and ac.federal_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoFederal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas5(Long idDistritoFederal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '09:01:00' And '09:59:59' and ac.federal_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoFederal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas6(Long idDistritoFederal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '10:00:00' And '18:00:00' and ac.federal_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoFederal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getTotalCasillasInstaladas(Long idDistritoFederal) {

		String sql = "select count(*) from app_instalacion_casilla aic " 
				+ "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id " 
				+ "where ac.federal_id = ?";
		return template.queryForObject(sql, new Object[] { idDistritoFederal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasByLocal(Long idDistritoLocal) {

		String sql = "select COUNT(*) from app_casilla ac where ac.local_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoLocal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas1L(Long idDistritoFederal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '07:00:00' And '07:30:00' and ac.local_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoFederal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas2L(Long idDistritoLocal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '07:30:01' And '08:00:00' and ac.local_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoLocal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas3L(Long idDistritoLocal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '08:00:01' And '08:30:59' and ac.local_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoLocal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas4L(Long idDistritoLocal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '08:31:00' And '09:00:00' and ac.local_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoLocal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas5L(Long idDistritoLocal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '09:00:01' And '09:59:59' and ac.local_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoLocal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas6L(Long idDistritoLocal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '10:00:00' And '18:00:00' and ac.local_id = ? ";
		return template.queryForObject(sql, new Object[] { idDistritoLocal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getTotalCasillasInstaladasL(Long idDistritoFederal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id " + "where ac.local_id = ?";
		return template.queryForObject(sql, new Object[] { idDistritoFederal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasByMunicipal(Long idMunicipal) {

		String sql = "select COUNT(*) from app_casilla ac where ac.municpio_id  = ? ";
		return template.queryForObject(sql, new Object[] { idMunicipal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas1M(Long idMunicipal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '07:00:00' And '07:30:00' and ac.municpio_id = ? ";
		return template.queryForObject(sql, new Object[] { idMunicipal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas2M(Long idMunicipal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '07:30:01' And '08:00:00' and ac.municpio_id = ? ";
		return template.queryForObject(sql, new Object[] { idMunicipal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas3M(Long idMunicipal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '08:00:01' And '08:30:59' and ac.municpio_id = ? ";
		return template.queryForObject(sql, new Object[] { idMunicipal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas4M(Long idMunicipal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '08:31:00' And '09:00:00' and ac.municpio_id = ? ";
		return template.queryForObject(sql, new Object[] { idMunicipal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas5M(Long idMunicipal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '09:00:01' And '09:59:59' and ac.municpio_id  = ? ";
		return template.queryForObject(sql, new Object[] { idMunicipal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas6M(Long idMunicipal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "where hora_instalacion Between '10:00:00' And '18:00:00' and ac.municpio_id = ? ";
		return template.queryForObject(sql, new Object[] { idMunicipal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getTotalCasillasInstaladasM(Long idMunicipal) {

		String sql = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id " + "where ac.municpio_id  = ?";
		return template.queryForObject(sql, new Object[] { idMunicipal }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public String getNombreMunicipio(Long idMunicipal) {

		String sql = "select nombre from app_municipio am where id = ? group by nombre ";
		return template.queryForObject(sql, new Object[] { idMunicipal }, new int[] { Types.NUMERIC },
				new StringRowMapper());
	}

	@Override
	public Long getCasillasByDistritoFederal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal) {

		String select = "select count(*) from app_casilla ac " 
				+ "inner join app_asignacion_casillas aac "
				+ "on ac.id = aac.id_casilla";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (idDistritoFederal != null) {
			where = " where federal_id = ? ";
			para.add(idDistritoFederal);
			type.add(Types.NUMERIC);
		}

		if (idDistritoLocal != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
			} else {
				where = " where ac.local_id = ? ";
			}
			para.add(idDistritoLocal);
			type.add(Types.NUMERIC);
		}

		if (idMunicipal != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.municpio_id = ? ");
			} else {
				where = " where ac.municpio_id = ? ";
			}
			para.add(idMunicipal);
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

			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public Long getCasillasInstaladas1Federal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal) {

		String select = "select count(*) from app_instalacion_casilla aic " 
				+ "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id " 
				+ "inner join app_asignacion_casillas aac "
				+ "on ac.id = aac.id_casilla "
				+ "where hora_instalacion Between '07:00:00' And '07:30:00'";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (idDistritoFederal != null) {
			where = " and federal_id = ? ";
			para.add(idDistritoFederal);
			type.add(Types.NUMERIC);
		}

		if (idDistritoLocal != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
			} else {
				where = " and ac.local_id = ? ";
			}
			para.add(idDistritoLocal);
			type.add(Types.NUMERIC);
		}

		if (idMunicipal != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.municpio_id = ? ");
			} else {
				where = " and ac.municpio_id = ? ";
			}
			para.add(idMunicipal);
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

			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public Long getCasillasInstaladas2Federal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal) {

		String select = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id " + "inner join app_asignacion_casillas aac "
				+ "on ac.id = aac.id_casilla "
				+ "where hora_instalacion Between '07:31:00' And '08:00:00'";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (idDistritoFederal != null) {
			where = " and federal_id = ? ";
			para.add(idDistritoFederal);
			type.add(Types.NUMERIC);
		}

		if (idDistritoLocal != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
			} else {
				where = " and ac.local_id = ? ";
			}
			para.add(idDistritoLocal);
			type.add(Types.NUMERIC);
		}

		if (idMunicipal != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.municpio_id = ? ");
			} else {
				where = " and ac.municpio_id = ? ";
			}
			para.add(idMunicipal);
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

			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public Long getCasillasInstaladas3Federal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal) {

		String select = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id " + "inner join app_asignacion_casillas aac "
				+ "on ac.id = aac.id_casilla "
				+ "where hora_instalacion Between '08:01:00' And '08:30:00' ";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (idDistritoFederal != null) {
			where = " and federal_id = ? ";
			para.add(idDistritoFederal);
			type.add(Types.NUMERIC);
		}

		if (idDistritoLocal != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
			} else {
				where = " and ac.local_id = ? ";
			}
			para.add(idDistritoLocal);
			type.add(Types.NUMERIC);
		}

		if (idMunicipal != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.municpio_id = ? ");
			} else {
				where = " and ac.municpio_id = ? ";
			}
			para.add(idMunicipal);
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

			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

//	public Long getCasillasInstaladas3Federal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal,
//			Long idRuta, Long idCasilla, Long hora) {
//		String select = "select count(*) from app_instalacion_casilla aic " 
//				+ "inner join app_casilla ac "
//				+ "on aic.id_casilla = ac.id " 
//				+ "inner join app_asignacion_casillas aac "
//				+ "on ac.federal_id = aac.distrito_federal_id ";
//
//		if (hora == 1)
//			select = select.concat(" where hora_instalacion Between '07:01:00' And '07:30:00' ");
//		if (hora == 2)
//			select = select.concat(" where hora_instalacion Between '07:31:00' And '08:00:00' ");
//		if (hora == 3)
//			select = select.concat(" where hora_instalacion Between '08:01:00' And '08:30:00' ");
//		if (hora == 4)
//			select = select.concat(" where hora_instalacion Between '08:31:00' And '09:00:00' ");
//		if (hora == 5)
//			select = select.concat(" where hora_instalacion Between '09:01:00' And '10:00:01' ");
//		if (hora == 6)
//			select = select.concat(" where hora_instalacion Between '10:01:00' And '18:00:00' ");
//		
//
//		String sql = null;
//		String where = "";
//		List<Object> para = new ArrayList<Object>();
//		List<Integer> type = new ArrayList<Integer>();
//		if (idDistritoFederal != null) {
//			where = " and federal_id = ? ";
//			para.add(idDistritoFederal);
//			type.add(Types.NUMERIC);
//		}
//		if (idDistritoLocal != null) {
//			if (para.size() > 0) {
//				where = where.concat(" and ac.local_id = ? ");
//			} else {
//				where = " and ac.local_id = ? ";
//			}
//			para.add(idDistritoLocal);
//			type.add(Types.NUMERIC);
//		}
//		if (idMunicipal != null) {
//			if (para.size() > 0) {
//				where = where.concat(" and ac.municpio_id = ? ");
//			} else {
//				where = " and ac.municpio_id = ? ";
//			}
//			para.add(idMunicipal);
//			type.add(Types.NUMERIC);
//		}
//		if (idRuta != null) {
//			if (para.size() > 0) {
//				where = where.concat(" and aac.ruta = ? ");
//			} else {
//				where = " and aac.ruta = ? ";
//			}
//			para.add(idRuta);
//			type.add(Types.NUMERIC);
//		}
//		if (idCasilla != null) {
//			if (para.size() > 0) {
//				where = where.concat(" and aac.id_casilla = ? ");
//			} else {
//				where = " and aac.id_casilla = ? ";
//			}
//			para.add(idCasilla);
//			type.add(Types.NUMERIC);
//		}
//		Object[] parametros = new Object[para.size()];
//		int[] types = new int[para.size()];
//		for (int i = 0; i < para.size(); i++) {
//			parametros[i] = para.get(i);
//			types[i] = type.get(i);
//		}
//		try {
//			sql = select.concat(where);
//			return template.queryForObject(sql, parametros, types, new LongRowMapper());
//		} catch (EmptyResultDataAccessException e) {
//			return null;
//		}
//	}

	@Override
	public Long getCasillasInstaladas4Federal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal) {

		String select = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id " + "inner join app_asignacion_casillas aac "
				+ "on ac.id = aac.id_casilla " 
				+ "where hora_instalacion Between '08:31:00' And '09:00:00' ";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (idDistritoFederal != null) {
			where = " and federal_id = ? ";
			para.add(idDistritoFederal);
			type.add(Types.NUMERIC);
		}

		if (idDistritoLocal != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
			} else {
				where = " and ac.local_id = ? ";
			}
			para.add(idDistritoLocal);
			type.add(Types.NUMERIC);
		}

		if (idMunicipal != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.municpio_id = ? ");
			} else {
				where = " and ac.municpio_id = ? ";
			}
			para.add(idMunicipal);
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

			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public Long getCasillasInstaladas5Federal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal) {

		String select = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id " + "inner join app_asignacion_casillas aac "
				+ "on ac.id = aac.id_casilla "
				+ "where hora_instalacion Between '09:01:00' And '09:59:59'";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (idDistritoFederal != null) {
			where = " and federal_id = ? ";
			para.add(idDistritoFederal);
			type.add(Types.NUMERIC);
		}

		if (idDistritoLocal != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
			} else {
				where = " and ac.local_id = ? ";
			}
			para.add(idDistritoLocal);
			type.add(Types.NUMERIC);
		}

		if (idMunicipal != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.municpio_id = ? ");
			} else {
				where = " and ac.municpio_id = ? ";
			}
			para.add(idMunicipal);
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

			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public Long getCasillasInstaladas6Federal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal) {

		String select = "select count(*) from app_instalacion_casilla aic " + "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id " + "inner join app_asignacion_casillas aac "
				+ "on ac.id = aac.id_casilla "
				+ "where hora_instalacion Between '10:00:00' And '18:00:00'";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (idDistritoFederal != null) {
			where = " and federal_id = ? ";
			para.add(idDistritoFederal);
			type.add(Types.NUMERIC);
		}

		if (idDistritoLocal != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
			} else {
				where = " and ac.local_id = ? ";
			}
			para.add(idDistritoLocal);
			type.add(Types.NUMERIC);
		}

		if (idMunicipal != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.municpio_id = ? ");
			} else {
				where = " and ac.municpio_id = ? ";
			}
			para.add(idMunicipal);
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

			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public Long getTotalCasillasInstaladasFederal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal) {

		String select = "select count(*) from app_instalacion_casilla aic " 
				+ " inner join app_casilla ac "
				+ "	on aic.id_casilla = ac.id " 
				+ "inner join app_asignacion_casillas aac "
				+ " on ac.id = aac.id_casilla ";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (idDistritoFederal != null) {
			where = " where federal_id = ? ";
			para.add(idDistritoFederal);
			type.add(Types.NUMERIC);
		}

		if (idDistritoLocal != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
			} else {
				where = " where ac.local_id = ? ";
			}
			para.add(idDistritoLocal);
			type.add(Types.NUMERIC);
		}

		if (idMunicipal != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.municpio_id = ? ");
			} else {
				where = " where ac.municpio_id = ? ";
			}
			para.add(idMunicipal);
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

			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> findByFederal(Long idFederal) {
		
		String sql = "select ar.ruta from app_rutas ar "
				+ "inner join app_secciones as3 "
				+ "on cast(ar.seccion_id as INTEGER) = as3.id "
				+ "where ar.distrito_federal_id = ? group by ar.ruta ";

		return template.queryForObject(sql, new Object[] { idFederal }, new int[] { Types.NUMERIC }, new RutaSeguimietoRowMapper());

	}

	@Override
	public Long getCasillasByRuta(Long ruta) {
		
		String sql = "select count(*) from app_casilla ac "
				+ "inner join app_secciones as2 "
				+ "on ac.seccion_id = as2.id "
				+ "left join app_rutas ar "
				+ "on cast(ar.seccion_id as INTEGER) = as2.id "
				+ "where ar.ruta = ?";
		return template.queryForObject(sql, new Object[] { ruta }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public List<SeccionElectoral> getSeccionByDistrito(Long df) {
		
		String sql = "select ar.seccion_id from app_rutas ar "
				+ "inner join app_secciones as3 "
				+ "on cast(ar.seccion_id as INTEGER) = as3.id "
				+ "where ar.distrito_federal_id = ?";
		return template.queryForObject(sql, new Object[] { df }, new int[] { Types.NUMERIC },
				new SeccionSeguimientoRowMapper());
	}

	@Override
	public String getTipoCasilla(Long seccion) {
		
		String sql = "select tipologia from app_casilla ac inner join app_secciones as2 on ac.seccion_id = as2.id "
				+ "left join app_rutas ar on cast(ar.seccion_id as INTEGER) = as2.id where ac.seccion_id = ? group by tipologia ";
		return template.queryForObject(sql, new Object[] { seccion }, new int[] { Types.NUMERIC },
				new StringRowMapper());
	}

	@Override
	public Long getIdLocal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal) {
		
		String select = "select count(*) from app_instalacion_casilla aic " 
				+ " inner join app_casilla ac "
				+ "	on aic.id_casilla = ac.id " 
				+ "inner join app_asignacion_casillas aac "
				+ " on ac.id = aac.id_casilla ";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (idDistritoFederal != null) {
			where = " where federal_id = ? ";
			para.add(idDistritoFederal);
			type.add(Types.NUMERIC);
		}

		if (idDistritoLocal != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
			} else {
				where = " where ac.local_id = ? ";
			}
			para.add(idDistritoLocal);
			type.add(Types.NUMERIC);
		}

		if (idMunicipal != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.municpio_id = ? ");
			} else {
				where = " where ac.municpio_id = ? ";
			}
			para.add(idMunicipal);
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

			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}

	@Override
	public Long getIdFederal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal) {

		String select = "select  from app_instalacion_casilla aic " 
				+ " inner join app_casilla ac "
				+ "	on aic.id_casilla = ac.id " 
				+ "inner join app_asignacion_casillas aac "
				+ " on ac.id = aac.id_casilla ";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (idDistritoFederal != null) {
			where = " where federal_id = ? ";
			para.add(idDistritoFederal);
			type.add(Types.NUMERIC);
		}

		if (idDistritoLocal != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
			} else {
				where = " where ac.local_id = ? ";
			}
			para.add(idDistritoLocal);
			type.add(Types.NUMERIC);
		}

		if (idMunicipal != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.municpio_id = ? ");
			} else {
				where = " where ac.municpio_id = ? ";
			}
			para.add(idMunicipal);
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

			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}

	@Override
	public Long getIdMunicipal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal) {
		
		String select = "select count(*) from app_instalacion_casilla aic " 
				+ " inner join app_casilla ac "
				+ "	on aic.id_casilla = ac.id " 
				+ "inner join app_asignacion_casillas aac "
				+ " on ac.id = aac.id_casilla ";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (idDistritoFederal != null) {
			where = " where federal_id = ? ";
			para.add(idDistritoFederal);
			type.add(Types.NUMERIC);
		}

		if (idDistritoLocal != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
			} else {
				where = " where ac.local_id = ? ";
			}
			para.add(idDistritoLocal);
			type.add(Types.NUMERIC);
		}

		if (idMunicipal != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.municpio_id = ? ");
			} else {
				where = " where ac.municpio_id = ? ";
			}
			para.add(idMunicipal);
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

			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}

	@Override
	public String getNomMunicipal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal) {
		
		String select = "select count(*) from app_instalacion_casilla aic " 
				+ " inner join app_casilla ac "
				+ "	on aic.id_casilla = ac.id " 
				+ "inner join app_asignacion_casillas aac "
				+ " on ac.id = aac.id_casilla ";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (idDistritoFederal != null) {
			where = " where federal_id = ? ";
			para.add(idDistritoFederal);
			type.add(Types.NUMERIC);
		}

		if (idDistritoLocal != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
			} else {
				where = " where ac.local_id = ? ";
			}
			para.add(idDistritoLocal);
			type.add(Types.NUMERIC);
		}

		if (idMunicipal != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.municpio_id = ? ");
			} else {
				where = " where ac.municpio_id = ? ";
			}
			para.add(idMunicipal);
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

			return template.queryForObject(sql, parametros, types, new StringRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}

	@Override
	public Long getCasillasInstaladas1Crg(Long idRuta) {
		
		String sql = "select COUNT(*) from app_instalacion_casilla aic "
				+ "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "inner join app_secciones as2 "
				+ "on ac.seccion_id = as2.id "
				+ "inner join app_rutas ar "
				+ "on as2.id = cast(ar.seccion_id as INTEGER) "
				+ "where hora_instalacion Between '07:00:00' And '07:30:00' and ar.id = ?";
		return template.queryForObject(sql, new Object[] { idRuta }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas2Crg(Long idRuta) {
		
		String sql = "select count(*) from app_instalacion_casilla aic "
				+ "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "inner join app_secciones as2 "
				+ "on ac.seccion_id = as2.id "
				+ "inner join app_rutas ar "
				+ "on as2.id = cast(ar.seccion_id as INTEGER) "
				+ "where hora_instalacion Between '07:31:00' And '08:00:00' and ar.id = ? ";
		return template.queryForObject(sql, new Object[] { idRuta }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas3Crg(Long idRuta) {
		
		String sql = "select count(*) from app_instalacion_casilla aic "
				+ "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "inner join app_secciones as2 "
				+ "on ac.seccion_id = as2.id "
				+ "inner join app_rutas ar "
				+ "on as2.id = cast(ar.seccion_id as INTEGER)"
				+ "where hora_instalacion Between '08:01:00' And '08:30:00' and ar.id = ? ";
		return template.queryForObject(sql, new Object[] { idRuta }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas4Crg(Long idRuta) {
		
		String sql = "select count(*) from app_instalacion_casilla aic "
				+ "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "inner join app_secciones as2 "
				+ "on ac.seccion_id = as2.id "
				+ "inner join app_rutas ar "
				+ "on as2.id = cast(ar.seccion_id as INTEGER)"
				+ "where hora_instalacion Between '08:31:00' And '09:00:00' and ar.id = ? ";
		return template.queryForObject(sql, new Object[] { idRuta }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas5Crg(Long idRuta) {
		
		String sql = "select count(*) from app_instalacion_casilla aic "
				+ "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "inner join app_secciones as2 "
				+ "on ac.seccion_id = as2.id "
				+ "inner join app_rutas ar "
				+ "on as2.id = cast(ar.seccion_id as INTEGER)"
				+ "where hora_instalacion Between '09:01:00' And '09:59:59' and ar.id = ? ";
		return template.queryForObject(sql, new Object[] { idRuta }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getCasillasInstaladas6Crg(Long idRuta) {
		
		String sql = "select count(*) from app_instalacion_casilla aic "
				+ "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "inner join app_secciones as2 "
				+ "on ac.seccion_id = as2.id "
				+ "inner join app_rutas ar "
				+ "on as2.id = cast(ar.seccion_id as INTEGER)"
				+ "where hora_instalacion Between '10:00:00' And '18:00:00' and ar.id = ? ";
		return template.queryForObject(sql, new Object[] { idRuta }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

	@Override
	public Long getTotalCasillasInstaladasCrg(Long idRuta) {
		String sql = "select count(*) from app_instalacion_casilla aic "
				+ "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id "
				+ "inner join app_secciones as2 "
				+ "on ac.seccion_id = as2.id "
				+ "inner join app_rutas ar "
				+ "on as2.id = cast(ar.seccion_id as INTEGER) "
				+ "where ar.id = ?";
		return template.queryForObject(sql, new Object[] { idRuta }, new int[] { Types.NUMERIC },
				new LongRowMapper());
	}

}