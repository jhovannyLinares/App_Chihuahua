package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.AsignacionCasillas;
import mx.morena.persistencia.repository.IAsignacionCasillasRepository;
import mx.morena.persistencia.rowmapper.AsignacionCasillaRowMapper;
import mx.morena.persistencia.rowmapper.ListAsignacionCasillaRowMapper;
import mx.morena.persistencia.rowmapper.LongRowMapper;

@Repository
public class AsignacionCasillasRepository implements IAsignacionCasillasRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public List<AsignacionCasillas> getRutasByIdCrg(long idCrg) {

		String sql = "select ruta, distrito_federal_id  from app_asignacion_casillas aac where id_crg = ? group by ruta, distrito_federal_id ";

		try {

			return template.queryForObject(sql, new Object[] { idCrg }, new int[] { Types.NUMERIC },
					new AsignacionCasillaRowMapper());

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<AsignacionCasillas> getRutasByFederal(Long idFederal) {
		String sql = "select ruta, distrito_federal_id from app_asignacion_casillas aac where distrito_federal_id = ? " 
				+ "group by ruta, distrito_federal_id order by ruta, distrito_federal_id asc";

		try {

			return template.queryForObject(sql, new Object[] { idFederal }, new int[] { Types.NUMERIC },
					new AsignacionCasillaRowMapper());

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Long countCasillasByIdCrgAndRuta(Long idCrg, Long casillaRuta, Long tipo, Long idFederal, Long municipio) {
		String select ="select count(*) from app_asignacion_casillas aac  ";
		
		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		
		if (tipo == 1L) {
			where = " where id_crg = ? and ruta = ? ";
			para.add(idCrg);
			type.add(Types.NUMERIC);
			para.add(casillaRuta);
			type.add(Types.NUMERIC);
			
		}
		
		if (tipo == 2L) {
			where = " where distrito_federal_id = ? and ruta = ?";
			para.add(idFederal);
			type.add(Types.NUMERIC);
			para.add(casillaRuta);
			type.add(Types.NUMERIC);
		}
		
		if (tipo == 3L) {
			where = " where distrito_federal_id = ? and ruta = ?";
			para.add(idFederal);
			type.add(Types.NUMERIC);
			para.add(casillaRuta);
			type.add(Types.NUMERIC);
		}
		
		if (tipo == 4L) {
			where = " inner join app_casilla ac "
					+"on ac.id = aac.id_casilla " 
					+"where ac.municpio_id = ? and ac.federal_id = ?";
			para.add(municipio);
			type.add(Types.NUMERIC);
			para.add(idFederal);
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

			return template.queryForObject(sql, parametros, types ,new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<AsignacionCasillas> getTipoCasillasByRutaRg(String idRutaRg) {
		String sql = "select * from app_asignacion_casillas aac where id_ruta_rg = ?";
		try {
			return template.queryForObject(sql, new Object[] { idRutaRg }, new int[] { Types.VARCHAR }, new ListAsignacionCasillaRowMapper() );
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<AsignacionCasillas> getAll() {
		
		String sql = "select ruta, distrito_federal_id from app_asignacion_casillas aac "
					+"group by ruta, distrito_federal_id order by ruta, distrito_federal_id asc";
		try {

			return template.queryForObject(sql,	new AsignacionCasillaRowMapper());

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<AsignacionCasillas> getRutasByIdFederal(Long idFederal) {

		String sql = "select * from app_asignacion_casillas aac where distrito_federal_id = ? order by seccion_id";
		try {
			return template.queryForObject(sql, new Object[] { idFederal }, new int[] { Types.NUMERIC },
					new ListAsignacionCasillaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
}
