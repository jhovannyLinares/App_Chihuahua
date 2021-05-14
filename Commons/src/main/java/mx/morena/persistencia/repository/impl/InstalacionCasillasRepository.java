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
import mx.morena.persistencia.rowmapper.GetInstalacionCasillaRowMapper;
import mx.morena.persistencia.rowmapper.InstalacionRowMapper;
import mx.morena.persistencia.rowmapper.LongRowMapper;

@Repository
public class InstalacionCasillasRepository implements IInstalacionCasillasRepository{
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public int save(InstalacionCasilla ic) {
		String sql = "INSERT INTO app_instalacion_casilla (id, " + "id_casilla, " + "hora_instalacion, " + "llegaron_funcionarios, " + "funcionarios_de_fila, " 
				+ " paquete_completo, " + "llego_rg, " + "desayuno, " + "inicio_votacion," + "llego_rc, "  //) "
				
				+ "instalo_casilla, "                       + "instalo_lugar_distinto, "             + "causa_lugar_distinto, " 
				+ "boletas_gobernador, "                    + "folio_inicial_gobernador, "           + "folio_final_gobernador, " 
				+ "boletas_diputado_local, "                + "folio_inicial_diputado_local, "       + "folio_final_diputado_local, " 
				+ "boletas_presidente_municipal, "          + "folio_inicial_presidente_municipal, " + "folio_final_presidente_municipal, " 
				+ "boletas_sindico, "                       + "folio_inicial_sindico, "              + "folio_final_sindico, "
				+ "boletas_diputado_federal, "              + "folio_inicial_diputado_federal, "     + "folio_final_diputado_federal, " 
				+ "sellaron_boletas, "                      + "id_partido_sello, " 
				+ "nombre_presidente, "                     + "nombre_secretario, "                  + "nombre_escrutador_1, " 
				+ "nombre_escrutador_2, "                   + "nombre_escrutador_3 )"
				
				+ " VALUES (COALESCE((SELECT MAX(id) FROM app_instalacion_casilla), 0)+1, ?, ?, ?, ?, ?, ?, ?, ?, ?, " // )";
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		try {
		template.update(sql, new Object[] {ic.getIdCasilla(), ic.getHoraInstalacion(), ic.getLlegaronFuncionarios(), ic.getFuncionariosFila(),
				 ic.getPaqueteCompleto(), ic.getLlegoRg(), ic.getDesayuno(), ic.getInicioVotacion(), ic.getLlegoRc(), //});
				 
		         ic.getInstaloCasilla(),             ic.getInstaloLugarDistinto(),        ic.getCausaLugarDistinto(),
		         ic.getBoletasGobernador(),          ic.getFolioIniGobernador(),          ic.getFolioFinGobernador(),
		         ic.getBoletasDipLocal(),            ic.getFolioIniDipLocal(),            ic.getFolioFinDipLocal(),
		         ic.getBoletasPresidenteMunicipal(), ic.getFolioIniPresidenteMunicipal(), ic.getFolioFinPresidenteMunicipal(),
		         ic.getBoletasSindico(),             ic.getFolioIniSindico(),             ic.getFolioFinSindico(),
		         ic.getBoletasDipFederal(),          ic.getFolioIniDipFederal(),          ic.getFolioFinDipFederal(),
		         ic.getSellaronBoletas(),            ic.getIdPartidoSello(),
		         ic.getNombrePresidente(),           ic.getNombreSecretario(),            ic.getNombreEscrutador1(),
		         ic.getNombreEscrutador2(),          ic.getNombreEscrutador3()});
		return 1;
		} catch (Exception e) {
//			System.out.println(e);
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
			e.printStackTrace();
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
	public Long getCountRgByLocalAndAsistencia(Long local, String SI, Long idFederal, Long tipo, Long idMunicipio) {
		String select = "select count(*) from app_instalacion_casilla aic " 
					+ "inner join app_casilla ac "
					+ "on ac.id = aic.id_casilla " ;
//					+ "where ac.local_id =  ? and aic.llego_rg = ?";
		
		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		
		if (tipo == 1L) {
			where = " where ac.local_id =  ? and aic.llego_rg = ? ";
			para.add(local);
			para.add(SI);
			type.add(Types.NUMERIC);
			type.add(Types.VARCHAR);
		}
		
		if (tipo == 2L) {
			where = " where ac.local_id =  ? and aic.llego_rg = ?  ";
			para.add(local);
			type.add(Types.NUMERIC);
			para.add(SI);
			type.add(Types.VARCHAR);
//			para.add(idFederal);
//			type.add(Types.NUMERIC);
			
		}
		
		if (tipo == 3L) {
			where = " where ac.local_id =  ? and aic.llego_rg = ?";
			para.add(local);
			type.add(Types.NUMERIC);
			para.add(SI);
			type.add(Types.VARCHAR);
//			para.add(idFederal);
//			type.add(Types.NUMERIC);
			
		}
		
		if (tipo == 4L) {
			where = "where aic.llego_rg = ? and ac.municpio_id = ? ";
			para.add(SI);
			type.add(Types.VARCHAR);
			para.add(idMunicipio);
			type.add(Types.NUMERIC);
		}
		
		if (tipo == 6L) {
			where = "where aic.llego_rg = ? and federal_id = ?";
			para.add(SI);
			type.add(Types.VARCHAR);
			para.add(idFederal);
			type.add(Types.NUMERIC);
		}
		
		if (tipo == 7L) {
			where = "where aic.llego_rg = ? and ac.municpio_id = ? and federal_id = ?";
			para.add(SI);
			type.add(Types.VARCHAR);
			para.add(idMunicipio);
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
	public Long getCountRcByLocalAndAsistencia(Long local, String SI, Long idFederal, Long tipo, Long idMunicipio) {
		String select = "select count(*) from app_instalacion_casilla aic " 
					+ "inner join app_casilla ac "
					+ "on ac.id = aic.id_casilla " ;
//					+ "where ac.local_id =  ? and aic.llego_rc = ?";
		
		
		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		
		if (tipo == 1L) {
			where = " where ac.local_id =  ? and aic.llego_rc = ? ";
			para.add(local);
			para.add(SI);
			type.add(Types.NUMERIC);
			type.add(Types.VARCHAR);
		}
		
		if (tipo == 2L) {
			where = " where ac.local_id =  ? and aic.llego_rc = ? ";
			para.add(local);
			type.add(Types.NUMERIC);
			para.add(SI);
			type.add(Types.VARCHAR);
//			para.add(idFederal);
//			type.add(Types.NUMERIC);
		}
		
		if (tipo == 3L) {
			where = " where ac.local_id =  ? and aic.llego_rc = ?  ";
			para.add(local);
			type.add(Types.NUMERIC);
			para.add(SI);
			type.add(Types.VARCHAR);
//			para.add(idFederal);
//			type.add(Types.NUMERIC);
		}
		
		if (tipo == 4L) {
			where = "where aic.llego_rc = ? and ac.municpio_id = ? ";
			para.add(SI);
			type.add(Types.VARCHAR);
			para.add(idMunicipio);
			type.add(Types.NUMERIC);
		}
		
		if (tipo == 6L) {
			where = "where aic.llego_rc = ? and federal_id = ?";
			para.add(SI);
			type.add(Types.VARCHAR);
			para.add(idFederal);
			type.add(Types.NUMERIC);
		}
		if (tipo == 7L) {
			where = "where aic.llego_rc = ? and ac.municpio_id = ? and federal_id = ?";
			para.add(SI);
			type.add(Types.VARCHAR);
			para.add(idMunicipio);
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
	public Long getCountRgByLocalAndAsistenciaCrg(String SI, Long idCrg, Long casillaRuta, Long tipo, Long federal, Long municipio) {
		String select = "select count(*) from app_instalacion_casilla aic " 
				+ "inner join app_asignacion_casillas aac "
				+ "on aic.id_casilla = aac.id_casilla " ;
//				+ "where aic.llego_rg = ? and aac.id_crg = ? and aac.ruta = ?";
		
		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		
		if (tipo == 1L) {
			where = " where aic.llego_rg = ? and aac.id_crg = ? and aac.ruta = ? ";
			
			para.add(SI);
			type.add(Types.VARCHAR);
			para.add(idCrg);
			type.add(Types.NUMERIC);
			para.add(casillaRuta);
			type.add(Types.NUMERIC);
		}

		if (tipo == 2L) {
			where = " where aic.llego_rg = ? and aac.distrito_federal_id = ? ";

			para.add(SI);
			type.add(Types.VARCHAR);
			para.add(federal);
			type.add(Types.NUMERIC);
		}

		if (tipo == 3L) {
			where = "where aic.llego_rg = ? and aac.ruta = ? and aac.distrito_federal_id = ?";
			para.add(SI);
			type.add(Types.VARCHAR);
//			para.add(idCrg);
//			type.add(Types.NUMERIC);
			para.add(casillaRuta);
			type.add(Types.NUMERIC);
			para.add(federal);
			type.add(Types.NUMERIC);
		}
		
		if (tipo == 4L) {
			where = "inner join app_casilla ac " 
					+"on ac.id = aic.id_casilla " 
					+"where aic.llego_rg = ? and ac.municpio_id = ? ";
			
			para.add(SI);
			type.add(Types.VARCHAR);
			para.add(municipio);
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
	public Long getCountRcByLocalAndAsistenciaCrg(String SI, Long idCrg, Long casillaRuta, Long tipo, Long federal, Long municipio) {
		String select = "select count(*) from app_instalacion_casilla aic " 
				+ "inner join app_asignacion_casillas aac "
				+ "on aic.id_casilla = aac.id_casilla " ;
//				+ "where aic.llego_rc = ? and aac.id_crg = ? and aac.ruta = ?";
		
		
		
		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		
		if (tipo == 1L) {
			where = " where aic.llego_rc = ? and aac.id_crg = ? and aac.ruta = ? ";
			
			para.add(SI);
			type.add(Types.VARCHAR);
			para.add(idCrg);
			type.add(Types.NUMERIC);
			para.add(casillaRuta);
			type.add(Types.NUMERIC);
		}
		
		if (tipo == 2L) {
			where = " where aic.llego_rc = ? and aac.distrito_federal_id = ? ";

			para.add(SI);
			type.add(Types.VARCHAR);
			para.add(federal);
			type.add(Types.NUMERIC);
		}

		if (tipo == 3L) {
			where = "where aic.llego_rc = ? and aac.ruta = ? and aac.distrito_federal_id = ?";
			para.add(SI);
			type.add(Types.VARCHAR);
//			para.add(idCrg);
//			type.add(Types.NUMERIC);
			para.add(casillaRuta);
			type.add(Types.NUMERIC);
			para.add(federal);
			type.add(Types.NUMERIC);
		}
		
		if (tipo == 4L) {
			where = "inner join app_casilla ac " 
					+"on ac.id = aic.id_casilla " 
					+"where aic.llego_rc = ? and ac.municpio_id = ? ";
			
			para.add(SI);
			type.add(Types.VARCHAR);
			para.add(municipio);
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
	public Long getCountRcByRutaRg(String SI, Long federal, Long local, Long municipio, Long seccion, Long crg, Long rg,
			Long tipo, String tipoCasilla, String idRutaRg) {
		
			String sql = "select count(*) from app_instalacion_casilla aic " 
				+ "inner join app_asignacion_casillas aac "
				+ "on aic.id_casilla = aac.id_casilla " 
				+ "where aic.llego_rc = ? and aac.id_ruta_rg = ? and aac.seccion_id = ? and tipo_casilla = ?";

		try {

			return template.queryForObject(sql, new Object[] { SI, idRutaRg, seccion, tipoCasilla }, new int[] {Types.VARCHAR, Types.VARCHAR, Types.NUMERIC, Types.VARCHAR }, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public Long getCountRcByAll(String SI, Long federal, Long local, Long municipio, Long seccion, Long crg, Long rg,
			Long tipo, String tipoCasilla, String idRutaRg) {

		String select = "select count(*) from app_instalacion_casilla aic " 
				+ "inner join app_casilla ac "
				+ "on aic.id_casilla = ac.id";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (federal == null) {
			where = "where aic.llego_rc = ? ";
			para.add(SI);
			type.add(Types.VARCHAR);
		}

		if (federal != null) {
//			if (para.size() > 0) {
				where = " where aic.llego_rc = ? and ac.federal_id = ? and ac.tipo_casilla = ? and ac.seccion_id = ?";
				para.add(SI);
				type.add(Types.VARCHAR);
				para.add(federal);
				type.add(Types.NUMERIC);
				para.add(tipoCasilla);
				type.add(Types.VARCHAR);
				para.add(seccion);
				type.add(Types.NUMERIC);
//			}
		}

		if (local != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
				para.add(local);
				type.add(Types.NUMERIC);
			}
		}
		
		if (municipio != null) {
			if (para.size() > 0) {
			where = where.concat(" and ac.municpio_id = ? ");
			para.add(municipio);
			type.add(Types.NUMERIC);
			}
		}

//		if (claveElector != null) {
//
//			if (para.size() > 0) {
//				where = where.concat(" and ac.clave_elector = ? ");
//			} else {
//				where = " where ac.clave_elector = ? ";
//			}
//			para.add(claveElector);
//			type.add(Types.VARCHAR);
//		}

		Object[] parametros = new Object[para.size()];
		int[] types = new int[para.size()];

		for (int i = 0; i < para.size(); i++) {
			parametros[i] = para.get(i);
			types[i] = type.get(i);
		}

		try {


			sql = select.concat(where);
//			System.out.println("xx " + sql);

			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public InstalacionCasilla getCasillaById(Long idCasilla) {
		String sql = "select * from app_instalacion_casilla aic where id_casilla = ?";
		try {

			return template.queryForObject(sql, new Object[] { idCasilla }, new int[] { Types.NUMERIC },
					new GetInstalacionCasillaRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
