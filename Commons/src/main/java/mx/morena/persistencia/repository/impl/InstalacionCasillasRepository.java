package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.InstalacionCasilla;
import mx.morena.persistencia.repository.IInstalacionCasillasRepository;
import mx.morena.persistencia.rowmapper.InstalacionRowMapper;
import mx.morena.persistencia.rowmapper.LongRowMapper;

@Repository
public class InstalacionCasillasRepository implements IInstalacionCasillasRepository{
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public int save(InstalacionCasilla ic) {
		String sql = "INSERT INTO app_instalacion_casilla (id, " + "id_casilla, " + "hora_instalacion, " + "llegaron_funcionarios, " + "funcionarios_de_fila, " 
				+ " paquete_completo, " + "llego_rg, " + "desayuno, " + "inicio_votacion," + "llego_rc) "
				+ " VALUES (COALESCE((SELECT MAX(id) FROM app_instalacion_casilla), 0)+1, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		try {
		template.update(sql, new Object[] {ic.getIdCasilla(), ic.getHoraInstalacion(), ic.getLlegaronFuncionarios(), ic.getFuncionariosFila(),
				 ic.getPaqueteCompleto(), ic.getLlegoRg(), ic.getDesayuno(), ic.getInicioVotacion(), ic.getLlegoRc()});
		return 1;
		} catch (Exception e) {
			return 0;
		}
	
	}

	@Override
	public Long getMaxId() {
		String sql = "SELECT MAX(id) FROM app_instalacion_casilla";
		return template.queryForObject(sql, new LongRowMapper());
	}

	@Override
	public List<InstalacionCasilla> getById(Long idCasilla) {

		String sql = " SELECT id, id_casilla, llegaron_funcionarios, funcionarios_de_fila, paquete_completo, llego_rg, desayuno, hora_instalacion, inicio_votacion, llego_rc "
				+ " FROM public.app_instalacion_casilla "
				+ " where id_casilla = ? ";
		try {
			
			return template.queryForObject(sql, new Object[] { idCasilla }, new int[] { Types.NUMERIC },
					new InstalacionRowMapper());

		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Long getCountRgByDfAndAsistencia(Long idFederal, String SI) {
		String sql = "select count(*) from app_instalacion_casilla aic " 
				+"inner join app_casilla ac "
				+"on aic.id_casilla = ac.id " 
				+"where ac.federal_id = ? and aic.llego_rg = ?";
		
		return template.queryForObject(sql, new Object[] { idFederal, SI }, new int[] { Types.NUMERIC, Types.VARCHAR }, new LongRowMapper());
	}

	@Override
	public Long getCountRcByDfAndAsistencia(Long idFederal, String SI) {
		String sql = "select count(*) from app_instalacion_casilla aic " 
					+ "inner join app_casilla ac "
					+ "on aic.id_casilla = ac.id " 
					+ "where ac.federal_id = ? and aic.llego_rc = ?";
		
		return template.queryForObject(sql, new Object[] { idFederal, SI }, new int[] { Types.NUMERIC, Types.VARCHAR },
				new LongRowMapper());
	}

	@Override
	public Long getCountRgByLocalAndAsistencia(Long local, String SI, Long idFederal, Long tipo) {
		String select = "select count(*) from app_instalacion_casilla aic " 
					+ "inner join app_casilla ac "
					+ "on ac.id = aic.id_casilla " ;
//					+ "where ac.local_id =  ? and aic.llego_rg = ?";
		
		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		
		if (tipo == 1L) {
			where = " where ac.local_id =  ? and aic.llego_rg = 'si' ";
			para.add(local);
//			para.add(SI);
			type.add(Types.NUMERIC);
//			type.add(Types.VARCHAR);
		}
		
		if (tipo == 2L || tipo == 3L) {
			where = " where ac.local_id =  ? and aic.llego_rg = 'si' and ac.federal_id = ? ";
			para.add(local);
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
	public Long getCountRcByLocalAndAsistencia(Long local, String SI, Long idFederal, Long tipo) {
		String select = "select count(*) from app_instalacion_casilla aic " 
					+ "inner join app_casilla ac "
					+ "on ac.id = aic.id_casilla " ;
//					+ "where ac.local_id =  ? and aic.llego_rc = ?";
		
		
		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		
		if (tipo == 1L) {
			where = " where ac.local_id =  ? and aic.llego_rc = 'si' ";
			para.add(local);
//			para.add(SI);
			type.add(Types.NUMERIC);
//			type.add(Types.VARCHAR);
		}
		
		if (tipo == 2L || tipo == 3L) {
			where = " where ac.local_id =  ? and aic.llego_rc = 'si' and ac.federal_id = ? ";
			para.add(local);
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

	
}
