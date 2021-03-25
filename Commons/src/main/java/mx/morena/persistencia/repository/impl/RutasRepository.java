package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.Rutas;
import mx.morena.persistencia.repository.IRutasRepository;
import mx.morena.persistencia.rowmapper.RutaConsultaRowMapper;
import mx.morena.persistencia.rowmapper.RutaRowMapper;
import mx.morena.persistencia.rowmapper.RutasRowMapper;
import mx.morena.persistencia.rowmapper.ZonasByDfRowMapper;
import mx.morena.persistencia.rowmapper.CasillaByRutaRowMapper;
import mx.morena.persistencia.rowmapper.RepresentanteRowMapper;
import mx.morena.persistencia.rowmapper.RutaByZonaRowMapper;

@Repository
public class RutasRepository implements IRutasRepository {

	@Autowired
	private JdbcTemplate template;

	private String campos = " id, distrito_federal_id, nombre_distrito, zona_crg, id_zona_crg, ruta, id_casilla, tipo_casilla, seccion_id, id_ruta_rg, status, id_crg ";

	@Override
	public Representantes getAllCrg(Long tipo) {
		String sql = "SELECT nombre, apellido_paterno, apellido_materno, tipo_representante from app_representantes ar"
		+ "inner join app_perfil ap"
		+ "on ar.tipo_representante = ap.id"
		+ "where ar.tipo_representante = ? ";
		try {
			return template.queryForObject(sql, new Object[] { tipo }, new int[] { Types.NUMERIC },
					new RepresentanteRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> findByCrgId(Long crgId) {
		String sql = "select id as ruta_id, id_crg from app_rutas2 where id_crg = ?";
		try {
			return template.queryForObject(sql, new Object[] { crgId },
					new int[] { Types.NUMERIC }, new RutaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> findById(Long idRutas) {
		String sql = "SELECT id as ruta_id, id_crg, id, status FROM app_rutas2 where id = ?";
		try {
			return template.queryForObject(sql, new Object[] { idRutas }, new int[] { Types.NUMERIC },
					new RutaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void updateIdCrg(Long idRuta, Long idCrg) {
		String sql = "UPDATE app_rutas2 SET id_crg = ?, status = 01 WHERE id = ? and status != 1";

		template.update(sql, new Object[] { idCrg, idRuta }, new int[] { Types.NUMERIC, Types.NUMERIC });

		
	}

	@Override
	public List<Rutas> getRutas(Long idFederal, Long zonaCRG, Long ruta, Long casilla) {
		String select = "SELECT" + campos + " FROM app_rutas2 ";
		String select2 = "select distrito_federal_id , nombre_distrito ,"
				+" id_ruta_rg , id_zona_crg , ruta , seccion_id , zona_crg from app_rutas2 ";   
	    String groupBy = " group by distrito_federal_id , nombre_distrito , "
	    		+ "id_ruta_rg , id_zona_crg , ruta , seccion_id , zona_crg ";
		String sql = null;

		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		String where = "";

		if (idFederal != null) {
			where = " where distrito_federal_id = ? ";
			para.add(idFederal);
			type.add(Types.NUMERIC);
		}

		if (zonaCRG != null) {

			if (para.size() > 0) {
				where = where.concat(" and zona_crg = ? ");
			} else {
				where = " where zona_crg = ? ";
			}
			para.add(zonaCRG);
			type.add(Types.NUMERIC);
		}

		if (ruta != null) {
			if (para.size() > 0) {
				where = where.concat(" and ruta = ? ");
			} else {
				where = " where ruta = ? ";
			}
			para.add(ruta);
			type.add(Types.NUMERIC);
		}
		if (casilla != null) {

			if (para.size() > 0) {
				where = where.concat(" and id_casilla = ? ");
			} else {
				where = " where id_casilla = ? ";
			}
			para.add(casilla);
			type.add(Types.NUMERIC);
		}

		Object[] parametros = new Object[para.size()];
		int[] types = new int[para.size()];

		for (int i = 0; i < para.size(); i++) {
			parametros[i] = para.get(i);
			types[i] = type.get(i);
		}

		try {
			 sql = select2.concat(where);
			 sql = sql.concat(groupBy);

			return template.queryForObject(sql, parametros, types, new RutaConsultaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	
	/////////////////////////   catalogos
	
	@Override
	public List<Rutas> getZonasByDistrito(Long idDistrito) {
		String sql = "select distrito_federal_id, nombre_distrito, zona_crg, id_zona_crg from app_rutas2 ar "
				+ " where distrito_federal_id = ? group by distrito_federal_id, nombre_distrito, zona_crg, id_zona_crg order by zona_crg asc";
		try {
			return template.queryForObject(sql, new Object[] {idDistrito}, new int[] { Types.NUMERIC },
					new ZonasByDfRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> getRutasByZonas(Long zonaCrg) {
		String sql = "select ruta, id_ruta_rg, id_zona_crg from app_rutas2 ar where zona_crg = ?"
				+ " group by ruta, id_ruta_rg, id_zona_crg order by ruta asc ";
		try {
			return template.queryForObject(sql, new Object[] {zonaCrg}, new int[] { Types.NUMERIC },
					new RutaByZonaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> getCasillaByRuta(Long ruta) {
		String sql = "select id_casilla , id_ruta_rg, ruta, tipo_casilla from app_rutas2 ar where ruta =? group by id_ruta_rg, id_casilla, ruta, tipo_casilla";
		try {
			return template.queryForObject(sql, new Object[] {ruta}, new int[] { Types.NUMERIC },
					new CasillaByRutaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> getTipoCasilla(String idRutaRg, Long seccionId) {
		String sql = "select * from app_rutas2 ar where id_ruta_rg = ? and seccion_id = ? ";		try {
			return template.queryForObject(sql, new Object[] { idRutaRg, seccionId }, new int[] { Types.VARCHAR, Types.NUMERIC },
					new RutasRowMapper());
		}catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}


}
