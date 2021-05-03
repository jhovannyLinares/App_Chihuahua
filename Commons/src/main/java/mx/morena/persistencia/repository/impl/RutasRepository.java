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
import mx.morena.persistencia.entidad.Zona;
import mx.morena.persistencia.repository.IRutasRepository;
import mx.morena.persistencia.rowmapper.CasillaByRutaRowMapper;
import mx.morena.persistencia.rowmapper.CasillaRowMapper;
import mx.morena.persistencia.rowmapper.RepresentanteRowMapper;
import mx.morena.persistencia.rowmapper.RutaByFederalRowMapper;
import mx.morena.persistencia.rowmapper.RutaByZonaRowMapper;
import mx.morena.persistencia.rowmapper.RutaConsultaRowMapper;
import mx.morena.persistencia.rowmapper.RutaRowMapper;
import mx.morena.persistencia.rowmapper.RutaTempRowMapper;
import mx.morena.persistencia.rowmapper.RutasRowMapper;
import mx.morena.persistencia.rowmapper.RutasTempRowMapper;
import mx.morena.persistencia.rowmapper.StringRowMapper;
import mx.morena.persistencia.rowmapper.ZonaRowMapper;
import mx.morena.persistencia.rowmapper.ZonasByDfRowMapper;

@Repository
public class RutasRepository implements IRutasRepository {

	@Autowired
	private JdbcTemplate template;

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
		String sql = "select id as ruta_id, id_crg from app_asignacion_casillas where id_crg = ?";
		try {
			return template.queryForObject(sql, new Object[] { crgId },
					new int[] { Types.NUMERIC }, new RutaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> findById(Long idRutas) {
		String sql = "SELECT id as ruta_id, id_crg, id, status FROM app_asignacion_casillas where id = ?";
		try {
			return template.queryForObject(sql, new Object[] { idRutas }, new int[] { Types.NUMERIC },
					new RutaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void updateIdCrg(Long idRuta, Long idCrg) {
		String sql = "UPDATE app_asignacion_casillas SET id_crg = ?, status = 01 WHERE id = ? and status != 1";

		template.update(sql, new Object[] { idCrg, idRuta }, new int[] { Types.NUMERIC, Types.NUMERIC });

		
	}

	@Override
	public List<Rutas> getRutas(Long idFederal, Long zonaCRG, Long ruta, Long casilla) {
		
		
		String select2 = "select distrito_federal_id , nombre_distrito ,"
				+" id_ruta_rg , id_zona_crg , ruta , seccion_id , zona_crg from app_asignacion_casillas ";   
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
	public List<Zona> getZonasByDistrito(Long idDistrito) {
		String sql = "select * from app_zonas where distrito_federal_id = ?";
		try {
			return template.queryForObject(sql, new Object[] {idDistrito}, new int[] { Types.NUMERIC },
					new ZonasByDfRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> getRutasByZonas(Long zona, String idzona) {
		String sql = "select MIN(id) id, distrito_federal_id, nombre_distrito, zona, id_zona, ruta, id_ruta_rg from app_rutas where zona = ? and id_zona = ? group by distrito_federal_id, nombre_distrito, zona, id_zona, ruta, id_ruta_rg ";
		try {
			return template.queryForObject(sql, new Object[] {zona,idzona}, new int[] { Types.NUMERIC ,Types.VARCHAR},
					new RutaByZonaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> getCasillaByRuta(String rutaRG) {
		String sql = "select id_casilla , id_ruta_rg, ruta, tipo_casilla from app_asignacion_casillas ar where id_ruta_rg =? group by id_ruta_rg, id_casilla, ruta, tipo_casilla";
		try {
			return template.queryForObject(sql, new Object[] {rutaRG}, new int[] { Types.VARCHAR },
					new CasillaByRutaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> getTipoCasilla(String idRutaRg, Long seccionId) {
		String sql = "select * from app_asignacion_casillas ar where id_ruta_rg = ? and seccion_id = ? ";		try {
			return template.queryForObject(sql, new Object[] { idRutaRg, seccionId }, new int[] { Types.VARCHAR, Types.NUMERIC },
					new RutasRowMapper());
		}catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}

	@Override
	public void asignarCasillas(Long idCasilla, Long idRuta,String ruta) {
		String sql = "UPDATE app_asignacion_casillas SET ruta = ? ,id_ruta_rg = ? WHERE id_casilla = ? AND ruta = 0";

		template.update(sql, new Object[] { idRuta, ruta, idCasilla }, new int[] { Types.NUMERIC, Types.VARCHAR, Types.NUMERIC  });
	}

	@Override
	public void desasignarCasillas(Long idCasilla) {
		String sql = "UPDATE app_asignacion_casillas SET ruta = 0, id_ruta_rg = null "
				+ "WHERE id_casilla = ? ";

		template.update(sql, new Object[] { idCasilla }, new int[] { Types.NUMERIC });
		
	}

	@Override
	public Rutas getCasillaByIdAndEstatus(Long idCasilla, int asignado) {
		String campo = "";

		if (asignado == 1) {
			campo = "ruta != 0";
		} else {
			campo = "id_ruta_rg IS NULL AND ruta = 0";
		}

		String sql = "SELECT id, distrito_federal_id, nombre_distrito, id_zona_crg, ruta, id_casilla, tipo_casilla, seccion_id, status, id_ruta_rg"
				+ " FROM app_asignacion_casillas WHERE id_casilla = ? AND " + campo;
		try {
			return template.queryForObject(sql, new Object[] { idCasilla }, new int[] { Types.NUMERIC },
					new CasillaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Rutas getRutaById(Long idRuta) {
		String sql = "SELECT id, distrito_federal_id, nombre_distrito, zona as zona_crg, id_zona as id_zona_crg, ruta, seccion_id, id_ruta_rg "
				+ " FROM app_rutas WHERE  id = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idRuta }, new int[] { Types.NUMERIC },
					new RutaTempRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void cambiarEstatusCasilla(Long idCasilla, int asignado) {
		String campo = "";

		if (asignado == 1) {
			campo = "false";
		} else {
			campo = "true";
		}

		String sql = "UPDATE app_casilla SET is_asignada = "+ campo + " where id = ? ";
		
		template.update(sql, new Object[] { idCasilla }, new int[] { Types.NUMERIC });
	}

	@Override
	public Zona getZonasByid(Long zonaCrg) {
		String sql = "select * from app_zonas where id = ?";
		try {
			return template.queryForObject(sql, new Object[] { zonaCrg }, new int[] { Types.NUMERIC },
					new ZonaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Rutas getRutaByid(Long rutaid) {
		String sql = "SELECT id, distrito_federal_id, nombre_distrito, zona as zona_crg, id_zona as id_zona_crg, ruta, seccion_id, id_ruta_rg FROM app_rutas where id = ?";
		try {
			return template.queryForObject(sql, new Object[] { rutaid }, new int[] { Types.NUMERIC },
					new RutaTempRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> getZonasByWhere(Long idFederal, Long zonaCRG, Long ruta, Long casilla) {
		String sql = "select min(ar.id) as id , min(ar.seccion_id) as seccion_id, ar.distrito_federal_id, ar.nombre_distrito, ar.zona as zona_crg, ar.id_zona as id_zona_crg, ar.ruta, ar.id_ruta_rg from app_rutas ar " + 
				"left join app_zonas az on az.id_zona_crg =ar.id_zona  " + 
				"left join app_asignacion_casillas aac on ar.id_ruta_rg =aac.id_ruta_rg and ar.ruta =aac.ruta";
		
		String where = "";
		
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		
		if (idFederal != null) {
			where = " where ar.distrito_federal_id = ? ";
			para.add(idFederal);
			type.add(Types.NUMERIC);
		}

		if (zonaCRG != null) {

			if (para.size() > 0) {
				where = where.concat(" and az.id = ? ");
			} else {
				where = " where az.id = ? ";
			}
			para.add(zonaCRG);
			type.add(Types.NUMERIC);
		}
		
		if (ruta != null) {

			if (para.size() > 0) {
				where = where.concat(" and ar.id = ? ");
			} else {
				where = " where ar.id = ? ";
			}
			para.add(ruta);
			type.add(Types.NUMERIC);
		}
		
		if (casilla != null) {

			if (para.size() > 0) {
				where = where.concat(" and aac.id_casilla = ? ");
			} else {
				where = " where aac.id_casilla = ? ";
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
			sql = sql.concat(where).concat(" group by ar.distrito_federal_id, ar.nombre_distrito, ar.zona , ar.id_zona , ar.ruta, ar.id_ruta_rg ");
			System.out.println(sql);
			
			return template.queryForObject(sql, parametros, types,
					new RutaConsultaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> getRutasByIdRutaRG(String idRutaRg) {
			String sql = "select id, distrito_federal_id, nombre_distrito, zona as zona_crg, id_zona as id_zona_crg, ruta, seccion_id, id_ruta_rg from app_rutas where id_ruta_rg =?";
	try {
		return template.queryForObject(sql, new Object[] { idRutaRg }, new int[] { Types.VARCHAR },
				new RutasTempRowMapper());
	} catch (EmptyResultDataAccessException e) {
		return null;
	}
	}

	@Override
	public String getIdRuraById(Long rutaId) {
		String sql = "select id_ruta_rg from app_rutas ar where id = ?";
		try {
			return template.queryForObject(sql, new Object[] { rutaId }, new int[] { Types.NUMERIC },
					new StringRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> getRutasByIdFederal(Long idFederal) {
		
		String sql = " select * from app_rutas ar where distrito_federal_id = ? ";
		
		try {
			return template.queryForObject(sql, new Object[] { idFederal }, new int[] { Types.NUMERIC },
					new RutaByFederalRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
