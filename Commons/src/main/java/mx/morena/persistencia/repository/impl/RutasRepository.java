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
import mx.morena.persistencia.rowmapper.RutasRowMapper;

@Repository
public class RutasRepository implements IRutasRepository {

	@Autowired
	private JdbcTemplate template;

	private String campos = " id, distrito_federal_id, nombre_distrito, zona_crg, id_zona_crg, ruta, id_casilla, tipo_casilla, seccion_id, id_ruta_rg, status, id_crg ";

	@Override
	public List<Representantes> getAllCrg(int tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rutas> findByCrgId(Long crgId, Long tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rutas getByIdAndEstatus(Long idCrg, char estatus, Long tipo) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public List<Rutas> findById(Long idRutas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateIdCrg(Long idRuta, Long idCrg) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Rutas> getRutas(Long idFederal, Long zonaCRG, Long ruta, Long casilla) {
		String select = "SELECT" + campos + " FROM app_rutas2 ";
		String sql = null;
		List<Rutas> rutas = null;

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
			 sql = select.concat(where);

			return template.queryForObject(sql, parametros, types, new RutasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public List<Rutas> getZonasByDistrito(Long idDistrito) {
		String sql = "select * from app_rutas2 ar where distrito_federal_id =?";
		try {
			return template.queryForObject(sql, new Object[] {idDistrito}, new int[] { Types.NUMERIC },
					new RutasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> getRutasByZonas(Long zonaCrg) {
		String sql = "select * from app_rutas2 ar where zona_crg =?";
		try {
			return template.queryForObject(sql, new Object[] {zonaCrg}, new int[] { Types.NUMERIC },
					new RutasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Rutas> getCasillaByRuta(Long ruta) {
		String sql = "select * from app_rutas2 ar where ruta = ?";
		try {
			return template.queryForObject(sql, new Object[] {ruta}, new int[] { Types.NUMERIC },
					new RutasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
