package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.AsignacionCasillas;
import mx.morena.persistencia.entidad.Casilla;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.rowmapper.AsignacionCasillasRowMapper;
import mx.morena.persistencia.rowmapper.CasillasLocalesByFederalRowMapper;
import mx.morena.persistencia.rowmapper.CasillasRowMapper;
import mx.morena.persistencia.rowmapper.CountCasillasRowMapper;
import mx.morena.persistencia.rowmapper.DistritoLocalGroupRowMapper;
import mx.morena.persistencia.rowmapper.StringRowMapper;

@Repository
public class CasillaRepository implements ICasillaRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public List<Casilla> getCasillas(Long entidad) {

		String sql = "SELECT id, entidad_id, federal_id, local_id, municpio_id, seccion_id, tipo_casilla, tipologia, tipo_domicilio, calle, numero, colonia, cp, ubicacion, referencia, is_asignada  FROM app_casilla"
				+ " where entidad_id = ? ";

		return template.queryForObject(sql,new Object[] { entidad },  new int[] { Types.NUMERIC }, new CasillasRowMapper());
	}

	@Override
	public List<Casilla> getCasillas(Long entidad, Long federal) {
		String sql = "SELECT id, federal_id, local_id, municpio_id, seccion_id, tipo_casilla, tipologia, tipo_domicilio, calle, numero, colonia, cp, ubicacion, referencia, is_asignada  FROM app_casilla"
				+ " where federal_id = ? ";

		return template.queryForObject(sql, new Object[] { federal, }, new int[] { Types.NUMERIC },
				new CasillasRowMapper());

	}

	@Override
	public List<Casilla> getCasillas(Long entidad, Long federal, Long municipio) {
		String sql = "SELECT id, federal_id, local_id, municpio_id, seccion_id, tipo_casilla, tipologia, tipo_domicilio, calle, numero, colonia, cp, ubicacion, referencia, is_asignada  FROM app_casilla"
				+ " where federal_id = ? and municpio_id = ? ";

		return template.queryForObject(sql, new Object[] { federal, municipio },
				new int[] { Types.NUMERIC, Types.NUMERIC }, new CasillasRowMapper());

	}

	@Override
	public List<Casilla> getCasillasFederal(Long distritoFederalId) {
		String sql = "SELECT id, federal_id, local_id, municpio_id, seccion_id, tipo_casilla, tipologia, tipo_domicilio, calle, numero, colonia, cp, ubicacion, referencia, is_asignada  FROM app_casilla"
				+ " where federal_id = ? ";

		return template.queryForObject(sql, new Object[] { distritoFederalId, }, new int[] { Types.NUMERIC },
				new CasillasRowMapper());
	}

	@Override
	public List<Casilla> getCasillasMunicipio(Long municipioId) {
		String sql = "SELECT id, federal_id, local_id, municpio_id, seccion_id, tipo_casilla, tipologia, tipo_domicilio, calle, numero, colonia, cp, ubicacion, referencia, is_asignada  FROM app_casilla"
				+ " where  municpio_id = ? ";

		return template.queryForObject(sql, new Object[] { municipioId }, new int[] { Types.NUMERIC },
				new CasillasRowMapper());
	}

	@Override
	public List<Casilla> getCasillasSeccion(Long seccionId) {
		String sql = "SELECT id, federal_id, local_id, municpio_id, seccion_id, tipo_casilla, tipologia, tipo_domicilio, calle, numero, colonia, cp, ubicacion, referencia, is_asignada FROM app_casilla"
				+ " where  seccion_id = ? ";

		return template.queryForObject(sql, new Object[] { seccionId }, new int[] { Types.NUMERIC },
				new CasillasRowMapper());
	}

	@Override
	public Long countByDistritoAndTipologia(Long distritoId, String tipologia) {
		String sql = "select count(*) from app_casilla ac where federal_id = ? and tipologia = ?";
		return template.queryForObject(sql, new Object[] {distritoId, tipologia}, new int[] {Types.NUMERIC, Types.VARCHAR},
				new CountCasillasRowMapper());
	}

	@Override
	public Long countByLocalAndTipologia(Long localId, String tipologia) {
		String sql = "select count(*) from app_casilla ac where local_id = ? and tipologia = ?";
		return template.queryForObject(sql, new Object[] {localId, tipologia}, new int[] {Types.NUMERIC, Types.VARCHAR},new CountCasillasRowMapper());
	}
	
	@Override
	public Long countByMunicipioAndTipologia(Long idMunicipio, String tipologia) {
		String sql = "select count(*) from app_casilla ac where ac.municpio_id = ? and ac.tipologia = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idMunicipio, tipologia}, new int[] { Types.NUMERIC, Types.VARCHAR },
					new CountCasillasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		
		}
	}

	@Override
	public List<AsignacionCasillas> getCasillasAsignadasByRuta(Long entidad, Long idDistritoF, Long idRuta) {
		
		String sql = " SELECT aac.id, aac.distrito_federal_id, aac.nombre_distrito, aac.zona_crg, aac.id_zona_crg, aac.ruta, aac.id_casilla, aac.tipo_casilla, " + 
				" aac.seccion_id, aac.status, aac.id_ruta_rg, aac.id_crg, COALESCE(aic.id, 0 ) isOpen  " + 
				" FROM app_asignacion_casillas aac left join app_instalacion_casilla aic on aac.id_casilla = aic.id_casilla " + 
				" where ruta = ? and ruta != 0 ";
		
		try {
			return template.queryForObject(sql, new Object[] { idRuta }, new int[] { Types.NUMERIC },
				new AsignacionCasillasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			
			return null;
		
		}
	}

	@Override
	public List<AsignacionCasillas> getCasillasAsignadasById(Long idCasilla) {
		String sql = " SELECT aac.id, aac.distrito_federal_id, aac.nombre_distrito, aac.zona_crg, aac.id_zona_crg, aac.ruta, aac.id_casilla, aac.tipo_casilla, aac.seccion_id, aac.status, id_ruta_rg, id_crg, COALESCE(aic.id, 0 ) isOpen " + 
				"FROM app_asignacion_casillas aac left join app_instalacion_casilla aic on aac.id_casilla = aic.id_casilla  " + 
				"where  aac.id_casilla = ? " 
				+ "and aac.ruta != 0 ";
		try {
			System.out.println(sql);
			return template.queryForObject(sql, new Object[] { idCasilla }, new int[] { Types.NUMERIC },
				new AsignacionCasillasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		
		}
	}

	@Override
	public List<Casilla> getCasillasById(Long idCasilla) {
		
		String sql = "SELECT id, entidad_id, federal_id, local_id, municpio_id, seccion_id, tipo_casilla, tipologia, tipo_domicilio, calle, numero, colonia, cp, ubicacion, referencia, is_asignada  FROM app_casilla"
				+ " where id = ? ";

		return template.queryForObject(sql,new Object[] { idCasilla },  new int[] { Types.NUMERIC }, new CasillasRowMapper());
		
	}

	@Override
	public String getTipoCasillasById(Long casillaId) {
		String sql = "select tipo_casilla from app_casilla ac where id = ?";
		try {
			return template.queryForObject(sql, new Object[] { casillaId }, new int[] { Types.NUMERIC },
					new StringRowMapper());
		}catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Casilla> getCasillasAsignadas(long idEstado, long idDistrito, long idMunicipio, long perfilUsuario) {
		String select = "select * from app_casilla  ";
		String order = " and is_asignada = true order by id asc ";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (perfilUsuario == 1) {
			where = "where entidad_id = ?";
			para.add(idEstado);
			type.add(Types.NUMERIC);
		}
		if (perfilUsuario == 2) {
			where = " where entidad_id = ? and federal_id = ? ";
			para.add(idEstado);
			type.add(Types.NUMERIC);
			para.add(idDistrito);
			type.add(Types.NUMERIC);
		}
		if (perfilUsuario == 4) {
			where = " where entidad_id = ? and municipio_id = ? ";
			para.add(idEstado);
			type.add(Types.NUMERIC);
			para.add(idMunicipio);
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
			sql = sql.concat(order);
			System.out.println("***** " + sql);
			return template.queryForObject(sql, parametros, types, new CasillasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Casilla> getLocalesByFederal(Long federal) {
		String sql = "select federal_id, local_id from app_casilla ac where  federal_id  = ? group by local_id, federal_id order by local_id asc ";
		return template.queryForObject(sql,new Object[] { federal },  new int[] { Types.NUMERIC }, new CasillasLocalesByFederalRowMapper());
	}

	@Override
	public List<Casilla> getAllDistritosLocales() {
		String sql = "select local_id from app_casilla ac group by local_id order by local_id asc ";
		try {
		return template.queryForObject(sql, new DistritoLocalGroupRowMapper());
		}catch (EmptyResultDataAccessException e) {
			return null;
		}
	
	}

}
